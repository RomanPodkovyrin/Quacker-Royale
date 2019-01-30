package com.anotherworld.main.network;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean serverIsRunning;
    int counter = 0;
    byte[] dataReceived;

    public Server() throws SocketException, UnknownHostException {
        socket = new DatagramSocket(4445);
        dataReceived = new byte[32];
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());

    }

    public void run() {
        serverIsRunning = true;
        while (serverIsRunning) {
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
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
                serverIsRunning = false;
                continue;
            }
        }
        socket.close();
    }

    public void sendToClient(byte[] dataToSend, InetAddress ipAddress, int port){
        DatagramPacket packet
                = new DatagramPacket(dataToSend, dataToSend.length, ipAddress, port);
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
