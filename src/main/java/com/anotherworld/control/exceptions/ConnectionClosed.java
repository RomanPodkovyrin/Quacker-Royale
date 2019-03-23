package com.anotherworld.control.exceptions;

public class ConnectionClosed extends Exception {
    public ConnectionClosed() {

    }

    public String toString() {
        return "Host has canceled the game";
    }
}
