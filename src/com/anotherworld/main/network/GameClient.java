package com.anotherworld.main.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;

public class GameClient {
    private DatagramSocket socket;
    private InetAddress serverIpAddress;
    private int port = 4445;

    public GameClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        serverIpAddress = InetAddress.getByName("172.22.84.8");
        informServerAboutConnection();
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
    }

    public void informServerAboutConnection() throws UnknownHostException {
        byte[] clientIP = Inet4Address.getLocalHost().getHostAddress().getBytes();
        DatagramPacket packet
                = new DatagramPacket(clientIP, clientIP.length, serverIpAddress, port);
        sendPacket(packet);
    }

    public void sendDataToServer(String msg) {
        byte[] data = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(data, data.length, serverIpAddress, port);
        sendPacket(packet);
    }

    public void sendPacket(DatagramPacket packet) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitGameToStartTCP() throws IOException {
        boolean gameStarted = false;
        while(!gameStarted) {
            String sentence;
            String modifiedSentence;
            //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket(serverIpAddress, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //sentence = inFromUser.readLine();
            System.out.println("line 53?");
            outToServer.writeBytes("yo");
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            if (modifiedSentence.equals("1")) {
                gameStarted = true;
                clientSocket.close();
            }
        }

    }

    public void waitGameToStart(){
        boolean gameStarted = false;
        while(!gameStarted) {
            sendDataToServer("not ip");
            byte[] status = new byte[30];
            DatagramPacket packet = new DatagramPacket(status, status.length);
            System.out.println("Did we get a packet?");
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String receivedStatus = new String(packet.getData());
            System.out.println("Received status is: " + receivedStatus);
            if (receivedStatus.equals("1"))
                gameStarted = true;
            System.out.println(receivedStatus);
        }
        System.out.println("we are still in a loop?");
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
        //client.waitGameToStartTCP();
        System.out.println("Game started!!!!");
        int counter=0;
        while(true){
            counter++;
            client.sendDataToServer( "hi from antoxaaxaa"+counter);
            client.getDataFromServer();
        }
    }
}
