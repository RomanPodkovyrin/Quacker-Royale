package com.anotherworld.model.logic;

import com.anotherworld.model.movable.*;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.Input;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that models a game session.
 * @author Alfi S.
 */
public class GameSession {
    private static PropertyReader properties;
    private static int numberOfBalls;
    private Player currentPlayer;
    private ArrayList<PlayerData> players;
    private ArrayList<PlayerData> ais;
    private ArrayList<Ball> balls;

    public GameSession(PlayerData currentPlayer,
                       ArrayList<PlayerData> players, ArrayList<PlayerData> ais) {

        this.currentPlayer = new Player(currentPlayer, false);
        // Receive the data from the properties file.
        try {
            this.properties = new PropertyReader("logic.properties");
            this.numberOfBalls = Integer.parseInt(properties.getValue("NUMBER_OF_BALLS"));
        } catch (IOException e) {
            System.err.println("Error when loading properties class: " + e.getMessage());
        }

        this.players = players; //Create the list of players
        this.ais = ais;

        for(int i = 0; i < numberOfBalls; i++) {
            //Instantiate a new ball at a random(?) location
        }
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){
        // Update the positions of the current player based on given input.

        // Update the positions of the other players.

        // Check whether or not the players are within the arena.

        // Check whether or not the players are colliding with a ball

        // Check whether or not the balls are colliding with a wall

    }

    public void updatePlayer(ArrayList<Input> keyPresses) {
        if (keyPresses.contains(Input.UP))    currentPlayer.setYVelocity(-currentPlayer.getSpeed());
        if (keyPresses.contains(Input.DOWN))  currentPlayer.setYVelocity( currentPlayer.getSpeed());
        if (keyPresses.contains(Input.LEFT))  currentPlayer.setXVelocity(-currentPlayer.getSpeed());
        if (keyPresses.contains(Input.RIGHT)) currentPlayer.setXVelocity( currentPlayer.getSpeed());
    }
}
