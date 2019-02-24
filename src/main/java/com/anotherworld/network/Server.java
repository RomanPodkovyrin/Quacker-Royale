package com.anotherworld.network;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {
    private static Logger logger = LogManager.getLogger(Server.class);
    private DatagramSocket socket;
    private boolean serverIsRunning;
    private byte[] dataReceived;
    private int numberOfPlayers;
    private int port = 4445;
    private ArrayList<String> IPs;

    public Server(ArrayList<String> IPs) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
        dataReceived = new byte[32];
        this.IPs = IPs;
        this.numberOfPlayers = IPs.size();
        System.out.println("Server Ip address: " + Inet4Address.getLocalHost().getHostAddress());
    }

    public void run() {
        serverIsRunning = true;
        while (serverIsRunning) {
            DatagramPacket packet = new DatagramPacket(this.dataReceived, this.dataReceived.length);
            String received = getFromClient(packet);
            logger.debug("The client port is: " + packet.getPort());
            logger.debug("The client address is: " + packet.getAddress());
            System.out.println("From client to server: " + received);
            BallData ballData = new BallData(true, 10, 10, ObjectState.IDLE, 15, 1);
            try {
                sendObjectToClient(ballData, packet.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public void sendStringToClient(byte[] dataToSend, int port) throws UnknownHostException {
        System.out.println("port is: " + port);
        for(int i = 0; i < numberOfPlayers; i++) {
            InetAddress playerIP = InetAddress.getByName(IPs.get(i));
            DatagramPacket packet
                    = new DatagramPacket(dataToSend, dataToSend.length, playerIP, port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendObjectToClient(Object object, int port) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(object);
        byte[] data = outputStream.toByteArray();
        for(int i = 0; i < numberOfPlayers; i++) {
            InetAddress playerIP = InetAddress.getByName(IPs.get(i));
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, playerIP, port);
            socket.send(sendPacket);
        }
    }

    public String getFromClient(DatagramPacket packet){
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

//    public static void main(String[] args) throws SocketException, UnknownHostException {
//        new Server().start();
//    }
}
