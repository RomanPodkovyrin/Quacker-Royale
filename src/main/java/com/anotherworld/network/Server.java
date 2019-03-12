package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.anotherworld.tools.input.Input;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class allows one player to host the game,
 * so it received all the key presses from the client players
 * and sends them the objects of the game
 *
 * @author Antons Lebedjko
 */
public class Server extends Thread {
    private static Logger logger = LogManager.getLogger(Server.class);
    private DatagramSocket socket;
    private boolean serverIsRunning;
    private byte[] dataReceived;
    private int numberOfPlayers;
    private int port = 4445;
    private ArrayList<String> IPs;
    private ArrayList<Integer> clientsPorts;
    private HashMap<String, String> ipToID = new HashMap<>();

    // Game data to be sent to client
    private PlayerData HostPlayer;
    private ArrayList<PlayerData> networkPlayers;
    private ArrayList<BallData> balls;
    private PlatformData platform;
    private WallData wall;
    private GameSessionData gamesession;
    private ArrayList<Pair<ArrayList<Input>, String>> inputAndIP = new ArrayList<>();

    /**
     * Used to set up a connection with the clients
     *
     * @param IPs ip addresses of all the client players
     * @param settings game settings
     */
    public Server(int IPs, GameSettings settings) throws SocketException, UnknownHostException {
        HostPlayer = settings.getCurrentPlayer();
        networkPlayers = settings.getPlayers();
        // TODO should we also have ai in the multiplayer
        balls = settings.getBalls();
        platform = settings.getPlatform().get(0);
        wall = settings.getWall().get(0);
        gamesession =settings.getGameSession();
        socket = new DatagramSocket(port);
        dataReceived = new byte[10000];
        this.numberOfPlayers = IPs;
        this.clientsPorts = new ArrayList<Integer>();
        this.IPs = new ArrayList<String>();
        logger.info("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
    }

    /**
     * A run method for the thread which first of all gets all the ports of the clients,
     * sends clients ids, sends the initial objects of the game and after starts to receive
     * all the key presses which clients send while they are playing
     */
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
            sendPlayerID();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        sendInitialObjectsToClients();
        checkIfClientsHaveReceivedInitialObjects();
        while(serverIsRunning){
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            try {
                inputAndIP = getKeyPressesFromClient(packet);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to send data to all clients which re connected to the host
     *
     * @param dataToSend the data to be send to all clients
     */
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

    /**
     * Used to send the player id of each client
     */
    public void sendPlayerID() throws UnknownHostException {
        for(int i = 0; i < numberOfPlayers; i++) {
            byte[] dataToSend = networkPlayers.get(i).getObjectID().getBytes();
            ipToID.put(IPs.get(i), networkPlayers.get(i).getObjectID());
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

    /**
     * Used to send any game object to all clients
     *
     * @param object any game object
     */
    public void sendObjectToClients(Object object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(object);
        byte[] data = outputStream.toByteArray();
        for(int i = 0; i < numberOfPlayers; i++) {
            logger.trace("i " + i + " Ips.get(i) " + IPs.get(i) + " " );
            InetAddress playerIP = InetAddress.getByName(IPs.get(i));
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, playerIP, clientsPorts.get(i));
            socket.send(sendPacket);
        }
        networkPlayers.get(0).getObjectID();
    }

    /**
     * Used to receive a string from the client
     *
     * @param packet incoming packet from the client
     */
    public String getFromClient(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    /**
     * Used to receive the key presses from the client
     *
     * @param packet incoming packet with the key presses
     * @return the key presses with the player id from where they have came
     */
    private ArrayList<Pair<ArrayList<Input>, String>> getKeyPressesFromClient(DatagramPacket packet) throws ClassNotFoundException, IOException {
        try {
            socket.receive(packet);
        } catch (IOException e) {
            logger.error("Server socket has been closed");
        }
        byte data[] = packet.getData();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
        Object object = objectInputStream.readObject();
        ArrayList<Input> received = new ArrayList<>();
        received = (ArrayList<Input>) object;
        String ipFromWhereReceived = packet.getAddress().toString().substring(1);
        String id = ipToID.get(ipFromWhereReceived);
        logger.trace("Key press has been received from " + id);
        for (int i = 0; i < inputAndIP.size(); i++) {
            Pair<ArrayList<Input>, String> playerCommand = inputAndIP.get(i);
            if (playerCommand.getValue().equals(id)) {
                playerCommand.getKey().addAll(received);
                ArrayList<Input> temp = playerCommand.getKey();
                inputAndIP.clear();
                inputAndIP.add(new Pair<>(received,id));
                return inputAndIP;
            }
        }
        ArrayList<Pair<ArrayList<Input>, String>> keyPressesWithClientId = new ArrayList<>(inputAndIP);
        keyPressesWithClientId.add( new Pair<>(received, id));
        return keyPressesWithClientId;
    }

    /**
     * Checks if clients have received initial objects, so if they are ready to start playing
     */
    public void checkIfClientsHaveReceivedInitialObjects(){
        for(int i = 0; i< numberOfPlayers; i++) {
            DatagramPacket lastConfirmationPacket = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            getFromClient(lastConfirmationPacket);
        }
    }

    /**
     * Used to send all initial objects of the game to all clients
     */
    public void sendInitialObjectsToClients(){
        try {
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
    }

    /**
     * Used to clear the keyPresses and client IP variable
     *
     * @return the keyPresses and client IP
     */
    public ArrayList<Pair<ArrayList<Input>, String>> getInputAndIP(){
        ArrayList<Pair<ArrayList<Input>, String>> temp = new ArrayList<>(inputAndIP);
        inputAndIP.clear();
        return temp;
    }

    /**
     * Stops the communication with all clients and closes the socket
     */
    public void stopServer() {
        serverIsRunning = false;
        socket.close();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        ArrayList<String> ips = new ArrayList<String>();
        ips.add("localhost");
        new Server(1, new GameSettings(
                new PlayerData("h",2,1,1, ObjectState.IDLE,1,2),
                new ArrayList<>(Arrays.asList(new PlayerData("h",2,1,1, ObjectState.IDLE,1,2))),
                null,
                new ArrayList<>(Arrays.asList(new BallData("h",true,1,1, ObjectState.IDLE,1,2))),
                new ArrayList<>(Arrays.asList(new PlatformData(2,2))),
                new ArrayList<>(Arrays.asList(new WallData(2,2))),
                null)).start();
    }
}
