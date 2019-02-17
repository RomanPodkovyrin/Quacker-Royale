package com.anotherworld.network;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean serverIsRunning;
    int counter = 0;
    private byte[] dataReceived;
    private int numberOfPlayers = 2;
    private String playersIPs[];

    public Server() throws SocketException, UnknownHostException {
        socket = new DatagramSocket(4445);
        dataReceived = new byte[32];
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
        playersIPs = new String[numberOfPlayers];
        playersIPs[0] = "10.42.0.133";
        playersIPs[1] = "10.42.0.1";
    }

    public void run() {
        serverIsRunning = true;
        while (serverIsRunning) {
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            String received = getFromClient(packet);
            System.out.println("From client to server: " + received);
            System.out.println(packet.getAddress());
            try {
                sendToClient((received).getBytes(), packet.getAddress(), packet.getPort());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
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

    public void sendToClient(byte[] dataToSend, InetAddress ipAddress, int port) throws UnknownHostException {
        for(int i = 0; i < numberOfPlayers; i++) {
            InetAddress playerIP = InetAddress.getByName(playersIPs[i]);
            DatagramPacket packet
                    = new DatagramPacket(dataToSend, dataToSend.length, playerIP, port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
