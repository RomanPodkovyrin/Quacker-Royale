package com.anotherworld.tools.exceptions;

/**
 * This exception tells that the multiplayer connection of the game was interrupted.
 * @author roman
 */
public class ConnectionClosed extends Exception {
    public ConnectionClosed() {

    }

    public String toString() {
        return "Host has canceled the game";
    }
}
