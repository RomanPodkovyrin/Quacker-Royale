package com.anotherworld.main.network;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    int counter = 0;

    public Server() throws SocketException, UnknownHostException {
        socket = new DatagramSocket(4445);
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());

    }

    public void run() {
        running = true;
        while (running) {
            byte[] data = new byte[32];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            String received = getFromClient(packet);
            System.out.println("From client to server: " + received);
            System.out.println(packet.getAddress());
            sendToClient((received).getBytes(), packet.getAddress(), packet.getPort());
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            if (received.equals("end")) {
                running = false;
                continue;
            }
        }
        socket.close();
    }

    public void sendToClient(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet
                = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFromClient(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String messageFromClient = new String(packet.getData());
        return messageFromClient;
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        new Server().start();
    }
}

