package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.ai.AIController;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.Input;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




/**
 * A class that models the logic of a game session.
 * @author Alfi S.
 */
public class GameSession {

    private static Logger logger = LogManager.getLogger(GameSession.class);

    private PlayerData currentPlayer;
    private ArrayList<PlayerData> allPlayers;
    private ArrayList<PlayerData> livingPlayers;

    private AIController aiController;
    private ArrayList<BallData> balls;

    private Platform platform;
    private Wall wall;

    private GameSessionData gameData;

    /**
     * Class constructor for a game session.
     * @param currentPlayer The current player's PlayerData object.
     * @param players An ArrayList of other human players.
     * @param ais An ArrayList of the ai controlled players.
     * @param balls An ArrayList of balls.
     * @param platform The platform the players are on.
     * @param wall The wall surrounding the platform.
     * @param gameSessionData The information about the game session.
     */
    public GameSession(PlayerData currentPlayer,
                       ArrayList<PlayerData> players, ArrayList<PlayerData> ais, ArrayList<BallData> balls,
                       PlatformData platform, WallData wall,
                       GameSessionData gameSessionData) {

        this.gameData = gameSessionData;
        this.currentPlayer = currentPlayer;
        this.allPlayers = new ArrayList<>();

        this.allPlayers.add(currentPlayer);
        ArrayList<PlayerData> aiPlayers = new ArrayList<>();

        for (PlayerData data : players) {
            allPlayers.add(data);
        }

        for (PlayerData data : ais) {
            aiPlayers.add(data);

        }

        this.allPlayers.addAll(aiPlayers);

        this.livingPlayers = new ArrayList<>();
        this.livingPlayers.addAll(allPlayers);

        this.balls = new ArrayList<>();
        for (BallData data : balls) {
            data.setVelocity(data.getSpeed(), data.getSpeed());
            this.balls.add(data);
        }

        this.platform = new Platform(platform);
        this.wall = new Wall(wall);

        this.aiController = new AIController(aiPlayers, this.allPlayers, this.balls, this.platform, this.gameData);

        Physics.setUp();
    }

    public boolean isRunning() {
        return livingPlayers.size() > 1;
    }

    /**
     * Updates all the objects in the game.
     */
    public void update() {

        aiController.makeDecision();
        updateAllPlayers();
        updateAllBalls();

        // Handling the time elements of the game.
        gameData.incrementTicksElapsed();
        logger.debug("ticksElapsed: " + gameData.getTicksElapsed());
        if (gameData.getTicksElapsed() % 60 == 0 && gameData.getTimeLeft() > 0) {
            gameData.decrementTimeLeft();
            if (gameData.isTimeStopped() && gameData.getTimeStopCounter() > 0) {
                gameData.decrementTimeStopCounter();
                if (gameData.getTimeStopCounter() <= 0) {
                    gameData.setTimeStopped(false);
                }
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
        if (!isRunning()) {
            gameData.getRankings().addFirst(livingPlayers.get(0).getObjectID());

            System.out.println(gameData.getRankings().toString());
        }


    }

    /**
     * Updates all ball positions and states, and calculates positions.
     */
    private void updateAllBalls() {
        for (BallData ball : this.balls) {

            // Move the ball
            if (gameData.isTimeStopped()) {
                continue;
            }
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
                if (Player.isDead(player)) {
                    continue;
                }

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
            if (player.getStunTimer() > 0) {
                continue;
            }

            if (!gameData.isTimeStopped()) {
                if (player.isTimeStopper()) {
                    player.setTimeStopper(false);
                }
                Physics.move(player);
            } else {
                if (player.isTimeStopper()) {
                    Physics.move(player);
                }
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

                    if (Player.isDead(playerB)) {
                        continue;
                    }

                    if (!player.equals(playerB)
                            && Physics.checkCollision(player, playerB)) {
                        Physics.collided(player, playerB);
                    }
                }

                // Kill the player if they fall off the edge of the platform
                if (!platform.isOnPlatform(player)) {
                    Player.kill(player);
                    player.setDeadByFalling(true);
                    gameData.getRankings().addFirst(player.getObjectID());
                    livingPlayers.remove(player);
                    logger.info(player.getObjectID() + " fell off");
                }
            }
        }
    }

    /**
     * Updates the current player's movement and actions based on the given list of inputs.
     * @param keyPresses The set of key presses determining the movement.
     */
    public void updatePlayer(ArrayList<Input> keyPresses) {
        updatePlayer(this.currentPlayer, keyPresses, this.gameData);
    }

    /**
     * Updates the movement and actions of a specified player based on given list of inputs.
     * @param player The playable character to act.
     * @param keyPresses The set of key presses determining the action.
     * @param gameData game data to access time.
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

                if (timeSpentCharging % 20 == 0) {
                    Player.incrementChargeLevel(player);
                }
            }
        } else {

            if (!Player.isDashing(player)) {
                updatePlayer(player, keyPresses);
            } else {
                long timeSpentDashing = gameData.getTicksElapsed() - player.getTimeStartedCharging();

                if (timeSpentDashing >= 10) {
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
                    // Otherwise dash forwards.
                    Physics.charge(player);
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.DASHING);

                }
            }
        }
    }

    /**
     * Updates the velocity of a player based on a given list of inputs.
     * @param player The player to be moved.
     * @param keyPresses The set of key presses determining the movement.
     */
    public static void updatePlayer(PlayerData player, ArrayList<Input> keyPresses) {
        if (keyPresses.contains(Input.UP)) {
            player.setYVelocity(-1);
        } else if (keyPresses.contains(Input.DOWN)) {
            player.setYVelocity(1);
        } else {
            player.setYVelocity(0);
        }

        if (keyPresses.contains(Input.LEFT)) {
            player.setXVelocity(-1);
        } else if (keyPresses.contains(Input.RIGHT)) {
            player.setXVelocity(1);
        } else {
            player.setXVelocity(0);
        }
    }
}
