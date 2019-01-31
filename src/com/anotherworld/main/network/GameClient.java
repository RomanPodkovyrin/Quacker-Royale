package com.anotherworld.main.network;

import java.io.IOException;
import java.net.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int multicastPort = 4445;
    private int port = 4446;
    private String multicastIP = "228.5.6.7";
    MulticastSocket s;
    InetAddress group;
    private byte[] buf;

    public GameClient() throws IOException {
        s = new MulticastSocket(multicastPort);
        group = InetAddress.getByName(multicastIP);
        s.joinGroup(group);
        socket = new DatagramSocket();
        address = InetAddress.getByName("172.20.10.3");
        System.out.println(address);
    }

    public void sendDataToServer(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("just have sent a message to server");
    }

    public void getDataFromServer() throws IOException {
        byte[] data = new byte[32];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        s.receive(packet);
        System.out.println("From server: " + new String(packet.getData()));
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        int counter=0;
        while(true){
            counter++;
            client.sendDataToServer( "hello from chi ho"+counter);
            client.getDataFromServer();
        }
    }
}
