package com.anotherworld.network;

import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.GameKeyListener;
import com.anotherworld.tools.input.Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controls Client session.
 * @author roman
 */
public class NetworkControllerClient extends AbstractNetworkController {

    /**
     * Initialises the Class which controls client.
     * @param client - the client class to control
     * @param settings - gamesettings to handle for this session
     */
    public NetworkControllerClient(GameClient client, GameSettings settings) {
        this.client = client;
        setUpGameSettings(settings);

    }

    @Override
    public void clientControl(GameKeyListener keyListener) {
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

    @Override
    public void stopNetworking() {
        client.stopClient();

    }

    @Override
    public void hostControl() {

    }

    @Override
    public void quitTheGame() {
        try {
            client.sendKeyPresses(new ArrayList<>(Arrays.asList(Input.QUIT)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
