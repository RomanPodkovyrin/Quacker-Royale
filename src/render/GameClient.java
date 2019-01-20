package render;

import java.net.*;

public class GameClient extends Thread{


    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Server server;

    public GameClient(Server server, String ipAddress){
        this.server = server;

        try{
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        }
        catch (SocketException e){
            e.printStackTrace();
        }
        catch (UnknownHostException e){
            e.printStackTrace();
        }
    }



    public static void main(String args[]){
        System.out.println("yoClient");
    }

}
