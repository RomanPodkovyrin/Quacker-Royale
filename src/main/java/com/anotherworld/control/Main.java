package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.network.GameClient;
import com.anotherworld.network.LobbyClient;
import com.anotherworld.network.LobbyServer;
import com.anotherworld.network.NetworkController;
import com.anotherworld.network.Server;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.MenuDemo;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * This class helps to set up the appropriate settings to either start a single player game or a multiplayer.
 * @author roman
 */
public class Main {
    private MenuDemo view;
    private ArrayList<String> playersIPaddresses = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(Main.class);
    private boolean runTheHostGame = false;

    /**
     * The main should only be used for testing.
     *
     * @param args - command name arguments are not used
     */
    public static void main(String []args) {
        Main main = new Main();
        GameSettings settings = new GameSettings(2,1,1);
        main.startTheGame(settings, new NetworkController());
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

    /**
     * Starts the game with the given settings and network connection.
     *
     * @param settings - settings which contain all the settings to the current game being played
     * @param network - networking which tells whether player is a server or a client. if a single player just
     *                pass new NetworkController()
     */
    private void startTheGame(GameSettings settings, NetworkController network) {

        GLFW.glfwInit();
        GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        try {
            // Starts the render
            logger.trace("Render is initialised");
            View view = new View((int)(mode.width() * 0.8), (int)(mode.height() * 0.8));

            // Starts the game itself
            logger.trace("The game session started");
            new GameSessionController(view, settings, network);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
    }

    /**
     * Host the game, called when player wants to host multiplayer game.
     */
    public void host() {
        logger.info("User starting the server");

        // number of network players
        int numberOfPlayers = 2;
        int numberOfBalls = 3;

        logger.trace("Multiplayer lobby is created and started");
        LobbyServer lobbyServer = new LobbyServer(numberOfPlayers);
        lobbyServer.start();

        logger.trace("Setting up the game session with " + numberOfPlayers + 1 + " players, " + numberOfBalls + " balls");
        GameSettings settings = new GameSettings(numberOfPlayers + 1,0,3);

        logger.trace("Setting up the game Server");
        Server server = null;
        try {
            server  = new Server(numberOfPlayers, settings);
            server.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        logger.trace("Lobby server is waiting for all players to connect");
        while (!lobbyServer.isReady()) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playersIPaddresses = lobbyServer.getIPs();
        }
        logger.trace("Lobby server: " + playersIPaddresses.size() + " players connected"
                + "\nLobby server is ready to play");

        logger.trace("Waiting for Host to start the game");
        runTheHostGame = true;
        while (!runTheHostGame) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Host started the game");

        settings.setServer(server);
        NetworkController network = new NetworkController(server, settings);
        startTheGame(settings,network);
    }

    /**
     * Connects to the game lobby on the give ip address.
     *
     * @param serverIP the host ip address to connect to
     */
    public void connect(String serverIP) {
        logger.trace("Starting the Lobby client");
        LobbyClient lobbyClient = new LobbyClient(serverIP);
        try {
            lobbyClient.sendMyIp();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.trace("Connecting to lobby host " + serverIP);
        try {
            lobbyClient.waitForGameToStart();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.trace("Starting the game client");
        GameClient client = null;
        try {
            client = new GameClient(serverIP);
            client.start();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // tells whether client got all the objects needed to start the game
        boolean waitingForObjects = true;

        ArrayList<PlayerData> allPlayers = null;
        ArrayList<BallData> allBalls = null;
        PlayerData myPlayer = null;
        PlatformData platform = null;
        WallData wall = null;
        GameSessionData session  = null;

        logger.trace("Waiting for host to send all the objects needed to start the game");
        while (waitingForObjects) {
            try {
                Thread.sleep(1);
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
                logger.trace("Client has received all the objects");
                waitingForObjects = false;
            }
        }

        // Removes the my player form the list of all players
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getObjectID().equals(myPlayer.getObjectID())) {
                allPlayers.remove(i);
                break;
            }
        }

        // Makes Platform and wall into an appropriate type
        ArrayList<PlatformData> platforms = new ArrayList<>();
        platforms.add(platform);
        ArrayList<WallData> walls = new ArrayList<>();
        walls.add(wall);

        logger.trace("Setting up the game session");
        GameSettings settings = new GameSettings(myPlayer,allPlayers,new ArrayList<>(),allBalls,platforms,walls,session);

        settings.setClient(client);

        NetworkController network = new NetworkController(client, settings);
        startTheGame(settings,network);
    }

    public void startSinglePlayer() {
        GameSettings settings = new GameSettings(4,3,6);
        startTheGame(settings, new NetworkController());
    }

    /**
     * Toggles the sound effect on and off.
     * @param on - true is on, false is off
     * @return - true is on, false is off
     */
    public static boolean sfxSetting(boolean on) {
        logger.info("Toggle soundEffect "  + on);
        AudioControl.setEffectsOn(on);
        return on;
    }

    /**
     * Toggles the music on and off.
     * @param on - true is on, false is off
     * @return - true is on, false is off
     */
    public static boolean musicSetting(boolean on) {
        logger.info("Toggle backgroundMusic "  + on);
        AudioControl.setMusicOn(on);
        return on;
    }

}
