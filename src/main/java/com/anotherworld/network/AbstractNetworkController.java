package com.anotherworld.network;

import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import com.anotherworld.tools.input.KeyListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class AbstractNetworkController {
    protected GameClient client;
    protected Server server;

    protected ArrayList<PlayerData> allPlayers;
    protected ArrayList<BallData> balls;
    protected PlatformData platform;
    protected WallData wall;
    protected GameSessionData gameSessionData;
    protected static Logger logger = LogManager.getLogger(NetworkController.class);
    protected int hostSendRate = 0;
//
//    /**
//     * Created when playing a single player game.
//     */
//    public AbstractNetworkController() {
//
//    }
//
//    /**
//     * Created for client.
//     * @param client - the client connection
//     * @param settings - the game representation for the player
//     */
//    public AbstractNetworkController(GameClient client, GameSettings settings) {
//        this.client = client;
//        setUpGameSettings(settings);
//
//    }
//
//    /**
//     * Created for server.
//     * @param server - the server connection
//     * @param settings - the game representation for the player
//     */
//    public AbstractNetworkController(Server server, GameSettings settings) {
//        this.server = server;
//        setUpGameSettings(settings);
//    }

    /**
     * Takes all the game objects from the class and saves their references int NetworkController class.
     *
     * @param settings - game representations
     */
    protected void setUpGameSettings(GameSettings settings) {
        ArrayList<PlayerData> temp = new ArrayList<>();
        temp.addAll(settings.getPlayers());
        temp.add(settings.getCurrentPlayer());

        this.allPlayers = temp;
        this.balls = settings.getBalls();
        this.platform = settings.getPlatform().get(0);
        this.wall = settings.getWall().get(0);
        this.gameSessionData = settings.getGameSession();

    }

//    public boolean isClient() {
//        return client != null;
//    }
//
//    public boolean isServer() {
//        return server != null;
//    }


    /**
     * The Network control for the client.
     */
    public abstract void clientControl(KeyListener keyListener) ;

    /**
     * Stops all the threads for game networking.
     */
    public abstract void stopNetworking() ;

    /**
     * Network control for the server.
     */
    public abstract void hostControl() ;
}
