package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.audio.SoundEffects;
import com.anotherworld.model.ai.AI;
import com.anotherworld.model.movable.*;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.Input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


/**
 * A class that models a game session.
 * @author Alfi S.
 */
public class GameSession {

    private static Logger logger = LogManager.getLogger(GameSession.class);

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

        this.currentPlayer = new Player(currentPlayer, false);

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
            newBall.setVelocity(1* newBall.getSpeed(), newBall.getSpeed());
            this.balls.add(newBall);
        }

        this.platform = new Platform(platform);
        this.wall = new Wall(wall);

        this.ai = new AI(this.ais, this.allPlayers, this.balls, this.platform);

        Physics.setUp();
    }

    /**
     * Function that checks and applies all the collisions within the game.
     * First checks each ball for a collisions with:
     *      (i)   a wall.
     *      (ii)  a player.
     *      (iii) another ball.
     * Then checks a player for collisions with:
     *      (i)   another player.
     *      (ii)  outside of the platform.
     */
    private void collisionCheck() {
        for(Ball ball : this.balls) {

            // Check if a ball has collided with the wall.
            if (Physics.bouncedWall(ball, this.wall)) {
                AudioControl.ballCollidedWithWall();
            }

            // Check if a ball has collided with a player.
            for (Player player : this.allPlayers) {
                if (player.isDead()) continue;

                if(Physics.checkCollision(ball, player)) {

                    AudioControl.playerCollidedWithBall();

                    if (!ball.isDangerous()){
                        ball.setDangerous(true);
                        ball.setTimer(GameSettings.getBallMaxTimer());
                        ball.setSpeed(ball.getSpeed() * 2);
                    } else player.damage(ball.getDamage());

                    Physics.collided(ball, player);
                }
            }

            // Check if a ball has collided with another ball.
            for (Ball ballB : this.balls) {
                if (!ball.equals(ballB) && Physics.checkCollision(ball, ballB)){
                    Physics.collided(ball, ballB);
                }
            }
        }

        for (Player playerA : this.allPlayers) {

            if (playerA.isDead()) continue;

            // Check if a player has collided with another player.
            for (Player playerB : this.allPlayers) {

                if (playerB.isDead()) continue;
                
                if(!playerA.equals(playerB)
                        && Physics.checkCollision(playerA, playerB)) {
                    Physics.collided(playerA, playerB);

                }
            }

            // Kill the player if they fall off the edge of the platform
            if(!platform.isOnPlatform(playerA)) {
                playerA.kill();
                logger.debug(playerA.getCharacterID() + " fell off");
            }
        }
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){

        ai.action();
        collisionCheck();

        for (Player player : allPlayers) {
            Physics.move(player);
            if (player.getHealth() <= 0) player.kill();
        }

        // Move all the balls based on velocity and decrement their timers.
        for (Ball ball: balls) {
            Physics.move(ball);

            // Handle the danger state of the balls.
            if (ball.isDangerous()) {
                ball.reduceTimer(GameSettings.getBallTimerDecrement());
                if (ball.getTimer() <= 0) {
                    ball.setDangerous(false);
                    ball.setSpeed(GameSettings.getDefaultBallSpeed());
                }
            }
        }

    }

    /**
     * Updates the current player's velocity based on the given list of inputs.
     * @param keyPresses
     */
    public void updatePlayer(ArrayList<Input> keyPresses) {
        // TODO think about the way to control network players
        if (keyPresses.contains(Input.CHARGE)) {
            //TODO: Implement charge action.
        } else {
            if (keyPresses.contains(Input.UP)) currentPlayer.setYVelocity(-currentPlayer.getSpeed());
            else if (keyPresses.contains(Input.DOWN)) currentPlayer.setYVelocity(currentPlayer.getSpeed());
            else currentPlayer.setYVelocity(0);

            if (keyPresses.contains(Input.LEFT)) currentPlayer.setXVelocity(-currentPlayer.getSpeed());
            else if (keyPresses.contains(Input.RIGHT)) currentPlayer.setXVelocity(currentPlayer.getSpeed());
            else currentPlayer.setXVelocity(0);
        }
    }
}
