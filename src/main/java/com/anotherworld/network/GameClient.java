package com.anotherworld.network;

import com.anotherworld.tools.datapool.BallData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port = 4445;
    private String playersIPs[];

    public GameClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        System.out.println("Client ip: " + Inet4Address.getLocalHost().getHostAddress());
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

    public Object getObjectFromServer() throws IOException{
        byte incomingData[] = new byte[1024];
        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        socket.receive(incomingPacket);
        byte data[] = incomingPacket.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        System.out.println();
        BallData ballData = null;
        try {
            ballData = (BallData) is.readObject();
            System.out.println("Ball object received = "+ballData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ballData;
    }

    public void closeSocket() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
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
