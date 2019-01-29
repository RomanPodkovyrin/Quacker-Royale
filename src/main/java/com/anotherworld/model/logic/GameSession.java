package com.anotherworld.model.logic;

import com.anotherworld.model.movable.*;

import java.util.ArrayList;

public class GameSession {
    private ArrayList<Player> players;
    private ArrayList<Player> ais;
    private ArrayList<Ball> balls;

    public GameSession(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){

    }
}
