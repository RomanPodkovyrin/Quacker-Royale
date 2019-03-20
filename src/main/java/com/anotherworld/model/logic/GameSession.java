package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.ai.AI;
import com.anotherworld.model.movable.*;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.*;
import com.anotherworld.tools.input.Input;

import com.anotherworld.tools.maths.Maths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


/**
 * A class that models a game session.
 * @author Alfi S.
 */
public class GameSession {

    private static Logger logger = LogManager.getLogger(GameSession.class);

    private PlayerData currentPlayer;
    private ArrayList<PlayerData> allPlayers;
    private ArrayList<PlayerData> livingPlayers;

    private AI ai;
    private ArrayList<BallData> balls;

    private Platform platform;
    private Wall wall;

    private GameSessionData gameData;

    public GameSession(PlayerData currentPlayer,
                       ArrayList<PlayerData> players, ArrayList<PlayerData> ais, ArrayList<BallData> balls,
                       PlatformData platform, WallData wall,
                       GameSessionData gameSessionData) {

        this.gameData = gameSessionData;
        this.currentPlayer = currentPlayer;
        this.allPlayers = new ArrayList<>();

        this.allPlayers.add(currentPlayer);
        ArrayList<PlayerData> aiPlayers = new ArrayList<>();

        for(PlayerData data : players)
            allPlayers.add(data);

        for(PlayerData data : ais)
            aiPlayers.add(data);

        this.allPlayers.addAll(aiPlayers);

        this.livingPlayers = new ArrayList<>();
        this.livingPlayers.addAll(allPlayers);

        this.balls = new ArrayList<>();
        for(BallData data : balls) {
            data.setVelocity(data.getSpeed(), data.getSpeed());
            this.balls.add(data);
        }

        this.platform = new Platform(platform);
        this.wall = new Wall(wall);

        this.ai = new AI(aiPlayers, this.allPlayers, this.balls, this.platform, this.gameData);

        Physics.setUp();
    }

    public boolean isRunning() {
        return livingPlayers.size() > 1;
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){

        ai.action();
        updateAllPlayers();
        updateAllBalls();

        // Handling the time elements of the game.
        gameData.incrementTicksElapsed();
        logger.debug("ticksElapsed: " + gameData.getTicksElapsed());
        if (gameData.getTicksElapsed() % 60 == 0 && gameData.getTimeLeft() > 0) {
            gameData.decrementTimeLeft();
            if(gameData.isTimeStopped() && gameData.getTimeStopCounter() > 0) {
                gameData.decrementTimeStopCounter();
                if(gameData.getTimeStopCounter() <= 0) gameData.setTimeStopped(false);
            }
        }

        // If enough time has elapsed, then reduce the size of the stage.
        if (gameData.getTimeLeft() < gameData.getTimeToNextStage() * (platform.getStage() - 1)) {
            platform.nextStage();
            wall.nextStage();
        }

        platform.shrink();
        wall.shrink();

        PowerUpManager.spawnPowerUp(gameData);

        // If someone has won, update the rankings one last time.
        if(!isRunning()) {
            gameData.getRankings().addFirst(livingPlayers.get(0).getObjectID());
            AudioControl.win();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(gameData.getRankings().toString());
        }


    }

    /**
     * Updates all ball positions and states, and calculates positions.
     */
    private void updateAllBalls() {
        for(BallData ball : this.balls) {

            // Move the ball
            if (gameData.isTimeStopped()) continue;
            Physics.move(ball);


            // Handle the danger state of the balls.
            if (ball.isDangerous()) {
                Ball.reduceTimer(ball, GameSettings.getBallTimerDecrement());
                if (ball.getTimer() <= 0) {
                    ball.setDangerous(false);
                    ball.setSpeed(GameSettings.getDefaultBallSpeed());
                }
            }

            // Check if a ball has collided with the wall.
            if (Physics.bouncedWall(ball, this.wall)) {
                AudioControl.ballCollidedWithWall();
            }

            // Check if a ball has collided with a player.
            for (PlayerData player : this.allPlayers) {
                if (Player.isDead(player)) continue;

                if (Physics.checkCollision(ball, player)) {

                    if (!ball.isDangerous()) {
                        ball.setDangerous(true);
                        ball.setTimer(GameSettings.getBallMaxTimer());
                        ball.setSpeed(ball.getSpeed() * 2);
                    } else {
                        if (player.isShielded()) {
                            player.setShielded(false);
                            //TODO: Play shield break sound effect
                        } else {
                            Player.damage(player, ball.getDamage());
                            AudioControl.playerCollidedWithBall();
                        }
                    }

                    Physics.collided(ball, player);

                    player.setStunTimer(5); //TODO: Magic number.
                    player.setVelocity(0, 0);
                    player.setState(ObjectState.IDLE);
                    player.setSpeed(GameSettings.getDefaultPlayerSpeed());

                    logger.info(player.getObjectID() + " collided with ball");
                }
            }

            // Check if a ball has collided with another ball.
            for (BallData ballB : this.balls) {
                if (!ball.equals(ballB) && Physics.checkCollision(ball, ballB)) {
                    Physics.collided(ball, ballB);
                }
            }
        }
    }

    /**
     * Updates all player positions and calculates player collisions.
     */
    private void updateAllPlayers() {

        for (PlayerData player : allPlayers) {

            Player.decrementStunTimer(player);
            if (player.getStunTimer() > 0) continue;

            if (!gameData.isTimeStopped()){
                if(player.isTimeStopper())
                    player.setTimeStopper(false);
                Physics.move(player);
            } else {
                if (player.isTimeStopper())
                    Physics.move(player);
            }

            if (!Player.isDead(player)) {
                // Check if a player has collided with a power up
                PowerUpManager.collect(player, gameData);

                if (player.getHealth() <= 0) {
                    Player.kill(player);
                    gameData.getRankings().addFirst(player.getObjectID());
                    livingPlayers.remove(player);
                    logger.info(player.getObjectID() + " was killed.");
                }

                // Check if a player has collided with another player.
                for (PlayerData playerB : this.allPlayers) {

                    if (Player.isDead(playerB)) continue;

                    if(!player.equals(playerB)
                            && Physics.checkCollision(player, playerB)) {
                        Physics.collided(player, playerB);
                    }
                }

                // Kill the player if they fall off the edge of the platform
                if(!platform.isOnPlatform(player)) {
                    Player.kill(player);
                    player.setDeadByFalling(true);
                    gameData.getRankings().addFirst(player.getObjectID());
                    livingPlayers.remove(player);
                    logger.info(player.getObjectID() + " fell off");
                }
            }
        }
    }

    public void updatePlayer(ArrayList<Input> keyPresses) {
        updatePlayer(this.currentPlayer, keyPresses, this.gameData);
    }

    /**
     * Updates the current player's velocity based on the given list of inputs.
     * @param player The playable character to move
     * @param keyPresses The set of key presses determining the movement
     * @param gameData game data to access time
     */
    public static void updatePlayer(PlayerData player, ArrayList<Input> keyPresses, GameSessionData gameData) {
        if (keyPresses.contains(Input.CHARGE)) {
            //TODO: Fix clunky dash.
            player.setVelocity(0,0);
            player.setSpeed(0);
            long timeSpentCharging = gameData.getTicksElapsed() - player.getTimeStartedCharging();

            if (player.getChargeLevel() < GameSettings.getDefaultPlayerMaxCharge()) {

                if (!Player.isCharging(player)) {
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.CHARGING);
                }

                if (timeSpentCharging % 20 == 0) Player.incrementChargeLevel(player);
            }
        }
        else {

            if (!Player.isDashing(player)) {
                if (keyPresses.contains(Input.UP)) player.setYVelocity(-1);
                else if (keyPresses.contains(Input.DOWN)) player.setYVelocity(1);
                else player.setYVelocity(0);

                if (keyPresses.contains(Input.LEFT)) player.setXVelocity(-1);
                else if (keyPresses.contains(Input.RIGHT)) player.setXVelocity(1);
                else player.setXVelocity(0);
            } else {
                long timeSpentDashing = gameData.getTicksElapsed() - player.getTimeStartedCharging();

                if(timeSpentDashing >= 10) {
                    player.setSpeed(GameSettings.getDefaultPlayerSpeed());
                    player.setState(ObjectState.IDLE);
                    player.setChargeLevel(1);
                    player.setVelocity(0,0);
                }
            }

            if (Player.isCharging(player)) {
                if (player.getChargeLevel() == 0) {
                    // If the charge button was pressed but not long enough, reset.
                    player.setState(ObjectState.IDLE);
                } else {
                    Physics.charge(player);
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.DASHING);

                }
            }
        }
    }

    public static void updatePlayer(PlayerData player, ArrayList<Input> keyPresses) {
        if (keyPresses.contains(Input.UP)) player.setYVelocity(-player.getSpeed());
        else if (keyPresses.contains(Input.DOWN)) player.setYVelocity(player.getSpeed());
        else player.setYVelocity(0);

        if (keyPresses.contains(Input.LEFT)) player.setXVelocity(-player.getSpeed());
        else if (keyPresses.contains(Input.RIGHT)) player.setXVelocity(player.getSpeed());
        else player.setXVelocity(0);
    }
}
