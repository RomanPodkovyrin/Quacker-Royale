package com.anotherworld.network;

import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import com.anotherworld.control.exceptions.ConnectionClosed;
import org.junit.Test;


public class LobbyClientAndLobbyServerTest {
    int numberOfClients = 3;

    @Test
    public void lobbyServerConnectionsWaitingTest() throws IOException {
        //TODO this test does not finish
//        //starting lobby server
//        LobbyServer lobbyServer = new LobbyServer(numberOfClients);
//        lobbyServer.start();
//        //creating the first client
//        LobbyClient lobbyClient1 = new LobbyClient("localhost");
//        //nobody has connected yet
//        assertTrue(!lobbyServer.isReady());
//        lobbyClient1.sendMyIp();
//        //only one client has send his ip, lobby server waits for 3 players
//        assertTrue(!lobbyServer.isReady());
//        //creating the second client
//        LobbyClient lobbyClient2 = new LobbyClient("localhost");
//        lobbyClient2.sendMyIp();
//        //only 2/3 clients have send their ips
//        assertTrue(!lobbyServer.isReady());
//        //creating the third client
//        LobbyClient lobbyClient3 = new LobbyClient("localhost");
//        lobbyClient3.sendMyIp();
//        //now everyone has connected
//        try {
//            lobbyClient2.waitForGameToStart();
//        } catch (ConnectionClosed connectionClosed) {
//            connectionClosed.printStackTrace();
//        }
//        assertTrue(lobbyServer.isReady());
//        ArrayList<String> clientsIps = lobbyServer.getIPs();
//        assertTrue(clientsIps.size() == numberOfClients);
//        lobbyServer.stopLobbyServer();
    }
}
