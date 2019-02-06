package com.anotherworld.model.logic;

import com.anotherworld.model.movable.*;
import com.anotherworld.tools.PropertyReader;
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
    private ArrayList<Player> players;
    private ArrayList<Player> ais;
    private ArrayList<Ball> balls;

    public GameSession(Player currentPlayer,
                       ArrayList<Player> players, ArrayList<Player> ais) {

        // Receive the data from the properties file.
        try {
            this.properties = new PropertyReader("logic.properties");
            this.numberOfBalls = Integer.parseInt(properties.getValue("NUMBER_OF_BALLS"));
        } catch (IOException e) {
            System.err.println("Error when loading properties class: " + e.getMessage());
        }

        this.currentPlayer = currentPlayer;
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
        if (keyPresses.contains(Input.UP)) System.out.println("Up is pressed!");
        if (keyPresses.contains(Input.DOWN)) System.out.println("Down is pressed!");
        if (keyPresses.contains(Input.LEFT)) System.out.println("Left is pressed!");
        if (keyPresses.contains(Input.RIGHT)) System.out.println("Right is pressed!");
    }
}
