package com.anotherworld.network;

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
        address = InetAddress.getByName("172.22.84.8");
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

    public void sendObjectToServer(){
        TestingObject object = new TestingObject();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            byte[] Buf= baos.toByteArray();

            int number = Buf.length;;
            byte[] data = new byte[4];

            // int -> byte[]
            for (int i = 0; i < 4; ++i) {
                int shift = i << 3; // i * 8
                data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
            }
            DatagramPacket packet
                    = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            System.out.println("DONE SENDING");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            //client.sendDataToServer( "hello from lil anton" + counter);
            client.sendObjectToServer();
            client.getDataFromServer();
        }
    }
}
