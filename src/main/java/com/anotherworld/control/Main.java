package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.control.exceptions.ConnectionClosed;
import com.anotherworld.control.exceptions.NoHostFound;
import com.anotherworld.network.AbstractNetworkController;
import com.anotherworld.network.GameClient;
import com.anotherworld.network.LobbyClient;
import com.anotherworld.network.LobbyServer;
import com.anotherworld.network.NetworkControllerClient;
import com.anotherworld.network.NetworkControllerServer;
import com.anotherworld.network.NetworkControllerSinglePlayer;
import com.anotherworld.network.Server;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.MenuDemo;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.Platform;

/**
 * This class helps to set up the appropriate settings to either start a single player game or a multiplayer.
 * @author roman
 */
public class Main {
    private MenuDemo view;
    private ArrayList<String> playersIPaddresses = new ArrayList<>();
    private static Logger logger = LogManager.getLogger(Main.class);
    private boolean runTheHostGame = false;
    private boolean cancelTheGame = false;

    private int defaultSinglePlayerAI;
    private int defaultSinglePlayerPlayers;
    private int defaultSinglePlayerBalls;

    private int defaultMultiPlayerAI;
    private int defaultMultiPlayerBalls;
    private int defaultNumberClients;


    /**
     * The main should only be used for testing.
     *
     * @param args - command line arguments are not used
     */
    public static void main(String []args) {
        Main main = new Main();
        GameSettings settings = new GameSettings(2,1,1);
        main.startTheGame(settings, new NetworkControllerSinglePlayer());
        MenuDemo viewMenu = new MenuDemo();

    }

    public void setRunTheHostGame(boolean run) {
        this.runTheHostGame = run;
    }

    public void add(MenuDemo view) {
        this.view = view;
    }

    /**
     * Used to initialise the game main class for the game.
     */
    public Main() {
        PropertyReader propertyFileLogic = null;
        try {
            propertyFileLogic = new PropertyReader("logic.properties");
            this.defaultSinglePlayerAI = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_AI"));
            this.defaultSinglePlayerPlayers = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_PLAYERS"));
            this.defaultSinglePlayerBalls = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_BALLS"));
            this.defaultMultiPlayerAI = Integer.parseInt(propertyFileLogic.getValue("MULTI_PLAYER_AI"));
            this.defaultMultiPlayerBalls = Integer.parseInt(propertyFileLogic.getValue("MULTI_PLAYER_BALLS"));
            this.defaultNumberClients = Integer.parseInt(propertyFileLogic.getValue("NUMBER_CLIENTS"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // need to set default config files?
    }

    /**
     * Starts the game with the given settings and network connection.
     *
     * @param settings - settings which contain all the settings to the current game being played
     * @param network - networking which tells whether player is a server or a client. if a single player just
     *                pass new NetworkController()
     */
    private void startTheGame(GameSettings settings, AbstractNetworkController network) {

        try {
            // Starts the render
            View view;
            logger.trace("Render is initialised");
            if (Platform.get() == Platform.MACOSX) {
                view = new View(1920, 1080);
            } else {
                GLFW.glfwInit();
                GLFWVidMode mode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
                view = new View((int)(mode.width()), (int)(mode.height()));
            }
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
     * When the server is up and running. Host can call this method to start the game.
     * @return true can start the game, false can not
     */
    public boolean hostStartTheGame() {
        if (playersIPaddresses.size() == defaultNumberClients) {
            runTheHostGame = true;
        }
        return runTheHostGame;
    }

    public void hostCancelTheGame() {
        cancelTheGame = true;
    }


    public ArrayList<String> getPlayersIPaddresses() {
        return playersIPaddresses;
    }

    /**
     * Host the game, called when player wants to host multiplayer game.
     */
    public void host() throws ConnectionClosed {
        cancelTheGame = false;
        // Sets waits for the host to start the game
        runTheHostGame = false;
        //TODO think about how to cancel it
        logger.info("User starting the server");

        logger.trace("Multiplayer lobby is created and started");
        LobbyServer lobbyServer = new LobbyServer(defaultNumberClients);
        lobbyServer.start();

        logger.trace("Setting up the game session with " + defaultNumberClients + 1 + " players, " + defaultMultiPlayerBalls + " balls");
        GameSettings settings = new GameSettings(defaultNumberClients + 1,defaultMultiPlayerAI,defaultMultiPlayerBalls);

        logger.trace("Setting up the game Server");
        Server server = null;
        try {
            server  = new Server(defaultNumberClients, settings);
            server.start();
        } catch (SocketException e) {
            // Throw them back?
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        logger.trace("Lobby server is waiting for all players to connect");
        while (!lobbyServer.isReady()) {
            if (cancelTheGame) {
                throw new ConnectionClosed();
                //TODO tell clients to close?
            }

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


        //TODO when ready for the proper lobby implementation remove this
        runTheHostGame = true;
        while (!runTheHostGame) {
            if (cancelTheGame) {
                throw new ConnectionClosed();
                //TODO tell clients to Close?
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.trace("Host started the game");

        NetworkControllerServer network = new NetworkControllerServer(server, settings);
        startTheGame(settings,network);
    }

    /**
     * Connects to the game lobby on the give ip address.
     *
     * @param serverIP the host ip address to connect to
     */
    public void connect(String serverIP) throws NoHostFound, ConnectionClosed {
        logger.trace("Starting the Lobby client");
        LobbyClient lobbyClient = new LobbyClient(serverIP);
        try {
            lobbyClient.sendMyIp();
        } catch (ConnectException e) {
            throw new NoHostFound();

        } catch (IOException e) {
            //TODO What ?
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
            //TODO one of those has to be game cancelled exception
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


        NetworkControllerClient network = new NetworkControllerClient(client, settings);
        startTheGame(settings,network);
    }

    public void startSinglePlayer() {
        GameSettings settings = new GameSettings(defaultSinglePlayerPlayers,defaultSinglePlayerAI,defaultSinglePlayerBalls);
        startTheGame(settings, new NetworkControllerSinglePlayer());
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
