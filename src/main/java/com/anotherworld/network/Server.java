package com.anotherworld.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private DatagramSocket mSocket;
    private InetAddress multicastGroup;
    private boolean running;
    int counter = 0;
    private int multicastPort = 4445;
    private int port = 4446;
    private String multicastIP = "228.5.6.7";
    private String[] playersIPs;
    private int amountOfPlayers = 2;

    public Server() throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
        mSocket = new DatagramSocket();
        playersIPs = new String[amountOfPlayers];
        multicastGroup = InetAddress.getByName(multicastIP);
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
    }

    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] data = new byte[32];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            String received = getFromClient(packet);
            System.out.println("From client to server: " + received);
            String playerIP = packet.getAddress().toString().substring(1);
            System.out.println("Ip address of a player: " + playerIP);
            updateIPaddresses(playerIP);
            try {
                sendToClient((received).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
            if (received.equals("end")) {
                running = false;
                continue;
            }
        }
        close();
    }

    public void sendToClient(byte[] dataToSend) throws IOException {
//        InetAddress playerIp = InetAddress.getByName(playersIPs[0]);
//                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, playerIp, multicastPort);
//                socket.send(packet);
        for(int i = 0; i<amountOfPlayers;i++){
            if(playersIPs[i] != null) {
                InetAddress playerIp = InetAddress.getByName(playersIPs[i]);
                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, playerIp, multicastPort);
                socket.send(packet);
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

    public void updateIPaddresses(String playerIP){
        playersIPs[0] = playerIP;
        playersIPs[1] = "192.168.43.150";
    }
//        if(playersIPs[0]==null){
//            playersIPs[0] = playerIP;
//            return;
//        }
//        else if(playersIPs[1] ==null){
//            playersIPs[1] = playerIP;
//            return;
//        }
//
//        if(!playersIPs[0].equals(playerIP))
//            playersIPs[1] = playerIP;
//    }

    public void close(){
        socket.close();
        mSocket.close();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        new Server().start();
    }
}
