package com.anotherworld.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class LobbyServer extends Thread{
    private ServerSocket TCPsocket;
    private int port;
    private boolean allPlayersJoined;
    private ArrayList<String> playersIPAddresses;
    private ArrayList<OutputStream> clientSockets;
    private int currentPlayersAmount;
    private int numberOfClients;
    private static Logger logger = LogManager.getLogger(LobbyServer.class);

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

    public void run(){
        while(!allPlayersJoined){
            try {
                getPlayersIP();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            informAllClientsThatGameHasStarted();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getPlayersIP() throws IOException {
        Socket lobbySocket = TCPsocket.accept();
        DataInputStream in = new DataInputStream(lobbySocket.getInputStream());
        logger.trace("Received from: " + lobbySocket.getInetAddress().getHostAddress() + " on port " + lobbySocket.getPort());
        clientSockets.add(lobbySocket.getOutputStream());
        playersIPAddresses.add(lobbySocket.getInetAddress().getHostAddress());
        countPlayers();
        logger.trace("New player has joined the lobby. Now there are " + currentPlayersAmount + " player in lobby");
    }

    public void informAllClientsThatGameHasStarted() throws IOException {
        for(int i = 0; i< clientSockets.size(); i++){
            DataOutputStream out = new DataOutputStream(clientSockets.get(i));
            out.writeUTF(String.valueOf(i));
            clientSockets.get(i).close();
        }
    }

    public void countPlayers(){
        currentPlayersAmount++;
        if(currentPlayersAmount == numberOfClients)
            allPlayersJoined = true;
    }

    public boolean isReady(){
        return allPlayersJoined;
    }

    public ArrayList<String> getIPs(){
        return playersIPAddresses;
    }

    public static void main(String arg[]) throws Exception {
        new LobbyServer(2).start();
    }
}
