package com.anotherworld.control;

import com.anotherworld.tools.exceptions.ConnectionClosed;
import com.anotherworld.tools.exceptions.NoHostFound;
import com.anotherworld.network.AbstractNetworkController;
import com.anotherworld.network.GameClient;
import com.anotherworld.network.LobbyClient;
import com.anotherworld.network.LobbyServer;
import com.anotherworld.network.NetworkControllerClient;
import com.anotherworld.network.NetworkControllerServer;
import com.anotherworld.network.NetworkControllerSinglePlayer;
import com.anotherworld.network.Server;
import com.anotherworld.settings.GameSettings;
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
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class helps to set up the appropriate settings to either start a single player game or a multiplayer.
 * @author roman
 */
public class Controller {
    private static Logger logger = LogManager.getLogger(Controller.class);

    private View view;

    //Default values for single player game
    private int defaultSinglePlayerAI;
    private int defaultSinglePlayerPlayers;
    private int defaultSinglePlayerBalls;

    // Default values for multi player game
    private int defaultMultiPlayerAI;
    private int defaultMultiPlayerBalls;
    private int defaultNumberClients;

    // Networking
    private ArrayList<String> playersIPaddresses = new ArrayList<>();
    private LobbyClient lobbyClient;
    private boolean runTheHostGame = false;
    private boolean cancelTheGame = false;
    private boolean waitingForObjects = true;

    /**
     * Used to initialise the game main class for the game.
     */
    public Controller(View view) {
        this.view = view;
        setUp();
//        GameSettings.changeDifficulty(GameSettings.Difficulty.MEDIUM);
    }

    private void setUp() {

        try {
            PropertyReader propertyFileLogic = new PropertyReader("logic.properties");
            this.defaultSinglePlayerAI = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_AI"));
            this.defaultSinglePlayerPlayers = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_PLAYERS"));
            this.defaultSinglePlayerBalls = Integer.parseInt(propertyFileLogic.getValue("SINGLE_PLAYER_BALLS"));
            this.defaultMultiPlayerAI = Integer.parseInt(propertyFileLogic.getValue("MULTI_PLAYER_AI"));
            this.defaultMultiPlayerBalls = Integer.parseInt(propertyFileLogic.getValue("MULTI_PLAYER_BALLS"));
            this.defaultNumberClients = Integer.parseInt(propertyFileLogic.getValue("NUMBER_CLIENTS"));
        } catch (IOException e) {
            logger.error("Could not load default values from properties file: Check that logic.properties file is present");
        }
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
            logger.info("Starting the game session");
            new GameSessionController(view, settings, network);
        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
        }
    }

    /**
     * When the server is up and running. Host can call this method to start the game when all clients connected.
     * @return true can start the game, false can not
     */
    public boolean hostStartTheGame() {
        if (playersIPaddresses.size() == defaultNumberClients) {
            runTheHostGame = true;
        }
        return runTheHostGame;
    }

    /**
     * Cancels the host game.
     */
    public void hostCancelTheGame() {
        cancelTheGame = true;
    }


    /**
     * Returns the current ips connected.
     * @return array of connected clients
     */
    public ArrayList<String> getPlayersIPaddresses() {
        return playersIPaddresses;
    }

    /**
     * Host the game, called when player wants to host multiplayer game.
     */
    public void host() throws ConnectionClosed {

        setUp();
        // Resets defaults before starting lobby
        cancelTheGame = false;
        runTheHostGame = false;

        logger.info("Starting Host");
        LobbyServer lobbyServer = new LobbyServer(defaultNumberClients);
        lobbyServer.start();
        logger.trace("Server lobby is running");

        logger.trace("Setting up the game session with " + defaultNumberClients + 1 + " players, " + defaultMultiPlayerBalls + " balls");
        GameSettings settings = new GameSettings(defaultNumberClients + 1,defaultMultiPlayerAI,defaultMultiPlayerBalls);

        logger.trace("Setting up the Game Server");
        Server server = null;
        try {
            server  = new Server(defaultNumberClients, settings);
            server.start();
        } catch (SocketException |UnknownHostException e) {
            //Could not start the server
            logger.warn("Could not start the server");
            lobbyServer.stopLobbyServer();
            server.stopServer();
            throw new ConnectionClosed();
        }

        waitInLobby(lobbyServer, server);

        // Waits until Clients are ready
        while (server.areClientsReady()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        logger.info("Host started the game");

        NetworkControllerServer network = new NetworkControllerServer(server, settings);
        startTheGame(settings,network);
    }

    /**
     * Waits for all clients to connect and player to start the game.
     * @param lobbyServer - the lobby server
     * @param server - the game server
     * @throws ConnectionClosed
     */
    private void waitInLobby(LobbyServer lobbyServer, Server server) throws ConnectionClosed {
        logger.trace("Lobby server is waiting for all players to connect and for Host to start the game");
        while (!(lobbyServer.isReady() && runTheHostGame)) {

            //Check if host canceled the game
            if (cancelTheGame) {

                lobbyServer.stopLobbyServer();
                server.stopServer();
                throw new ConnectionClosed();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Get ips of connected clients
            playersIPaddresses = lobbyServer.getIPs();
        }

        logger.info("Lobby server: " + playersIPaddresses.size() + " players connected"
                + "\nLobby server is ready to play");

        // Tell clients to start the game
        lobbyServer.canStartTheGame();
    }

    /**
     * Client quits game lobby.
     * @return true can quit, false can't quit.
     */
    public boolean clientCancel() {

        if (lobbyClient != null) {

            try {
                logger.info("quit lobby");
                lobbyClient.cancelConnection();
            } catch (IOException e) {
                logger.error("Could not quit the lobby");
            }

            return true;
        }

        return false;
    }

    /**
     * Tells if received all the initial game objects.
     * @return true - has all the objects, false - waiting for objects
     */
    public boolean getServerStarted() {
        return !waitingForObjects;
    }

    /**
     * Connects to the game lobby on the give ip address.
     *
     * @param serverIP the host ip address to connect to
     */
    public void connect(String serverIP) throws NoHostFound, ConnectionClosed {
        // tells whether client got all the objects needed to start the game
        waitingForObjects = true;

        logger.info("Starting the Lobby client");
        lobbyClient = new LobbyClient(serverIP);
        try {
            logger.info("Connecting to lobby host " + serverIP);
            lobbyClient.sendMyIp();
        } catch (ConnectException e) {
            logger.warn("Could not connect to the server");
            throw new NoHostFound();

        } catch (IOException e) {
            //Could not connect to the host
            try {
                lobbyClient.cancelConnection();
            } catch (IOException e1) {

            } finally {
                logger.warn("Error while connecting to the Server");
                throw new ConnectionClosed();
            }

        }

        logger.info("Waiting for game to start");
        try {
            lobbyClient.waitForGameToStart();
        } catch (IOException e) {
            try {
                lobbyClient.cancelConnection();
            } catch (IOException e1) {

            } finally {
                throw new ConnectionClosed();
            }
        }

        logger.info("Starting the game client");
        GameClient client = null;
        try {
            client = new GameClient(serverIP);
            client.start();
        } catch (SocketException |UnknownHostException e) {
            logger.warn("Could not connect to game client");
            client.stopClient();
            throw new ConnectionClosed();
        }

        GameSettings settings = getGameSettings(client);


        logger.info("Starting the game");
        NetworkControllerClient network = new NetworkControllerClient(client, settings);
        startTheGame(settings,network);
    }

    /**
     * Gets all the initial objects from server to start a game.
     * @param client - the client connection
     * @return - game settings to start a game
     */
    private GameSettings getGameSettings(GameClient client) {
        // The initial game objects
        ArrayList<PlayerData> allPlayers = null;
        ArrayList<BallData> allBalls = null;
        PlayerData myPlayer = null;
        PlatformData platform = null;
        WallData wall = null;
        GameSessionData session  = null;

        logger.info("Waiting for host to send all the objects needed to start the game");
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
                logger.info("Client has received all the objects");
                waitingForObjects = false;
            }
        }

        // Removes  my player form the list of all players
        for (int i = 0; i < allPlayers.size(); i++) {
            if (allPlayers.get(i).getObjectID().equals(myPlayer.getObjectID())) {
                allPlayers.remove(i);
                break;
            }
        }

        // Makes Platform and wall into an appropriate type
        ArrayList<PlatformData> platforms = new ArrayList<>(Arrays.asList(platform));
        ArrayList<WallData> walls = new ArrayList<>(Arrays.asList(wall));

        logger.info("Setting up the game session");
        return new GameSettings(myPlayer,allPlayers,new ArrayList<>(),allBalls,platforms,walls,session);
    }

    /**
     * This method is used to start single player game.
     */
    public void startSinglePlayer() {
        logger.info("Starting single player game");
//        setUp();
        GameSettings settings = new GameSettings(defaultSinglePlayerPlayers,defaultSinglePlayerAI,defaultSinglePlayerBalls);
        startTheGame(settings, new NetworkControllerSinglePlayer());
    }

}
