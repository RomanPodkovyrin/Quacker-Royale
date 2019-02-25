package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.audio.BackgroundMusic;
import com.anotherworld.network.*;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.MenuDemo;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {
    private MenuDemo view;
    private GameLobby lobby;
    private ArrayList<String> playersIPaddresses = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(Main.class);
    boolean runTheHostGame = false;

    public static void main (String args[]) {
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

            new GameSessionController(view, settings);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
    }

    public void host() {
        logger.info("User starting the server");
        // TODO write the logic
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
            } catch (InterruptedException e){
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
//        startTheGame(numberOfNetworkPlayer + 1, 0,3);


        GLFW.glfwInit();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        try {
            View view = new View((int)(mode.width()), (int)(mode.height()));

            new GameSessionController(view, settings);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
        // TODO Implement network
        // Check if network game and a host
        // if yes then send all game objects to clients

        // Start the game with the current settings

    }

    public void connect(String serverIP) {
        // TODO write the logic
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

//        while(true) {
//
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        GameSettings settings = new GameSettings(null,null,null,null,null,null,null);
//
//        settings.setClient(client);
//
//
//        GLFW.glfwInit();
//        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
//
//        try {
//            View view = new View((int)(mode.width()), (int)(mode.height()));
//
//            new GameSessionController(view, settings);
//
//        } catch (KeyListenerNotFoundException ex) {
//            logger.fatal(ex);
//        } catch (RuntimeException ex) {
//            logger.fatal(ex);
//            ex.printStackTrace();
//        }
        // recieve the game objects from the host.
        // create the game setting
        // start the game with the current settings
    }


    public void startSinglePlayer() {
        startTheGame(4,3,4);
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
