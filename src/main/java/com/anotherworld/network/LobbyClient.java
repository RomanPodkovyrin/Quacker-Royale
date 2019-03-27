package com.anotherworld.network;

import com.anotherworld.control.exceptions.ConnectionClosed;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import com.anotherworld.tools.exceptions.ConnectionClosed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class allows clients to connect to the lobby server, before the actual game starts.
 *
 * @author Antons Lebedjko
 */
public class LobbyClient {
    private String serverIp;
    private String myIpAddress;
    private Socket client;
    private String myID;
    private int port;
    private DataOutputStream out;
    private OutputStream outToServer;
    private DataInputStream in;
    private static Logger logger = LogManager.getLogger(LobbyClient.class);
    private boolean conectedToHost = false;

    /**
     * Used to set up a connection with the lobby server.
     *
     * @param serverIP The ip address of the lobby host player, to which clients should connect.
     */
    public LobbyClient(String serverIP) {
        this.serverIp = serverIP;
        port = 4446;
        try {
            myIpAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to send a message to the lobby host player, so it can store the IP address of this player.
     */
    public void sendMyIp() throws IOException {
        client = new Socket(serverIp, port);
        logger.info("Just connected to " + client.getRemoteSocketAddress());
        outToServer = client.getOutputStream();
        out = new DataOutputStream(outToServer);
        out.writeUTF("Hello from " + client.getLocalSocketAddress());
        conectedToHost = true;
    }

    /**
     * Waits for a confirmation message from the host, that everyone has connected.
     * and the game is ready to be started
     */
    public void waitForGameToStart() throws IOException, ConnectionClosed {
        InputStream inFromServer = client.getInputStream();
        in = new DataInputStream(inFromServer);
        myID = in.readUTF();
        if (myID.equals("Host has cancelled the lobby")) {
            throw new ConnectionClosed();
        }
        logger.trace("My ID is now: " + myID);
        client.close();
    }

    /**
     * Cancels connection with the lobby host.
     */
    public void cancelConnection() throws IOException {
        if (in != null) {
            in.close();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client = new Socket(serverIp, port);
        outToServer = client.getOutputStream();
        out = new DataOutputStream(outToServer);
        out.writeUTF("cancel connection");
        client.close();
        logger.info("lobby client has cancelled the connection with lobby host. Closed the socket");

    }
}
