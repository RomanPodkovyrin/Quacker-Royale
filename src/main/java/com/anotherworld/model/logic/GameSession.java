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

    private int playersAlive;

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

        this.playersAlive = this.allPlayers.size();

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
     * Updates all ball positions and states, and calculates positions.
     */
    private void updateAllBalls() {
        for(Ball ball : this.balls) {

            // Move the ball
            Physics.move(ball);

            // Handle the danger state of the balls.
            if (ball.isDangerous()) {
                ball.reduceTimer(GameSettings.getBallTimerDecrement());
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
            for (Player player : this.allPlayers) {
                if (player.isDead()) continue;

                if(Physics.checkCollision(ball, player)) {

                    if (!ball.isDangerous()){
                        ball.setDangerous(true);
                        ball.setTimer(GameSettings.getBallMaxTimer());
                        ball.setSpeed(ball.getSpeed() * 2);
                    } else {
                        player.damage(ball.getDamage());
                        AudioControl.playerCollidedWithBall();
                    }

                    Physics.collided(ball, player);
                    logger.trace(player.getCharacterID() + " collide with ball");
                }
            }

            // Check if a ball has collided with another ball.
            for (Ball ballB : this.balls) {
                if (!ball.equals(ballB) && Physics.checkCollision(ball, ballB)){
                    Physics.collided(ball, ballB);
                }
            }
        }


    }

    /**
     * Updates all player positions and calculates player collisions.
     */
    private void updateAllPlayers() {

        for (Player player : allPlayers) {

            Physics.move(player);

            if (!player.isDead()) {

                if (player.getHealth() <= 0) {
                    player.kill();
                    gameSessionData.getRankings().addFirst(player.getCharacterID());
                    playersAlive--;
                }

                // Check if a player has collided with another player.
                for (Player playerB : this.allPlayers) {

                    if (playerB.isDead()) continue;

                    if(!player.equals(playerB)
                            && Physics.checkCollision(player, playerB)) {
                        Physics.collided(player, playerB);

                    }
                }

                // Kill the player if they fall off the edge of the platform
                if(!platform.isOnPlatform(player)) {
                    player.kill();
                    playersAlive--;
                    logger.debug(player.getCharacterID() + " fell off");
                }
            }
        }
    }

    /**
     * Checks all the objects and updates their state and location
     * physics and ai are run during this time
     */
    public void update(){

        if(playersAlive > 1) {

            ai.action();
            updateAllPlayers();
            updateAllBalls();

            // Handling the time-based elements of the game
            gameSessionData.incrementTicksElapsed();
            logger.debug("ticksElapsed: " + gameSessionData.getTicksElapsed());

            if (gameSessionData.getTicksElapsed() % 60 == 0 && gameSessionData.getTimeLeft() > 0) {
                gameSessionData.decrementTimeLeft();
            }

            if (gameSessionData.getTimeLeft() < gameSessionData.getTimeToNextStage() * (platform.getStage() - 1)) {
                platform.nextStage();
                wall.nextStage();
            }

            platform.shrink();
            wall.shrink();
        } else {
            System.out.println(gameSessionData.getRankings().toString());
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
            //TODO: Fix clunky dash.
            player.setSpeed(0);
            long timeSpentCharging = gameData.getTicksElapsed() - player.getTimeStartedCharging();

            if (player.getChargeLevel() < GameSettings.getDefaultPlayerMaxCharge()) {

                if (!player.isCharging()) {
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.CHARGING);
                }

                if (timeSpentCharging % 60 == 0) player.incrementChargeLevel();
            }
        }
        else {

            if (!player.isDashing()) {
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

            if (player.isCharging()) {
                Physics.charge(player);
                player.setTimeStartedCharging(gameData.getTicksElapsed());
                player.setState(ObjectState.DASHING);
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
