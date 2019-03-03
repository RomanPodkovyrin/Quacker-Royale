package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.audio.SoundEffects;
import com.anotherworld.model.ai.AI;
import com.anotherworld.model.movable.*;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
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
    private GameSessionData gameSessionData;

    public GameSession(PlayerData currentPlayer,
                       ArrayList<PlayerData> players, ArrayList<PlayerData> ais, ArrayList<BallData> balls,
                       PlatformData platform, WallData wall,
                       GameSessionData gameSessionData) {

        this.gameSessionData = gameSessionData;
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
                    System.out.println(player.getCharacterID() + " collide with ball");
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


        collisionCheck();
        // Handling the time-based elements of the game

        gameSessionData.incrementTicksElapsed();
        logger.debug("ticksElapsed: " + gameSessionData.getTicksElapsed());

        if (gameSessionData.getTicksElapsed() % 60 == 0 && gameSessionData.getTimeLeft() > 0) {
            gameSessionData.decrementTimeLeft();
        }

        if (gameSessionData.getTimeLeft() < gameSessionData.getTimeToNextStage()*(platform.getStage()-1)){
            platform.nextStage();
            wall.nextStage();
        }

    }

    public void updatePlayer(ArrayList<Input> keyPresses) {
        updatePlayer(this.currentPlayer, keyPresses, this.gameSessionData);
    }

    /**
     * Updates the current player's velocity based on the given list of inputs.
     * @param keyPresses
     */
    public static void updatePlayer(Player player, ArrayList<Input> keyPresses, GameSessionData gameData) {
        if (keyPresses.contains(Input.CHARGE)) {
            
            player.setVelocity(0, 0);
            long timeSpentCharging = gameData.getTicksElapsed() - player.getTimeStartedCharging();

            if(player.getChargeLevel() < GameSettings.getDefaultPlayerMaxCharge()
                    && timeSpentCharging % 60 == 0) {
                if (player.getState() != ObjectState.CHARGING) {
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.CHARGING);
                }
                
                player.incrementChargeLevel();
            }
        } else if (player.getState() == ObjectState.CHARGING) {
            if (keyPresses.contains(Input.UP)) player.setAngle(0);
            else if (keyPresses.contains(Input.DOWN)) player.setAngle(180);
            else player.setAngle(0);;

            if (keyPresses.contains(Input.LEFT)) player.setAngle(270);
            else if (keyPresses.contains(Input.RIGHT)) player.setAngle(90);
            else player.setAngle(0);
            System.out.println("DASH!");
            player.setState(ObjectState.DASHING);
            Physics.charge(player);
            player.setTimeStartedCharging(0);
        } 
        else if (player.getState().equals(ObjectState.DASHING)) {
            if(player.getChargeLevel()>0)
                Physics.charge(player);
            else{
                player.setAngle(0);
                player.setState(ObjectState.IDLE);
            }
        }
            else {
            if (keyPresses.contains(Input.UP)) player.setYVelocity(-player.getSpeed());
            else if (keyPresses.contains(Input.DOWN)) player.setYVelocity(player.getSpeed());
            else player.setYVelocity(0);

            if (keyPresses.contains(Input.LEFT)) player.setXVelocity(-player.getSpeed());
            else if (keyPresses.contains(Input.RIGHT)) player.setXVelocity(player.getSpeed());
            else player.setXVelocity(0);
        }
    }
}
