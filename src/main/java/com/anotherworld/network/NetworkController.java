package com.anotherworld.network;

import com.anotherworld.model.logic.Wall;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import com.anotherworld.tools.input.Input;
import com.anotherworld.tools.input.KeyListener;

import java.util.ArrayList;

public class NetworkController {
    private GameClient client;
    private Server server;
    private KeyListener keyListener;

    private GameSettings settings;

    private ArrayList<PlayerData> allPlayers;
    private ArrayList<BallData> balls;
    private PlatformData platform;
    private WallData wall;
    private GameSessionData gameSessionData;

    public NetworkController() {

    }

    public NetworkController(GameClient client, GameSettings settings) {
        this.client = client;
        this.settings = settings;
    }

    public NetworkController(Server server, GameSettings settings) {
        this.server = server;
        this.settings = settings;
    }

    private void setUpGameSettion(GameSettings settings) {
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
        return client!=null;
    }

    public boolean isServer() {
        return server!=null;
    }


    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void sendKeyPress() {

    }

    public void clientControl() {

        if (isClient()) {

            // Get all the game objects from server and update all the game objects accordingly




            // TODO send the key presses to host

            // send the given key presses to the host
            ArrayList<Input> keyPresses = keyListener.getKeyPresses();
            if (keyPresses.contains(Input.CHARGE)) {
                //TODO: Implement charge action.
            } else {
                if (keyPresses.contains(Input.UP)) {
                    //
                } else if (keyPresses.contains(Input.DOWN)) {
                    //
                } else {
                    //
                }

                if (keyPresses.contains(Input.LEFT)) {

                } else if (keyPresses.contains(Input.RIGHT)) {
                    //
                } else {
                    //
                }
            }



            //TODO check if there are any new objects to update
            // gets all the objects send from host and updates the current reference
            ArrayList<PlayerData> playerUpdate = client.getPlayerData();
            ArrayList<BallData> ballUpdate = client.getBallData();
            PlatformData platformUpdate = client.getPlatformData();
            WallData wallUpdate = client.getWallData();
            GameSessionData sessionUpdate = client.getGameSessionData();
//
//            if (playerUpdate != null) {
//                // update Players
//                for (PlayerData data : playerUpdate) {
//                    for (PlayerData player : allPlayers) {
//                        if (data.getObjectID().equals(player.getObjectID())) {
//                            player.copyObject(data);
//                        }
//
//                    }
//                }
//            }
//
//            // update balls
//            // TODO need ball ids
//            for (BallData data: ballUpdate) {
//                for(BallData ball: balls) {
//                    if (data.getObjectID().equals(ball.getObjectID())) {
//                        ball.copyObject(data);
//                    }
//                }
//            }
//            // update platform
//            platform.copyObject(platformUpdate);
//
//            // update wall
//            wall.copyObject(wallUpdate);
//
//            // update session
//            gameSessionData.copyObject(sessionUpdate);
//
//

        }


    }

    public void hostControl() {
        if (isServer()) {
            // TODO send the states of of the game to clients
            // Regardless of the situation send all the game object to clients

            // Get all the button pressed from clients and update game objects accordingly

            // Should i be done before sending the objects ?



            // TODO get the button presses from the client
            // check player id and trigger the appropriate action based on the button press

        }
    }




}
