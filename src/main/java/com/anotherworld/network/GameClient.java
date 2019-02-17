package com.anotherworld.network;

import java.io.IOException;
import java.net.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port = 4445;
    private String playersIPs[];

    public GameClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("10.42.0.1");
        System.out.println("Client ip: " +

                Inet4Address.getLocalHost().getHostAddress());
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

    public void closeSocket() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        int counter=0;
        while(true){
            counter++;
            client.sendDataToServer( "hello from anton"+counter);
            client.getDataFromServer();
        }
    }
}
