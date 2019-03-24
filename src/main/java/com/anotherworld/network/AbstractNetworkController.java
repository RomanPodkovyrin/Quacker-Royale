package com.anotherworld.network;

import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.GameKeyListener;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract class AbstractNetworkController {
    protected GameClient client;
    protected Server server;

    protected ArrayList<PlayerData> allPlayers;
    protected ArrayList<BallData> balls;
    protected PlatformData platform;
    protected WallData wall;
    protected GameSessionData gameSessionData;
    protected static Logger logger = LogManager.getLogger(AbstractNetworkController.class);
    protected int hostSendRate = 0;

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
        this.gameSessionData = settings.getGameSessionData();

    }



    /**
     * The Network control for the client.
     */
    public abstract void clientControl(GameKeyListener keyListener);

    /**
     * Stops all the threads for game networking.
     */
    public abstract void stopNetworking();

    /**
     * Network control for the server.
     */
    public abstract void hostControl();
}
