package com.anotherworld.main.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private ServerSocket TCPsocket;
    private boolean serverIsRunning;
    int counter = 0;
    byte[] dataReceived;
    int playersAmount;
    String playersIPAddresses[];
    boolean allPlayersJoined;
    int port = 4445;

    public Server(int playersAmount) throws IOException {
        socket = new DatagramSocket(port);
        TCPsocket = new ServerSocket(port);
        dataReceived = new byte[32];
        this.playersAmount = playersAmount;
        playersIPAddresses = new String[this.playersAmount];
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
    }

    public void run() {
        havePlayersConnected();
        System.out.println("We reached line 33");
        for(int i =0; i<playersAmount; i++){
            System.out.println(playersIPAddresses[i] + " ip address of player " + i+1);
        }
//        try {
//            informClientsGameStartedTCP();
//            System.out.println("succesfull?");
//        } catch (IOException e) {
//            System.out.println("or not?");
//            e.printStackTrace();
//        }
        serverIsRunning = true;
        System.out.println("does it go on 51?");
        while (serverIsRunning) {
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            String received = getFromClient(packet);
            System.out.println("From client to server: " + received);
            //System.out.println(packet.getAddress());
            try {
                sendToClient((received).getBytes(), packet.getAddress(), packet.getPort());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(300);
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
        for(int i =0; i<playersAmount; i++){
            System.out.println(i + "      :" +InetAddress.getByName(playersIPAddresses[i]));
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, InetAddress.getByName(playersIPAddresses[i]), port);
            sendPacket(packet);

        }
    }

    public void informClientsGameStartedTCP() throws IOException {
            Socket connectionSocket = TCPsocket.accept();
            String clientSentence;
            String capitalizedSentence;
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + 'n';
            outToClient.writeBytes("1");
    }

    public void informClientsGameStartedUDP() throws UnknownHostException {
        String status = "1";
        for(int i =0; i<playersAmount; i++){
            DatagramPacket packet = new DatagramPacket(status.getBytes(), status.getBytes().length, InetAddress.getByName(playersIPAddresses[i]), port);
            sendPacket(packet);
        }
        serverIsRunning = true;
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

    public String receiveClientIp(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buffer = packet.getData();
        System.out.println("ip is: " + new String(buffer, 0, packet.getLength()));
        return new String(buffer, 0, packet.getLength());
    }

    public void havePlayersConnected(){
        int clientIndex = 0;
        while (!allPlayersJoined) {
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            String received = receiveClientIp(packet);
            System.out.println("Received" + received);
            if(!received.equals("not ip")) {
                playersIPAddresses[clientIndex] = received;
                clientIndex++;
                if (clientIndex == playersAmount)
                    allPlayersJoined = true;
                System.out.println(received);
                System.out.println("Have all players connected? " + allPlayersJoined);
            }
        }
    }

    public void sendPacket(DatagramPacket packet){
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(1).start();
    }
}
