package com.anotherworld.network;

import java.util.ArrayList;

public class GameLobby {
    private boolean isHost;

    /**
     *
     * @param isHost true if a host false if a client
     */
    public GameLobby(boolean isHost) {
        this.isHost = isHost;
        if (isHost) {
            // start a client
        } else {
            // connect to a client
        }

    }

    public boolean isHost() {
        return isHost;
    }

    public boolean isConnected() {
        return false;
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
