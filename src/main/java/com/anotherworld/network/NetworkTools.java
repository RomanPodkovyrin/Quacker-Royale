package com.anotherworld.network;

import java.net.*;
import java.util.Enumeration;

public class NetworkTools {


    /**
     * Returns the current ip of the network car of the machine.
     * @return ip address
     */
    public static String getMyIP() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces( );
            NetworkInterface netInt = interfaces.nextElement();
            for ( final InterfaceAddress addr : netInt.getInterfaceAddresses( ) )
            {
                final InetAddress inetAddr = addr.getAddress( );

                if ( !( inetAddr instanceof Inet4Address ) ) {
                    continue;
                }
                return inetAddr.getHostAddress( );
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "IP NOT FOUND";

    }

    public static void main(String args[]) {

        System.out.println(getMyIP());
    }
}
