package com.anotherworld.network;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.Input;
import com.anotherworld.tools.input.KeyListener;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkControllerServer extends AbstractNetworkController {

    public NetworkControllerServer(Server server, GameSettings settings) {
        this.server = server;
        setUpGameSettings(settings);
    }

    @Override
    public void clientControl(KeyListener keyListener) {

    }

    @Override
    public void stopNetworking() {
        server.stopServer();
    }

    @Override
    public void hostControl() {
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
