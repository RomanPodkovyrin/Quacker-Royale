package com.anotherworld.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class allows host player to create a lobby, so
 * all the clients can connect to it before game starts.
 *
 * @author Antons Lebedjko
 */
public class LobbyServer extends Thread {
    private ServerSocket tcpSocket;
    private int port;
    private boolean allPlayersJoined;
    private boolean canStart = false;
    private ArrayList<String> playersIpAddresses;
    private ArrayList<OutputStream> clientSockets;
    private int currentPlayersAmount;
    private int numberOfClients;
    private static Logger logger = LogManager.getLogger(LobbyServer.class);

    /**
     * Used to set up a connection with the clients.
     *
     * @param numberOfClients the amount of clients that are going to play
     */
    public LobbyServer(int numberOfClients) {
        playersIpAddresses = new ArrayList<String>();
        clientSockets = new ArrayList<OutputStream>();
        currentPlayersAmount = 0;
        this.numberOfClients = numberOfClients;
        port = 4446;
        try {
            tcpSocket = new ServerSocket(port);
            tcpSocket.setSoTimeout(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void canStartTheGame() {
        canStart = true;
    }

    /**
     * A run method for the thread which waits for all clients to connect,
     * after that it will inform them that the game can start.
     */
    public void run() {
        canStart = false;
        while (!(allPlayersJoined && canStart)) {
            try {
                getPlayersIP();
            } catch (IOException e) {
//                System.out.println("Temp, remove: timout");
                logger.trace("LobbyServerTimeout");
            }
        }
        logger.info("Telling clients to start the game");
        try {
            informAllClientsThatGameCanStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to get IP addresses of all the clients.
     */
    private void getPlayersIP() throws IOException {
        Socket lobbySocket = tcpSocket.accept();
        DataInputStream in = new DataInputStream(lobbySocket.getInputStream());
        if (in.readUTF().equals("cancel connection")) {
            System.out.println("Cancel me ");
            for (int i = 0; i < playersIpAddresses.size(); i++) {
                if (playersIpAddresses.get(i) == lobbySocket.getInetAddress().getHostAddress()) {
                    logger.info("Player " + playersIpAddresses.get(i) + " Disconnected");
                    playersIpAddresses.remove(playersIpAddresses.get(i));
                    currentPlayersAmount--;
                    allPlayersJoined = false;
                    canStart = false;
                }
            }
        } else {
            logger.info("Received from: " + lobbySocket.getInetAddress().getHostAddress() + " on port " + lobbySocket.getPort());
            clientSockets.add(lobbySocket.getOutputStream());
            playersIpAddresses.add(lobbySocket.getInetAddress().getHostAddress());
            countPlayers();
            logger.info("New player has joined the lobby. Now there are " + currentPlayersAmount + " player in lobby");
        }
    }

    /**
     * Used to send a message to all clients, that everyone has connected and gam can be started.
     */
    public void informAllClientsThatGameCanStart() throws IOException {
        for (int i = 0; i < clientSockets.size(); i++) {
            DataOutputStream out = new DataOutputStream(clientSockets.get(i));
            out.writeUTF(String.valueOf(i));
            clientSockets.get(i).close();
        }
    }

    /**
     * Used to track how many clients have already connected to the Lobby Server.
     */
    public void countPlayers() {
        currentPlayersAmount++;
        if (currentPlayersAmount == numberOfClients) {
            allPlayersJoined = true;
        }
    }

    /**
     * Used to send the player id of each client.
     *
     * @return the status if all players have joined the lobby or they haven't yet
     */
    public boolean isReady() {
        return allPlayersJoined;
    }

    /**
     * Used to get all the IP addresses of the clients.
     *
     * @return the ArrayList with all clients IPs
     */
    public ArrayList<String> getIPs() {
        return playersIpAddresses;
    }

    /**
     * Informs all the clients that lobby has been cancelled.
     */
    public void cancelLobby() throws IOException {
        for (int i = 0; i < clientSockets.size(); i++) {
            DataOutputStream out = new DataOutputStream(clientSockets.get(i));
            out.writeUTF("Host has cancelled the lobby");
            clientSockets.get(i).close();
            logger.trace("Lobby host has cancelled the lobby. Closed lobby client sockets");
        }
    }

    /**
     * Stops the communication with all clients and closes the socket.
     */
    public void stopLobbyServer() {
        allPlayersJoined = true;
        try {
            tcpSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
