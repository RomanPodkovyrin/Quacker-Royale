package com.anotherworld.model.logic;

public class PlayerNotFoundError extends Exception {

    String player;

    public PlayerNotFoundError(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return player + " was not found in the list of players.";
    }
}
