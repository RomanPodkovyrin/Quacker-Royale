package com.anotherworld.model.logic;

import com.anotherworld.model.ai.AI;
import com.anotherworld.model.movable.*;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
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
    private AI ai;
    private ArrayList<Ball> balls;
    private Platform platform;
    private Wall wall;

    public GameSession(PlayerData currentPlayer, ArrayList<PlayerData> players, ArrayList<PlayerData> ais,
            ArrayList<BallData> balls, PlatformData platform, WallData wall) {

        // Create the model of the current player.
        this.currentPlayer = new Player(currentPlayer, false);

        // Receive the data from the properties file.
        try {
            GameSession.properties = new PropertyReader("logic.properties");
            GameSession.numberOfBalls = Integer.parseInt(properties.getValue("NUMBER_OF_BALLS"));
        } catch (IOException e) {
            System.err.println("Error when loading properties class: " + e.getMessage());
        }

        this.players = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            this.players.add(new Player(players.get(i), false));
        }
        this.ais = new ArrayList<>();
        for (int i = 0; i < ais.size(); i++) {
            this.ais.add(new Player(ais.get(i), true));
        }
        this.balls = new ArrayList<>();
        for(int i = 0; i < balls.size(); i++) {
            this.balls.add(new Ball(balls.get(i)));
            balls.get(i).setVelocity(0, balls.get(i).getSpeed());
        }
        this.platform = new Platform(platform);
        this.wall = new Wall(wall);
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(this.ais);
        allPlayers.addAll(this.players);
        allPlayers.add(this.currentPlayer);
        this.ai = new AI(this.ais, allPlayers, this.balls, this.platform);
        Physics.setUp();
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){
        // Update the positions of the current player based on given input.

        //currentPlayer.setCoordinates(currentPlayer.getXCoordinate() + currentPlayer.getXVelocity(), currentPlayer.getYCoordinate() + currentPlayer.getYVelocity());
        // Update the positions of the other players.
        ai.action();
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(this.ais);
        allPlayers.addAll(this.players);
        allPlayers.add(this.currentPlayer);
        Physics.onCollision(this.balls, allPlayers, wall);
        
        Physics.move(currentPlayer);
        for (Player ai: ais) {
            Physics.move(ai);
        }
        for (Player player: players) {
            Physics.move(player);
        }
        for (Ball ball: balls) {
            Physics.move(ball);
        }
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
