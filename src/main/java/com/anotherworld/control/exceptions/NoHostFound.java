package com.anotherworld.control.exceptions;

public class NoHostFound extends Exception {
    public NoHostFound() {

    }

    public String toString(){
        return "Client could not find and connect to Host";
    }
}
