package com.anotherworld.network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckMyIp {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("1: " + inetAddress.getHostAddress());
        System.out.println("2: " + inetAddress.getCanonicalHostName());
        System.out.println("3: " + Inet4Address.getLocalHost());
        System.out.println("4: " + Inet6Address.getLocalHost());
    }
}
