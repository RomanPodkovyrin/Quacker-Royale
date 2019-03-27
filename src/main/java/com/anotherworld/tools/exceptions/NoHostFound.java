package com.anotherworld.tools.exceptions;

/**
 * This exception tells that client could not find the host to connect to.
 * @author roman
 */
public class NoHostFound extends Exception {
    public NoHostFound() {

    }

    public String toString() {
        return "Client could not find and connect to Host";
    }
}
