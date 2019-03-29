package com.anotherworld.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkTools {


    /**
     * Returns the current ip of the network car of the machine.
     * @return ip address
     */
    public static String getMyIP() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface netInt = interfaces.nextElement();
            for (final InterfaceAddress addr : netInt.getInterfaceAddresses()) {
                final InetAddress inetAddr = addr.getAddress();

                if (!(inetAddr instanceof Inet4Address)) {
                    continue;
                }
                return inetAddr.getHostAddress();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "IP NOT FOUND";

    }


}
