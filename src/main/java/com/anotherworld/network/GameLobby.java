package com.anotherworld.network;

import java.util.ArrayList;

public class GameLobby {

    /**
     *
     * @param isHost true if a host false if a client
     */
    public GameLobby(boolean isHost) {
        if (isHost) {
            // start a client
        } else {
            // connect to a client
        }

    }

    /**
     * Returns the list of players which are currently connected to the host
     *
     * @return
     */
    public ArrayList<String> getNetworkPlayers() {
        return null;
    }
}
