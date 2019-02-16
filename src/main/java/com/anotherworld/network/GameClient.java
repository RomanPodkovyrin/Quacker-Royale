package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int multicastPort = 4445;
    private int port = 4446;
    private String multicastIP = "228.5.6.7";
    MulticastSocket s;
    InetAddress group;
    private byte[] dataToSend;

    public GameClient() throws IOException {
        s = new MulticastSocket(multicastPort);
        group = InetAddress.getByName(multicastIP);
        s.joinGroup(group);
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        System.out.println("Client address : " + Inet4Address.getLocalHost().getHostAddress());
    }

    public void sendDataToServer(String msg) throws IOException {
        dataToSend = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(dataToSend, dataToSend.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObjectToServer() throws IOException {
        BallData ballData = new BallData(true, 10, 10, ObjectState.IDLE, 15, 1);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(ballData);
        byte[] data = outputStream.toByteArray();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
        socket.send(sendPacket);
        System.out.println("Message sent from client" );
    }

    public void getDataFromServer() throws IOException {
        byte[] data = new byte[32];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        s.receive(packet);

        System.out.println("From server: " + new String(packet.getData()));
    }

    public void close() {
        socket.close();
        s.close();
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        int counter=0;
        while(true){
            counter++;
            System.out.println("hllo");
            //client.sendDataToServer( "hello from lil anton" + counter);
            client.sendObjectToServer();
            client.getDataFromServer();
        }
    }
}

