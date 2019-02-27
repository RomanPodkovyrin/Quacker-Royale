package com.anotherworld.network;

import com.anotherworld.tools.datapool.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.ArrayList;

public class GameClient extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    private int port = 4445;
    private ArrayList<BallData> ballData;
    private ArrayList<PlayerData> playerData;
    private GameSessionData gameSessionData;
    private PlatformData platformData;
    private WallData wallData;
    private PlayerData clientPlayer;
    private String myID;

    public GameClient(String serverIP) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(serverIP);
        System.out.println("Client ip: " + Inet4Address.getLocalHost().getHostAddress());
        sendDataToServer("set up connection message");
        waitForGameToStart();
//        start();
    }

    public void run(){
        while(true){
            try {
                getObjectFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDataToServer(String msg) {
        byte[] data = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(data, data.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForGameToStart(){
        byte[] data = new byte[16];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("My id is:" + received);
    }

    public void getObjectFromServer() throws IOException, ClassNotFoundException {
        byte incomingData[] = new byte[1024];
        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        socket.receive(incomingPacket);
        byte data[] = incomingPacket.getData();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
        Object object = objectInputStream.readObject();
        if(object instanceof ArrayList<?>) {
            System.out.println("ArrayList has been received");
            System.out.println(" array : " + object);
            ArrayList<?> ballOrPlayer = ((ArrayList<?>)object);
            if(ballOrPlayer.get(0) instanceof PlayerData){
                playerData = (ArrayList<PlayerData>) ballOrPlayer;
                for (int i = 0; i < playerData.size(); i ++ ) {
                    if (playerData.get(i).getObjectID().equals(myID)) {
                        clientPlayer = playerData.get(i);
                        playerData.remove(i);
                    }
                }
                System.out.println("Player data object has been received");
            } else if(ballOrPlayer.get(0) instanceof BallData){
                ballData = (ArrayList<BallData>) ballOrPlayer;
                System.out.println("Ball object received.");
            }
        } else if(object instanceof GameSessionData){
            gameSessionData = (GameSessionData) object;
            System.out.println("GameSessionData object has been received");
        } else if(object instanceof PlatformData){
            platformData = (PlatformData) object;
            System.out.println("PlatformData object has been received");
        } else if(object instanceof WallData){
            wallData = (WallData) object;
            System.out.println("WallData object has been received");
        }
    }

    public void closeSocket() {
        socket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GameClient client = new GameClient("10.42.0.1");
        int counter=0;
        while(true){
            counter++;
            client.sendDataToServer( "hello from anton "+counter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
