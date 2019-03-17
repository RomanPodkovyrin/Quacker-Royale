package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.Input;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static junit.framework.TestCase.assertTrue;


public class ClientAndServerTests {
    @Test
    public void serverAndClientTest() throws IOException {
        //starting server
        Server server = new Server(1, new GameSettings(
                new PlayerData("h",2,1,1, ObjectState.IDLE,1,2),
                new ArrayList<>(Arrays.asList(new PlayerData("h",2,1,1, ObjectState.IDLE,1,2))),
                null,
                new ArrayList<>(Arrays.asList(new BallData("h",true,1,1, ObjectState.IDLE,1,2))),
                new ArrayList<>(Arrays.asList(new PlatformData(2,2))),
                new ArrayList<>(Arrays.asList(new WallData(2,2))),
                null));
        server.start();
        //starting the client
        GameClient client1 = new GameClient("localhost");

        //test if initial objects before server has sent anything to the clients are equal to null
        assertTrue(client1.getClientPlayer() == null);
        assertTrue(client1.getWallData() == null);
        assertTrue(client1.getBallData() == null);
        assertTrue(client1.getPlatformData() == null);
        assertTrue(client1.getPlayerData() == null);
        assertTrue(client1.getGameSessionData() == null);

        //starting client thread
        client1.start();
        //wait for client to receive initial objects
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //test if client has received starting objects from the server
        assertTrue(client1.getClientPlayer() != null);
        assertTrue(client1.getWallData() != null);
        assertTrue(client1.getBallData() != null);
        assertTrue(client1.getPlatformData() != null);
        assertTrue(client1.getPlayerData() != null);

        //send key presses to server
        ArrayList<Input> keyPresses = new ArrayList<>();
        keyPresses.add(Input.LEFT);
        client1.sendKeyPresses(keyPresses);


        //terminating client
        client1.stop();
        client1.stopClient();

        //sending string to the client
        server.sendStringToClient("string".getBytes());

        //terminating server
        server.stopServer();

    }
}
