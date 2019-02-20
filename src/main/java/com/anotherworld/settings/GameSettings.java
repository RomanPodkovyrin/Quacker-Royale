package com.anotherworld.settings;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.ObjectState;
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

    // Settings regarding the number of players
    private int numberOfPlayers;
    private int numberofAIPlayers;
    private int numberOfBall;

    // Settings regarding the player defaults
    private static float defaultPlayerSpeed;
    private float defaultPlayerRadius;
    private static int defaultPlayerHealth;

    // Settings regarding the ball defaults
    private static int defaultBallMaxTimer;
    private static int defaultBallTimerDecrement;
    private static float defaultBallSpeed;
    private float defaultBallRadius;
    private static int defaultBallDamage;

    // Settings regarding the wall defaults
    private float defaultWallXSize;
    private float defaultWallYSize;

    //Settings regarding the platform defaults
    private float defaultPlatformXSize;
    private float defaultPlatformYSize;

    private PlayerData currentPlayer;
    private ArrayList<PlayerData> players = new ArrayList<>();
    private ArrayList<PlayerData> ai = new ArrayList<>();
    private ArrayList<BallData> balls = new ArrayList<>();
    private ArrayList<PlatformData> platforms = new ArrayList<>();
    private ArrayList<WallData> walls = new ArrayList<>();

    private ArrayList<String> names = new ArrayList<>(Arrays.asList("Boi","Terminator", "Eiker", "DanTheMan", "Loser" ));

    private static PropertyReader gamesession;


    public GameSettings(int numberOfPlayers, int numberOfAIPlayers, int numberOfBalls) {

        this.numberOfPlayers = numberOfPlayers;
        this.numberofAIPlayers = numberOfAIPlayers;
        this.numberOfBall = numberOfBalls;

        try {
            PropertyReader propertyFileLogic = new PropertyReader("logic.properties");

            this.defaultPlayerSpeed  = Float.parseFloat(propertyFileLogic.getValue("PLAYER_SPEED"));
            this.defaultPlayerHealth = Integer.parseInt(propertyFileLogic.getValue("PLAYER_HEALTH"));
            this.defaultPlayerRadius = Float.parseFloat(propertyFileLogic.getValue("PLAYER_RADIUS"));

            this.defaultBallMaxTimer = Integer.parseInt(propertyFileLogic.getValue("BALL_MAX_TIMER"));
            this.defaultBallTimerDecrement = Integer.parseInt(propertyFileLogic.getValue("BALL_TIMER_DEC"));
            this.defaultBallSpeed  = Float.parseFloat(propertyFileLogic.getValue("BALL_SPEED"));
            this.defaultBallDamage = Integer.parseInt(propertyFileLogic.getValue("BALL_DAMAGE"));
            this.defaultBallRadius =  Float.parseFloat(propertyFileLogic.getValue("BALL_RADIUS"));

            this.defaultWallXSize = Float.parseFloat(propertyFileLogic.getValue("WALL_X_SIZE"));
            this.defaultWallYSize = Float.parseFloat(propertyFileLogic.getValue("WALL_Y_SIZE"));

            this.defaultPlatformXSize = Float.parseFloat(propertyFileLogic.getValue("PLATFORM_X_SIZE"));
            this.defaultPlatformYSize = Float.parseFloat(propertyFileLogic.getValue("PLATFORM_Y_SIZE"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean toggleOnOff(String setting){
        boolean settingState = false;
        try{
        gamesession = new PropertyReader("gamesession.properties");
        String state = gamesession.getValue(setting);
        switch (state){
            case "on":
                settingState = false;
                gamesession.setValue(setting,"off");
                break;
            case "off":
                settingState = true;
                gamesession.setValue(setting,"on");
                break;
            default:
                settingState = false;
                gamesession.setValue(setting,"off");
                break;
        }
        } catch (IOException e) {
        e.printStackTrace();
        }


        return settingState;
    }

    private void createBalls(int numberOfBalls) throws IOException{
        //need number of balls somewhere

        PlatformData platform = platforms.get(0);
        WallData wall = walls.get(0);

        for (int i = 0; i < numberOfBalls; i++) {

            int side = (int)(Math.random() * 4) + 1;
            float xMin = platform.getxSize() + platform.getXCoordinate();
            float xMax = platform.getxSize() + platform.getXCoordinate();

            float yMin = platform.getySize() + platform.getYCoordinate();
            float yMax = platform.getySize() + platform.getYCoordinate();

            float ballRadius =  defaultBallRadius;
            float ballSpeed = defaultBallSpeed;
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

        wall.setxSize(defaultWallXSize);
        wall.setWidth(wall.getxSize() * 2);

        wall.setySize(defaultWallYSize);
        wall.setHeight(wall.getySize() * 2);
        walls.add(wall);
    }

    private void createPlatform() throws IOException {
        // new PlatformData();
        // Where is a center
        PlatformData platform = new PlatformData(80,45);

        platform.setxSize(defaultPlatformXSize);
        platform.setWidth(platform.getxSize() * 2);

        platform.setySize(defaultPlatformYSize);
        platform.setHeight(platform.getySize() * 2);

        platforms.add(platform);

    }

    private void createPlayers(int numberOfPlayers, int numberofAIPlayers) throws IOException {
        PlatformData platform = platforms.get(0);
        int playerHealth = defaultPlayerHealth;
        float playerRadius = defaultPlayerRadius;
        float playerSpeed = defaultPlayerSpeed;
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


    public static float getDefaultPlayerSpeed() { return defaultPlayerSpeed; }

    public static int getDefaultPlayerHealth() { return defaultPlayerHealth; }

    public static int getBallMaxTimer() { return defaultBallMaxTimer; }

    public static int getBallTimerDecrement() { return defaultBallTimerDecrement; }

    public static float getDefaultBallSpeed() { return defaultBallSpeed; }

    public static int getDefaultBallDamage() { return defaultBallDamage; }
}
