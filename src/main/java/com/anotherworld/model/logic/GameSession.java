package com.anotherworld.model.logic;

import com.anotherworld.model.movable.*;
import com.anotherworld.model.physics.Physics;
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
    private ArrayList<Player> players;
    private ArrayList<Player> ais;
    private ArrayList<Ball> balls;

    public GameSession(PlayerData currentPlayer,
                       ArrayList<PlayerData> players, ArrayList<PlayerData> ais) {

        // Create the model of the current player.
        this.currentPlayer = new Player(currentPlayer, false);

        // Setting the list of human-playable characters.
        this.players = new ArrayList<Player>();
        for(PlayerData data : players) this.players.add(new Player(data, false));

        // Setting the list of ai-controlled characters.
        this.ais = new ArrayList<Player>();
        for(PlayerData data : ais) this.ais.add(new Player(data, true));

        // Receive the data from the properties file.
        try {
            this.properties = new PropertyReader("logic.properties");
            this.numberOfBalls = Integer.parseInt(properties.getValue("NUMBER_OF_BALLS"));
        } catch (IOException e) {
            System.err.println("Error when loading properties class: " + e.getMessage());
        }

    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){
        // Update the positions of the current player based on given input.
        Physics.move(currentPlayer);

        // Update the positions of the other players.

        // Check whether or not the players are within the arena.

        // Check whether or not the players are colliding with a ball

        // Check whether or not the balls are colliding with a wall

    }

    public void updatePlayer(ArrayList<Input> keyPresses) {
        if (keyPresses.contains(Input.UP)) currentPlayer.setYVelocity(-currentPlayer.getSpeed());
        else if (keyPresses.contains(Input.DOWN)) currentPlayer.setYVelocity(currentPlayer.getSpeed());
        else currentPlayer.setYVelocity(0);

        if (keyPresses.contains(Input.LEFT)) currentPlayer.setXVelocity(-currentPlayer.getSpeed());
        else if (keyPresses.contains(Input.RIGHT)) currentPlayer.setXVelocity( currentPlayer.getSpeed());
        else currentPlayer.setXVelocity(0);
    }
}
