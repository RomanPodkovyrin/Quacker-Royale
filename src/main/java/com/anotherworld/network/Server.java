package com.anotherworld.network;

import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {
    private static Logger logger = LogManager.getLogger(Server.class);
    private DatagramSocket socket;
    private boolean serverIsRunning;
    private byte[] dataReceived;
    private int numberOfPlayers;
    private int port = 4445;
    private ArrayList<String> IPs;
    private ArrayList<Integer> clientsPorts;

    // Game data to be sent to client
    private PlayerData HostPlayer;
    private ArrayList<PlayerData> networkPlayers;
    private ArrayList<BallData> balls;
    private PlatformData platform;
    private WallData wall;
    private GameSessionData gamesession;


    public Server(int IPs, GameSettings settings) throws SocketException, UnknownHostException {
        HostPlayer = settings.getCurrentPlayer();
        networkPlayers = settings.getPlayers();

        balls = settings.getBalls();
        platform = settings.getPlatform().get(0);
        wall = settings.getWall().get(0);
        gamesession =settings.getGameSession();
        socket = new DatagramSocket(port);
        dataReceived = new byte[32];
        this.numberOfPlayers = IPs;
        this.clientsPorts = new ArrayList<Integer>();
        this.IPs = new ArrayList<String>();
        logger.info("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
    }

    public void run() {
        serverIsRunning = true;
        //get the ports of all the players before while starts
        for(int i = 0; i< numberOfPlayers; i++){
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            getFromClient(packet);
            logger.info("Client has connected on port " + packet.getPort());
            clientsPorts.add(packet.getPort());
            IPs.add(packet.getAddress().toString().substring(1));
        }
        logger.trace("Size of IPs " + IPs.size());
        try {
            sendStringToClient("game started".getBytes());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
//            sendObjectToClients(HostPlayer);
            logger.trace("Sending all the players");
            ArrayList<PlayerData> temp = new ArrayList<>();
            temp.addAll(networkPlayers);
            temp.add(HostPlayer);
            sendObjectToClients(temp);
            logger.trace("Sending all balls");
            sendObjectToClients(balls);
            logger.trace("Sending the platform");
            sendObjectToClients(platform);
            logger.trace("Sending the Wall");
            sendObjectToClients(wall);
            logger.trace("Sending the game session");
            sendObjectToClients(gamesession);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(serverIsRunning){
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            String keyPresses = getFromClient(packet);
            System.out.println(keyPresses);
            }

        socket.close();
    }

    public void sendStringToClient(byte[] dataToSend) throws UnknownHostException {
        for(int i = 0; i < numberOfPlayers; i++) {
            InetAddress playerIP = InetAddress.getByName(IPs.get(i));
            DatagramPacket packet
                    = new DatagramPacket(dataToSend, dataToSend.length, playerIP, clientsPorts.get(i));
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendObjectToClients(Object object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(object);
        byte[] data = outputStream.toByteArray();
//        logger.trace("Number of players: " + IPs.size());
        for(int i = 0; i < numberOfPlayers; i++) {
            logger.trace("i " + i + " Ips.get(i) " + IPs.get(i) + " " );
            InetAddress playerIP = InetAddress.getByName(IPs.get(i));
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, playerIP, clientsPorts.get(i));
            socket.send(sendPacket);
        }
    }

    public String getFromClient(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }


    public static void main(String[] args) throws SocketException, UnknownHostException {
        ArrayList<String> ips = new ArrayList<String>();
        ips.add("localhost");
//        new Server(ips).start();
    }
}
