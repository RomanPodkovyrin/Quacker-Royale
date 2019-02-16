package com.anotherworld.network;

import com.anotherworld.tools.datapool.BallData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
            byte[] incomingData = new byte[1024];
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
//            String received = getFromClient(packet);
//            System.out.println("From client to server: " + received);
            //start
            boolean received = false;
            try {
                received = getObjectFromClient(incomingPacket).isDangerous();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("From client to server: " + received);
            //end
            //String playerIP = packet.getAddress().toString().substring(1);
            String playerIP = "lalala";
            System.out.println("Ip address of a player: " + playerIP);
            updateIPaddresses(playerIP);
            try {
                sendToClient(("boolean was sent " + received).getBytes());
                //sendToClient((received).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        }
        close();
    }

    public void sendToClient(byte[] dataToSend) throws IOException {
        InetAddress playerIp = InetAddress.getByName(playersIPs[0]);
                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, multicastGroup, multicastPort);
                socket.send(packet);
        System.out.println("SEnding to client");
//        for(int i = 0; i<amountOfPlayers;i++){
//            if(playersIPs[i] != null) {
//                InetAddress playerIp = InetAddress.getByName(playersIPs[i]);
//                DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, playerIp, multicastPort);
//                socket.send(packet);
//            }
//        }

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

    public BallData getObjectFromClient(DatagramPacket incomingPacket) throws IOException, ClassNotFoundException {

        socket.receive(incomingPacket);
        byte data[] = incomingPacket.getData();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        System.out.println();
        BallData ballData = null;
        try {
            ballData = (BallData) is.readObject();
            System.out.println("Student object received = "+ballData);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ballData;
    }

    public void updateIPaddresses(String playerIP){
        //playersIPs[0] = playerIP;
        playersIPs[0] = "localhost";
        //playersIPs[1] = "192.168.0.21";
    }

    public void close(){
        socket.close();
        mSocket.close();
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        new Server().start();
    }
}

