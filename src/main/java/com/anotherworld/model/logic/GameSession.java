package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.ai.ControllerAI;
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
    private ArrayList<PlayerData> aiPlayers;
    private ArrayList<PlayerData> livingPlayers;

    private ControllerAI controllerAI;
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
        aiPlayers = new ArrayList<>();

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

        this.controllerAI = new ControllerAI(this.aiPlayers, this.allPlayers, this.balls, this.platform, this.gameData);

        Physics.setUp();
    }

    public boolean isRunning() {
        return livingPlayers.size() > 1;
    }

    /**
     * Updates all the objects in the game.
     */
    public void update() {

        controllerAI.makeDecision();
        updateAllPlayers();
        updateAllBalls();

        // Handling the time elements of the game.
        handleGameTime();

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
     * Handles the time elements of the game. Calculates seconds from ticks and handles time stops.
     */
    private void handleGameTime() {
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
    }

    /**
     * Searches the list of all players for one with the given ID, adds it to the
     * current list of AI players and passes it into the AI Controller.
     * @param playerId The ID of the player to replace.
     * @throws PlayerNotFoundError Error when the player to search is not found.
     */
    public void replacePlayableWithAI(String playerId) throws PlayerNotFoundError {
        PlayerData playerToReplace = null;
        for (PlayerData player : allPlayers) {
            if (player.getObjectID().equals(playerId)) {
                playerToReplace = player;
            }
        }

        if (playerToReplace == null) {
            throw new PlayerNotFoundError(playerId);
        }

        this.aiPlayers.add(playerToReplace);
        this.controllerAI = new ControllerAI(this.aiPlayers, this.allPlayers, this.balls, this.platform, this.gameData);
    }

    /**
     * Updates all ball positions and states, and calculates positions.
     */
    private void updateAllBalls() {
        for (BallData ball : this.balls) {
            if (!gameData.isTimeStopped()) {

                // Move the ball
                Physics.move(ball);

                // Handle the danger state of the balls.
                Ball.handleDangerState(ball);

                // Check if a ball has collided with the wall.
                if (Physics.bouncedWall(ball, this.wall)) {
                    AudioControl.ballCollidedWithWall();
                }

                // Check if a ball has collided with a player.
                for (PlayerData player : this.allPlayers) {
                    if (!Player.isDead(player)) {
                        if (Physics.checkCollision(ball, player)) {

                            ballToPlayerCollision(ball, player);

                            logger.info(player.getObjectID() + " collided with ball");
                        }
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
    }

    /**
     * Handles the logic behind a ball-player collision. Includes damaging and stunning players.
     * @param ball the ball in the collision.
     * @param player the player in the collision.
     */
    private void ballToPlayerCollision(BallData ball, PlayerData player) {
        if (!ball.isDangerous()) {
            ball.setDangerous(true);
            ball.setTimer(GameSettings.getBallMaxTimer());
            ball.setSpeed(ball.getSpeed() * 2);
            AudioControl.ballCollidedWithWall();
        } else {
            if (player.isShielded()) {
                player.setShielded(false);
                AudioControl.shieldBreak();
            } else {
                Player.damage(player, ball.getDamage());
                AudioControl.playerCollidedWithBall();
            }
        }

        Physics.collided(ball, player);

        player.setStunTimer(5);
        player.setVelocity(0, 0);
        player.setState(ObjectState.IDLE);
        player.setSpeed(GameSettings.getDefaultPlayerSpeed());
    }

    /**
     * Updates all player positions and calculates player collisions.
     */
    private void updateAllPlayers() {

        for (PlayerData player : allPlayers) {

            // Handle the player dash/charge if applicable.
            Player.chargeForward(gameData, player);

            // If the player is stunned, decrement their timer.
            Player.decrementStunTimer(player);

            if (player.getStunTimer() <= 0) {

                // Move the player based on the time stop.
                Player.movePlayer(gameData, player);

                if (!Player.isDead(player)) {
                    // Check if a player has collided with a power up.
                    PowerUpManager.collect(player, gameData);

                    // Kill the player if their health goes below zero.
                    if (player.getHealth() <= 0) {
                        Player.kill(player, false);
                        gameData.getRankings().addFirst(player.getObjectID());
                        livingPlayers.remove(player);
                        logger.info(player.getObjectID() + " was killed.");
                    }

                    // Check if a player has collided with another player.
                    for (PlayerData playerB : this.allPlayers) {
                        collidedWithPlayer(player, playerB);
                    }

                    // Kill the player if they fall off the edge of the platform
                    if (!platform.isOnPlatform(player)) {
                        Player.kill(player, true);
                        gameData.getRankings().addFirst(player.getObjectID());
                        livingPlayers.remove(player);
                        logger.info(player.getObjectID() + " fell off");
                    }
                }
            }
        }
    }

    /**
     * Handles the collision between two players.
     * @param player the first player in the collision.
     * @param playerB the second player in the collision.
     */
    private void collidedWithPlayer(PlayerData player, PlayerData playerB) {
        if (!Player.isDead(playerB)) {
            if (!player.equals(playerB)
                    && Physics.checkCollision(player, playerB)) {
                Physics.collided(player, playerB);
            }
        }
    }

    /**
     * Updates the velocity of a player based on a given list of inputs.
     * @param player The player to be moved.
     * @param keyPresses The set of key presses determining the movement.
     */
    private static void updatePlayerVelocity(PlayerData player, ArrayList<Input> keyPresses) {
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

    /**
     * Updates the movement and actions of a specified player based on given list of inputs.
     * @param player The playable character to act.
     * @param keyPresses The set of key presses determining the action.
     * @param gameData game data to access time.
     */
    public static void updatePlayer(PlayerData player, ArrayList<Input> keyPresses, GameSessionData gameData) {
        if (keyPresses.contains(Input.CHARGE)) {
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
                updatePlayerVelocity(player, keyPresses);
            }

            if (Player.isCharging(player)) {
                if (player.getChargeLevel() == 0) {
                    // If the charge button was pressed but not long enough, reset.
                    player.setState(ObjectState.IDLE);
                    player.setSpeed(GameSettings.getDefaultPlayerSpeed());
                } else {
                    // Otherwise dash forwards.
                    player.setTimeStartedCharging(gameData.getTicksElapsed());
                    player.setState(ObjectState.DASHING);
                }
            }
        }
    }

    /**
     * Updates the current player's movement and actions based on the given list of inputs.
     * @param keyPresses The set of key presses determining the movement.
     */
    public void updateCurrentPlayer(ArrayList<Input> keyPresses) {
        updatePlayer(this.currentPlayer, keyPresses, this.gameData);
    }

}
