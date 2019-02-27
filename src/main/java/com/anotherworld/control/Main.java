package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.network.*;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.MenuDemo;
import com.anotherworld.tools.datapool.*;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * @author roman
 */
public class Main {
    private MenuDemo view;
    private GameLobby lobby;
    private ArrayList<String> playersIPaddresses = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(Main.class);
    boolean runTheHostGame = false;

    public static void main(String []args) {
        Main main = new Main();
        main.startTheGame(2,0,3);
        MenuDemo viewMenu = new MenuDemo();

    }

    public void setRunTheHostGame(boolean run) {
        this.runTheHostGame = run;
    }

    public void add(MenuDemo view) {
        this.view = view;
    }

    public Main() {
        // need to set default config files?

    }

    
    public void startTheGame(int numberOfplayers, int ai, int balls) {

        GameSettings settings = new GameSettings(numberOfplayers,ai,balls);

        GLFW.glfwInit();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        try {
            View view = new View((int)(mode.width() * 0.8), (int)(mode.height() * 0.8));

            new GameSessionController(view, settings, new NetworkController());

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
    }

    public void host() {
        logger.info("User starting the server");
        // start the server
        // wait for clients to connect
        // count number of network players
        int numberOfPlayers = 1;
        //LobbyServer lobbyServer = new LobbyServer(numberOfPlayers);
        LobbyServer lobbyServer = new LobbyServer(numberOfPlayers);
        lobbyServer.start();

        logger.info("Started the lobby server");
        //GameLobby lobby = new GameLobby(true);
        //ArrayList<String> players = lobby.getNetworkPlayers();
        // waits for one player to connect


        logger.trace("All network players: " + playersIPaddresses);
        GameSettings settings = new GameSettings(numberOfPlayers + 1,0,3);
        Server server = null;
        try {
            server  = new Server(numberOfPlayers, settings);
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        while (!lobbyServer.isReady()) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playersIPaddresses = lobbyServer.getIPs();

        }
        logger.info("Setting up the game session");
        // Create the game settings
        runTheHostGame = true;
        while (!runTheHostGame) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        settings.setServer(server);
        NetworkController network = new NetworkController(server, settings);
//        startTheGame(numberOfNetworkPlayer + 1, 0,3);


        GLFW.glfwInit();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        try {
            View view = new View((int)(mode.width()), (int)(mode.height()));

            new GameSessionController(view, settings, network);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
        // Check if network game and a host
        // if yes then send all game objects to clients

        // Start the game with the current settings

    }

    public void connect(String serverIP) {
        // Enter the ip you want to connect to
        // wait for the command from host to start the game

        LobbyClient lobbyClient = new LobbyClient(serverIP);
        try {
            lobbyClient.sendMyIp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Connecting to lobby host " + serverIP);
        try {
            lobbyClient.waitForGameToStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Setting up the game session");

        GameClient client = null;
        try {
            client = new GameClient(serverIP);
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // get the send object from the server

        // make a new game settings with those objects

        // give the setting the client object

        // start the game as usual with the given game setting
        boolean waitingForObjects = true;

        ArrayList<PlayerData> allPlayers = null;
        ArrayList<BallData> allBalls = null ;
        PlayerData myPlayer= null ;
        PlatformData platform= null ;
        WallData wall= null;
        GameSessionData session = null;

        while(waitingForObjects) {
            System.out.println("Hello ");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            allPlayers = client.getPlayerData();
            allBalls = client.getBallData();
            myPlayer = client.getClientPlayer();
            platform = client.getPlatformData();
            wall = client.getWallData();
            session = client.getGameSessionData();

            if ((allPlayers != null & allBalls != null & myPlayer != null & platform != null & wall != null & session != null)) {
                logger.info("Got all objects");
                waitingForObjects = false;
            }
        }
        System.out.println(allPlayers + " " + allBalls + " " + myPlayer + " " +platform + " " + wall + " " + session);

        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getObjectID().equals(myPlayer.getObjectID())) {
                allPlayers.remove(i);
            }
        }

        ArrayList<PlatformData> platforms = new ArrayList<>();
        platforms.add(platform);

        ArrayList<WallData> walls = new ArrayList<>();
        walls.add(wall);




        GameSettings settings = new GameSettings(myPlayer,allPlayers,new ArrayList<PlayerData>(),allBalls,platforms,walls,session);

        settings.setClient(client);

        NetworkController network = new NetworkController(client, settings);

        GLFW.glfwInit();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        try {
            View view = new View((int)(mode.width()), (int)(mode.height()));

            new GameSessionController(view, settings, network);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
        // recieve the game objects from the host.
        // create the game setting
        // start the game with the current settings
    }


    public void startSinglePlayer() {
        startTheGame(4,3,6);
    }

    public static boolean sfxSetting(boolean on) {
//        boolean state = GameSettings.toggleOnOff("soundEffects");
        logger.info("Toggle soundEffect "  + on);
        AudioControl.setEffectsOn(on);
        return on;
    }
    
    public static boolean musicSetting(boolean on) {
        AudioControl.setMusicOn(on);
//        boolean state = GameSettings.toggleOnOff("backgroundMusic");
        logger.info("Toggle backgroundMusic "  + on);
        return on;
    }


}
