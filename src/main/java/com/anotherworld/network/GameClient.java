package com.anotherworld.network;

import com.anotherworld.tools.datapool.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port = 4445;
    private BallData ballData;
    private PlayerData playerData;
    private GameSessionData gameSessionData;
    private PlatformData platformData;
    private WallData wallData;

    public GameClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        System.out.println("Client ip: " + Inet4Address.getLocalHost().getHostAddress());
        System.out.println();
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

    public void getDataFromServer(){
        byte[] data = new byte[32];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("From server: " + new String(packet.getData()));
    }

    public void getObjectFromServer() throws IOException, ClassNotFoundException {
        byte incomingData[] = new byte[1024];
        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        socket.receive(incomingPacket);
        byte data[] = incomingPacket.getData();
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
        Object object = objectInputStream.readObject();
        if(object instanceof BallData){
                ballData = (BallData) object;
                System.out.println("Ball object received. Is ball dangerous? : "+ballData.isDangerous());
                //BallData class update method
        } else if(object instanceof PlayerData) {
            playerData = (PlayerData) object;
            System.out.println("Player data object has been received");
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
        GameClient client = new GameClient();
        int counter=0;
        while(true){
            counter++;
            client.sendDataToServer( "hello from anton"+counter);
            //client.getDataFromServer();
            client.getObjectFromServer();
        }
    }
}
