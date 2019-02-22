package com.anotherworld.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class LobbyClient {
    private String serverIp;
    private String myIpAddress;
    private Socket client;
    private String myID;
    private int port;
    private static Logger logger = LogManager.getLogger(LobbyClient.class);

    public LobbyClient(String serverIP){
        this.serverIp = serverIP;
        port = 4446;
        try {
            myIpAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendMyIp() throws IOException {
        client = new Socket(serverIp, port);
        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("Hello from " + client.getLocalSocketAddress());
    }

    public void waitForGameToStart() throws IOException {
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        myID = in.readUTF();
        logger.trace("My ID is now: " + myID);
        client.close();
    }

    public static void main(String arg[]) throws Exception {
        LobbyClient client = new LobbyClient("localhost");
        client.sendMyIp();
        client.waitForGameToStart();
    }
}
