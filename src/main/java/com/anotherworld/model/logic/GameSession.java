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
    private ArrayList<Player> allPlayers;

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
        for(PlayerData data : players) this.players.add(new Player(data, false));

        this.ais = new ArrayList<>();
        for(PlayerData data : ais) this.ais.add(new Player(data, true));

        this.allPlayers = new ArrayList<>();
        this.allPlayers.addAll(this.ais);
        this.allPlayers.addAll(this.players);
        this.allPlayers.add(this.currentPlayer);

        this.balls = new ArrayList<>();
        for(BallData data : balls) {
            Ball newBall = new Ball(data);
            newBall.setVelocity(0, newBall.getSpeed());
            this.balls.add(newBall);
        }

        this.platform = new Platform(platform);
        this.wall = new Wall(wall);

        this.ai = new AI(this.ais, this.allPlayers, this.balls, this.platform);

        Physics.setUp();
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){
        ai.action();

        Physics.onCollision2ElectricBoogaloo(this.balls, this.allPlayers, this.wall);

        for(Player player : allPlayers){
            if(!platform.isOnPlatform(player)) player.setState(ObjectState.DEAD);
            //TODO: If the player object turns out to not be needed at the end just delete it.

        }

        Physics.move(currentPlayer);
        for (Player ai: ais) Physics.move(ai);
        for (Player player: players) Physics.move(player);
        for (Ball ball: balls) Physics.move(ball);

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
