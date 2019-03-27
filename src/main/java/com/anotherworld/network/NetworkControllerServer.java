package com.anotherworld.network;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.GameKeyListener;
import com.anotherworld.tools.input.Input;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Pair;


/**
 * Class which controls the server side of the session.
 * @author roman
 */
public class NetworkControllerServer extends AbstractNetworkController {

    public NetworkControllerServer(Server server, GameSettings settings) {
        this.server = server;
        setUpGameSettings(settings);
    }

    @Override
    public void clientControl(GameKeyListener keyListener) {

    }

    @Override
    public void stopNetworking() {
        server.stopServer();
    }

    @Override
    public void hostControl() {

        ArrayList<Pair<ArrayList<Input>,String>> keyPress = server.getInputAndIP();

        for (Pair<ArrayList<Input>, String> input: keyPress) {

            ArrayList<Input> in = input.getKey();
            String id = input.getValue();

            for (PlayerData player: allPlayers) {

                if (player.getObjectID().equals(id)) {

                    if (in.contains(Input.QUIT)) {
                        //The client quit the game
                        player.setState(ObjectState.DEAD);
                    }

                    GameSession.updatePlayer(player,in, gameSessionData);
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

    @Override
    public void quitTheGame() {
        for (PlayerData player: allPlayers) {
            player.setState(ObjectState.DEAD);
        }
        try {
            server.sendObjectToClients(allPlayers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
