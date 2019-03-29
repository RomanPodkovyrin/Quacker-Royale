package com.anotherworld.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class AbstractNetworkControllerTest {

    @Test
    public void networkingTest() {
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
//          client = new GameClient("localhost");
//          AbstractNetworkController networkingClient = new NetworkControllerClient(client,settings);
//          networkingClient.stopNetworking();

          server = new Server(1,settings);
          AbstractNetworkController networkingServer = new NetworkControllerServer(server,settings);
          try {
              networkingServer.stopNetworking();
          } catch (IndexOutOfBoundsException e) {

          }


          AbstractNetworkController networkSinglePlayer = new NetworkControllerSinglePlayer();
//          as(networkSinglePlayer.clientControl(null));

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }
}
