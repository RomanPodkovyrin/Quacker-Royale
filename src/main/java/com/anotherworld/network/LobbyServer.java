package com.anotherworld.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * This class allows host player to create a lobby, so
 * all the clients can connect to it before game starts
 *
 * @author Antons Lebedjko
 */
public class LobbyServer extends Thread{
    private ServerSocket TCPsocket;
    private int port;
    private boolean allPlayersJoined;
    private ArrayList<String> playersIPAddresses;
    private ArrayList<OutputStream> clientSockets;
    private int currentPlayersAmount;
    private int numberOfClients;
    private static Logger logger = LogManager.getLogger(LobbyServer.class);

    /**
     * Used to set up a connection with the clients
     *
     * @param numberOfClients the amount of clients that are going to play
     */
    public LobbyServer(int numberOfClients){
        playersIPAddresses = new ArrayList<String>();
        clientSockets = new ArrayList<OutputStream>();
        currentPlayersAmount = 0;
        this.numberOfClients = numberOfClients;
        port = 4446;
        try {
            TCPsocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A run method for the thread which waits for all clients to connect,
     * after that it will inform them that the game can start
     */
    public void run(){
        while(!allPlayersJoined){
            try {
                getPlayersIP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            informAllClientsThatGameCanStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to get IP addresses of all the clients
     */
    private void getPlayersIP() throws IOException {
        Socket lobbySocket = TCPsocket.accept();
        DataInputStream in = new DataInputStream(lobbySocket.getInputStream());
        logger.trace("Received from: " + lobbySocket.getInetAddress().getHostAddress() + " on port " + lobbySocket.getPort());
        clientSockets.add(lobbySocket.getOutputStream());
        playersIPAddresses.add(lobbySocket.getInetAddress().getHostAddress());
        countPlayers();
        logger.trace("New player has joined the lobby. Now there are " + currentPlayersAmount + " player in lobby");
    }

    /**
     * Used to send a message to all clients, that everyone has connected and gam can be started
     */
    public void informAllClientsThatGameCanStart() throws IOException {
        for(int i = 0; i< clientSockets.size(); i++){
            DataOutputStream out = new DataOutputStream(clientSockets.get(i));
            out.writeUTF(String.valueOf(i));
            clientSockets.get(i).close();
        }
    }

    /**
     * Used to track how many clients have already connected to the Lobby Server
     */
    public void countPlayers(){
        currentPlayersAmount++;
        if(currentPlayersAmount == numberOfClients)
            allPlayersJoined = true;
    }

    /**
     * Used to send the player id of each client
     *
     * @return the status if all players have joined the lobby or they haven't yet
     */
    public boolean isReady(){
        return allPlayersJoined;
    }

    /**
     * Used to get all the IP addresses of the clients
     *
     * @return the ArrayList with all clients IPs
     */
    public ArrayList<String> getIPs(){
        return playersIPAddresses;
    }
}
