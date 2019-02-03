package com.anotherworld.main.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.*;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    int counter = 0;
    private int multicastPort = 4445;
    private String multicastIP = "228.5.6.7";
    private int port = 4446;

    public Server() throws SocketException, UnknownHostException {
            socket = new DatagramSocket(port);
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
                try {
                    sendToClient((received).getBytes(), packet.getAddress(), packet.getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        public void sendToClient(byte[] data, InetAddress ipAddress, int port) throws IOException {
            byte[] buf;
            DatagramSocket mSocket = new DatagramSocket();
            InetAddress group = InetAddress.getByName(multicastIP);
            DatagramPacket packet
                    = new DatagramPacket(data, data.length, group, multicastPort);
            socket.send(packet);
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
