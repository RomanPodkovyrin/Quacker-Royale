package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkControllerTest {

    @Test
    public void NetworkingTest() {
        GameSettings settings = new GameSettings(new PlayerData("",0,0,0, ObjectState.IDLE,0,0),
                new ArrayList<>(Arrays.asList(new PlayerData("",0,0,0, ObjectState.IDLE,0,0))),
                new ArrayList<>(Arrays.asList(new PlayerData("",0,0,0, ObjectState.IDLE,0,0))),
                new ArrayList<>(Arrays.asList(new BallData("",true,0,0, ObjectState.IDLE,0,0))),
                new ArrayList<>(Arrays.asList(new PlatformData(0,0))),
                new ArrayList<>(Arrays.asList(new WallData(0,0))),
                new GameSessionData(0));

        GameClient client;
        Server server;
        try {
//            client = new GameClient("localhost");
//            NetworkController networking = new NetworkController(client,settings);
//            assertEquals(true,networking.isClient());
//            assertEquals(false,networking.isServer());
//            System.out.println("R");
//            networking.stopNetworking();

            server = new Server(1,settings);
            NetworkController networking = new NetworkController(server,settings);
            assertEquals(true,networking.isServer());
            assertEquals(false,networking.isClient());
            networking.stopNetworking();

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
}
