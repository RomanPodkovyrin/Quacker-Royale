package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.anotherworld.tools.input.Input;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

//        socket.close();
    }

    public void stopServer() {
        serverIsRunning = false;
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

    public String getFromClient(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

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
//                inputAndIP.set(i,new Pair<>(temp,id));

                // testing
                inputAndIP.clear();
                inputAndIP.add(new Pair<>(received,id));
                return inputAndIP;
            }
        }
        ArrayList<Pair<ArrayList<Input>, String>> returnValue = new ArrayList<>(inputAndIP);
        returnValue.add( new Pair<>(received, id));
        return returnValue;
    }

    public ArrayList<Pair<ArrayList<Input>, String>> getInputAndIP(){
        ArrayList<Pair<ArrayList<Input>, String>> temp = new ArrayList<>(inputAndIP);
        inputAndIP.clear();
        return temp;
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
