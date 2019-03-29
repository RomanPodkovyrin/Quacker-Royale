package com.anotherworld.model.logic;

/**
 * Class for networking to look for a player within a list of players.
 *
 * @author Alfi S.
 */
public class PlayerNotFoundError extends Exception {

    String player;

    /**
     * The Exception's constructor.
     * @param player The player that was searched for.
     */
    public PlayerNotFoundError(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return player + " was not found in the list of players.";
    }
}
