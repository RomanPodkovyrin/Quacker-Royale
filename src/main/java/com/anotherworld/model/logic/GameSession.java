package com.anotherworld.model.logic;

import com.anotherworld.model.movable.*;
import com.anotherworld.tools.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;

public class GameSession {
    private static PropertyReader properties;
    private static int numberOfBalls;
    private ArrayList<Player> players;
    private ArrayList<Player> ais;
    private ArrayList<Ball> balls;

    public GameSession(ArrayList<Player> players, ArrayList<Player> ais) {
        try {
            this.properties = new PropertyReader("logic.properties");
            this.numberOfBalls = Integer.parseInt(properties.getValue("NUMBER_OF_BALLS"));
        } catch (IOException e) {
            System.err.println("Error when loading properties class: " + e.getMessage());
        }
        this.players = players; //Create the list of players
        this.ais = ais;
        for(int i = 0; i < numberOfBalls; i++) {
            //Instantiate a new ball at a random? location
        }
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){

    }
}
