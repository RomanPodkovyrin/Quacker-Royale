package com.anotherworld.network;

import com.anotherworld.model.logic.GameSession;
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


/**
 * Abstract networking class.
 * @author roman
 */
public abstract class AbstractNetworkController {
    protected GameClient client;
    protected Server server;

    protected ArrayList<PlayerData> allPlayers;
    protected PlayerData currentPlayer;
    protected ArrayList<BallData> balls;
    protected PlatformData platform;
    protected WallData wall;
    protected GameSessionData gameSessionData;
    protected GameSession gameSession;
    protected static Logger logger = LogManager.getLogger(AbstractNetworkController.class);
    protected int hostSendRate = 0;

    /**
     * Takes all the game objects from the class and saves their references int NetworkController class.
     *
     * @param settings - game representations
     */
    protected void setUpGameSettings(GameSettings settings) {
        ArrayList<PlayerData> temp = new ArrayList<>(settings.getPlayers());
        temp.add(settings.getCurrentPlayer());

        this.currentPlayer = settings.getCurrentPlayer();
        this.allPlayers = temp;
        this.balls = settings.getBalls();
        this.platform = settings.getPlatform().get(0);
        this.wall = settings.getWall().get(0);
        this.gameSessionData = settings.getGameSessionData();

    }

    /**
     * Adds the current game session.
     * @param gameSession - Current game session
     */
    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
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

    /**
     * Used to quit the game.
     */
    public abstract void quitTheGame();
}
