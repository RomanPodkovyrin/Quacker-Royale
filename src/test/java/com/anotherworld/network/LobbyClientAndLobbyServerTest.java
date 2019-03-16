package com.anotherworld.network;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

public class LobbyClientAndLobbyServerTest {
    int numberOfClients = 3;
    @Test
    public void lobbyServerConnectionsWaitingTest() throws InterruptedException, IOException {
        //starting lobby server
        LobbyServer lobbyServer = new LobbyServer(numberOfClients);
        lobbyServer.start();
        //creating the first client
        LobbyClient lobbyClient1 = new LobbyClient("localhost");
        //nobody has connected yet
        assertTrue(!lobbyServer.isReady());
        lobbyClient1.sendMyIp();
        //only one client has send his ip, lobby server waits for 3 players
        assertTrue(!lobbyServer.isReady());
        //creating the second client
        LobbyClient lobbyClient2 = new LobbyClient("localhost");
        lobbyClient2.sendMyIp();
        //only 2/3 clients have send their ips
        assertTrue(!lobbyServer.isReady());
        //creating the third client
        LobbyClient lobbyClient3 = new LobbyClient("localhost");
        lobbyClient3.sendMyIp();
        //now everyone has connected
        lobbyClient2.waitForGameToStart();
        assertTrue(lobbyServer.isReady());
        ArrayList<String> clientsIps = lobbyServer.getIPs();
        assertTrue(clientsIps.size() == numberOfClients);
        lobbyServer.stopLobbyServer();
    }
}
