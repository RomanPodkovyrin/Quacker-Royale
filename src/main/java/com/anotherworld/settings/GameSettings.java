package com.anotherworld.settings;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.anotherworld.tools.maths.Maths.getRandom;

/**
 * This class allows view to call the functions to change individual settings and prepare the game.
 *
 * @author Roman
 */
public class GameSettings {

    private static Logger logger = LogManager.getLogger(GameSettings.class);
    private int numberOfPlayers;
    private int numberofAIPlayers;
    private boolean musicSound;
    private boolean effectsSound;
    private int numberOfBall;

    private PlayerData currentPlayer;
    private ArrayList<PlayerData> players = new ArrayList<>();
    private ArrayList<PlayerData> ai = new ArrayList<>();
    private ArrayList<BallData> balls = new ArrayList<>();
    private ArrayList<PlatformData> platforms = new ArrayList<>();
    private ArrayList<WallData> walls = new ArrayList<>();

    private ArrayList<String> names = new ArrayList<>(Arrays.asList("Boi","Terminator", "Eiker", "DanTheMan", "Loser" ));

    private PropertyReader propertyFile ;


    public GameSettings(int numberOfPlayers, int numberOfAIPlayers, int numberOfBalls, boolean musicSound, boolean effectsSound) {

        this.numberOfPlayers = numberOfPlayers;
        this.numberofAIPlayers = numberOfAIPlayers;
        this.numberOfBall = numberOfBalls;
        this.musicSound = musicSound;
        this.effectsSound = effectsSound;

        try {
            propertyFile = new PropertyReader("logic.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createBalls(int numberOfBalls) throws IOException{
        //need number of balls somewhere

        PlatformData platform = platforms.get(0);
        WallData wall = walls.get(0);

        for (int i = 0; i < numberOfBalls; i++) {

            int side = (int)(Math.random() * 4) + 1;
            float xMin = 0;
            float xMax = 0;

            float yMin = 0;
            float yMax = 0;

            float ballRadius =  Float.parseFloat(propertyFile.getValue("BALL_RADIUS"));
            float ballSpeed = Float.parseFloat(propertyFile.getValue("BALL_SPEED"));
            switch (side) {
                case 0: // Left side
                    logger.trace("left");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = platform.getXCoordinate() - platform.getxSize() ;//- ballRadius;

                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                case 1: // Right side
                    logger.trace("right");
                    xMin= platform.getXCoordinate() + platform.getxSize() ;//+ ballRadius;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                case 2: // Upper side
                    logger.trace("up");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMax = platform.getYCoordinate() - platform.getySize() ;//- ballRadius;
                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    break;
                case 4: // Lower side
                    logger.trace("down");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMin = platform.getYCoordinate() + platform.getySize() ;//+ ballRadius;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                default:
                    logger.error("Wrong random ball set up");

            }

            float newBallXCoordinate = getRandom(xMin,xMax);
            float newBallYCoordinate = getRandom(yMin,yMax);
            boolean emptySpace = false;

            int counter = 0;
            while (!emptySpace) {

                emptySpace = true;
                newBallXCoordinate = getRandom(xMin,xMax);
                newBallYCoordinate = getRandom(yMin,yMax);
                for (BallData createdBall : balls) {
                    if (MatrixMath.distanceAB(new Matrix(newBallXCoordinate, newBallYCoordinate), createdBall.getCoordinates()) <= ballRadius * 2) {
                        emptySpace = false;
                    }
                }
                if (counter < 10) {
                    counter = counter + 1;
                } else {
                    counter =0;
                    logger.trace("Too long to find a random location for the ball ");
                    break;

                }
            }

            // set random location with random direction
            // probably towards the middle
            BallData newBall = new BallData(false,newBallXCoordinate,newBallYCoordinate,ObjectState.IDLE,ballSpeed,ballRadius);
            balls.add(newBall);
        }

    }

    private void createWall() throws IOException{
        //again where is the center
        WallData wall = new WallData(80,45);

        wall.setxSize(Float.parseFloat(propertyFile.getValue("WALL_X_SIZE")));
        wall.setWidth(wall.getxSize() * 2);

        wall.setySize(Float.parseFloat(propertyFile.getValue("WALL_Y_SIZE")));
        wall.setHeight(wall.getySize() * 2);
        walls.add(wall);
    }

    private void createPlatform() throws IOException {
        // new PlatformData();
        // Where is a center
        PlatformData platform = new PlatformData(80,45);

        platform.setxSize(Float.parseFloat(propertyFile.getValue("PLATFORM_X_SIZE")));
        platform.setWidth(platform.getxSize() * 2);

        platform.setySize(Float.parseFloat(propertyFile.getValue("PLATFORM_Y_SIZE")));
        platform.setHeight(platform.getySize() * 2);

        platforms.add(platform);

    }

    private void createPlayers(int numberOfPlayers, int numberofAIPlayers) throws IOException {
        PlatformData platform = platforms.get(0);
        int playerHealth = Integer.parseInt(propertyFile.getValue("PLAYER_HEALTH"));
        float playerRadius = Float.parseFloat(propertyFile.getValue("PLAYER_RADIUS"));
        float playerSpeed = Float.parseFloat(propertyFile.getValue("PLAYER_SPEED"));
        for (int i = 0; i < numberOfPlayers; i++) {
            float distanceFromBoarder = 5;
            float xRandom = getRandom(platform.getXCoordinate() - platform.getxSize() + distanceFromBoarder,
                    platform.getXCoordinate() + platform.getxSize() - distanceFromBoarder);

            float yRandom = getRandom(platform.getYCoordinate() - platform.getySize() + distanceFromBoarder,
                    platform.getYCoordinate() + platform.getySize() - distanceFromBoarder);

            PlayerData newPlayer = new PlayerData(names.get(i),playerHealth,xRandom,yRandom, ObjectState.IDLE, playerSpeed,playerRadius);
            if (numberofAIPlayers > 0) {
                ai.add(newPlayer);
                numberofAIPlayers--;
            } else {
                if (i == numberOfPlayers-1) currentPlayer = newPlayer;
                else players.add(newPlayer);
            }
        }

    }

    public GameSession createSession() {
        createGameFiles();
        return new GameSession(currentPlayer, players, ai, balls, platforms.get(0), walls.get(0));
    }

    private void createGameFiles () {
        try {
            createPlatform();
            createWall();
            createPlayers(numberOfPlayers, numberofAIPlayers);
            createBalls(numberOfBall);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeDifficulty() {
        //TODO: Think of difficulty settings.
    }

    public ArrayList<PlayerData> getPlayers() {
        return players;
    }

    public ArrayList<PlayerData> getAi() {
        return ai;
    }

    public ArrayList<BallData> getBalls() {
        return balls;
    }

    public ArrayList<PlatformData> getPlatform() {
        return platforms;
    }

    public ArrayList<WallData> getWall() {
        return walls;
    }

    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }
}
