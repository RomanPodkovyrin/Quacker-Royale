package com.anotherworld.network;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.Input;
import com.anotherworld.tools.input.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Controls the game networking between client and host.
 *
 * @author roman
 */
@Deprecated
public class NetworkController {
    private GameClient client;
    private Server server;

    private ArrayList<PlayerData> allPlayers;
    private ArrayList<BallData> balls;
    private PlatformData platform;
    private WallData wall;
    private GameSessionData gameSessionData;
    private static Logger logger = LogManager.getLogger(NetworkController.class);
    private int hostSendRate = 0;

    /**
     * Created when playing a single player game.
     */
    public NetworkController() {

    }

    /**
     * Created for client.
     * @param client - the client connection
     * @param settings - the game representation for the player
     */
    public NetworkController(GameClient client, GameSettings settings) {
        this.client = client;
        setUpGameSettings(settings);

    }

    /**
     * Created for server.
     * @param server - the server connection
     * @param settings - the game representation for the player
     */
    public NetworkController(Server server, GameSettings settings) {
        this.server = server;
        setUpGameSettings(settings);
    }

    /**
     * Takes all the game objects from the class and saves their references int NetworkController class.
     *
     * @param settings - game representations
     */
    private void setUpGameSettings(GameSettings settings) {
        ArrayList<PlayerData> temp = new ArrayList<>();
        temp.addAll(settings.getPlayers());
        temp.add(settings.getCurrentPlayer());

        allPlayers = temp;
        balls = settings.getBalls();
        platform = settings.getPlatform().get(0);
        wall = settings.getWall().get(0);
        gameSessionData = settings.getGameSession();

    }

    public boolean isClient() {
        return client != null;
    }

    public boolean isServer() {
        return server != null;
    }


    /**
     * The Network control for the client.
     */
    public void clientControl(KeyListener keyListener) {

        if (isClient()) {
            // send the given key presses to the host
            ArrayList<Input> keyPresses = keyListener.getKeyPresses();

            // send key presses to host
            try {
                client.sendKeyPresses(keyPresses);
            } catch (IOException e) {
                e.printStackTrace();
            }


            ArrayList<PlayerData> playerUpdate = client.getPlayerData();
            ArrayList<BallData> ballUpdate = client.getBallData();
            PlatformData platformUpdate = client.getPlatformData();
            WallData wallUpdate = client.getWallData();
            GameSessionData sessionUpdate = client.getGameSessionData();

            if (playerUpdate != null) {
                // update Players
                for (PlayerData data : playerUpdate) {
                    for (PlayerData player : allPlayers) {
                        if (data.getObjectID().equals(player.getObjectID())) {
                            player.copyObject(data);
                        }

                    }
                }
            }

            // update balls
            // TODO need ball ids
            for (BallData data: ballUpdate) {
                for (BallData ball: balls) {
                    if (data.getObjectID().equals(ball.getObjectID())) {
                        ball.copyObject(data);
                    }
                }
            }
            // update platform
            platform.copyObject(platformUpdate);

            // update wall
            wall.copyObject(wallUpdate);

            // update session
            gameSessionData.copyObject(sessionUpdate);



        }


    }

    /**
     * Stops all the threads for game networking.
     */
    public void stopNetworking() {
        if (isServer()) {
            server.stopServer();
        } else if (isClient()) {
            client.stopClient();
        }
    }

    /**
     * Network control for the server.
     */
    public void hostControl() {

        if (isServer()) {
            // TODO get the button presses from the client

            //GameSession.updatePlayer();
            ArrayList<Pair<ArrayList<Input>,String>> keyPress = server.getInputAndIP();

            for (Pair<ArrayList<Input>, String> input: keyPress) {

                ArrayList<Input> in = input.getKey();
                String id = input.getValue();

                for (PlayerData player: allPlayers) {

                    if (player.getObjectID().equals(id)) {
//                        if (in.contains(Input.UP)) player.setYVelocity(-player.getSpeed());
//                        else if (in.contains(Input.DOWN)) player.setYVelocity(player.getSpeed());
//                        else player.setYVelocity(0);
//
//                        if (in.contains(Input.LEFT)) player.setXVelocity(-player.getSpeed());
//                        else if (in.contains(Input.RIGHT)) player.setXVelocity(player.getSpeed());
//                        else player.setXVelocity(0);

                        GameSession.updatePlayer(player,in);
                    }
                }

            }



            if (hostSendRate == 0) {
                logger.trace("Sending all the players");
                try {
                    server.sendObjectToClients(allPlayers);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                logger.trace("Sending all balls");
                try {
                    server.sendObjectToClients(balls);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                logger.trace("Sending the platform");
                try {
                    server.sendObjectToClients(platform);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                logger.trace("Sending the Wall");
                try {
                    server.sendObjectToClients(wall);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.trace("Sending the game session");
                try {
                    server.sendObjectToClients(gameSessionData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                hostSendRate++;
            } else if (hostSendRate == 2) {
                hostSendRate = 0;
            } else {
                hostSendRate++;
            }
        }
    }
}
