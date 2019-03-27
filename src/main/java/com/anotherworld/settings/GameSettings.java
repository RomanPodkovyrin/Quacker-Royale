package com.anotherworld.settings;

import static com.anotherworld.tools.maths.Maths.getRandom;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.tools.maths.MatrixMath;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.logic.PowerUpManager;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.PropertyReader;

import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class allows view to call the functions to change individual settings and prepare the game.
 * Also translates persistent settings stored in properties file into the game.
 *
 * @author Roman P.
 * @author Alfi S.
 */
public class GameSettings {

    private static Logger logger = LogManager.getLogger(GameSettings.class);

    // Settings regarding the number of players
    private int numberOfPlayers;
    private int numberOfaiPlayers;
    private int numberOfBall;

    // Settings regarding the player defaults
    private static float defaultPlayerSpeed;
    private float defaultPlayerRadius;
    private static int defaultPlayerHealth;
    private static int defaultPlayerMaxCharge;

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

    // Created Game objects
    private PlayerData currentPlayer;
    private ArrayList<PlayerData> players = new ArrayList<>();
    private ArrayList<PlayerData> ai = new ArrayList<>();
    private ArrayList<BallData> balls = new ArrayList<>();
    private ArrayList<PlatformData> platforms = new ArrayList<>();
    private ArrayList<WallData> walls = new ArrayList<>();
    private GameSessionData gameSessionData;

    private ArrayList<String> names = new ArrayList<>(Arrays.asList("santa","robber", "police", "pirate", "default"));

    // networking objects

    /**
     * This method allows to create the Game settings object with pregenerated game objects.
     *
     * @param currentPlayer  - The player of the machine
     * @param players - All the other players - current players and ai
     * @param ai  - all the ai players
     * @param balls - all the balls
     * @param platforms -platform
     * @param walls - wall
     * @param gameSessionData - game session
     */
    public GameSettings(PlayerData currentPlayer, ArrayList<PlayerData> players,ArrayList<PlayerData> ai,
                        ArrayList<BallData> balls,ArrayList<PlatformData> platforms,ArrayList<WallData> walls, GameSessionData gameSessionData) {
        this.currentPlayer = currentPlayer;
        logger.info("GameSettings current player: " + currentPlayer);
        this.players = players;
        logger.info("GameSettings players: " + players);
        this.ai = ai;
        logger.info("GameSettings ai: " + ai);
        this.balls = balls;
        logger.info("GameSettings balls: " + balls);
        this.platforms = platforms;
        logger.info("GameSettings platform: " + platforms);
        this.walls = walls;
        logger.info("GameSettings wall: " + walls);
        this.gameSessionData = gameSessionData;
        logger.info("GameSettings session: " + gameSessionData);

        // load all the default values
        loadAllGameValues();

    }

    /**
     * This class loads all the defaults from the property files.
     */
    private void loadAllGameValues() {

        try {
            PropertyReader propertyFileLogic = new PropertyReader("logic.properties");

            this.defaultPlayerSpeed = Float.parseFloat(propertyFileLogic.getValue("PLAYER_SPEED"));
            this.defaultPlayerHealth = Integer.parseInt(propertyFileLogic.getValue("PLAYER_HEALTH"));
            this.defaultPlayerRadius = Float.parseFloat(propertyFileLogic.getValue("PLAYER_RADIUS"));
            this.defaultPlayerMaxCharge = Integer.parseInt(propertyFileLogic.getValue("PLAYER_MAX_CHARGE"));

            this.defaultBallMaxTimer = Integer.parseInt(propertyFileLogic.getValue("BALL_MAX_TIMER"));
            this.defaultBallTimerDecrement = Integer.parseInt(propertyFileLogic.getValue("BALL_TIMER_DEC"));
            this.defaultBallSpeed  = Float.parseFloat(propertyFileLogic.getValue("BALL_SPEED"));
            this.defaultBallDamage = Integer.parseInt(propertyFileLogic.getValue("BALL_DAMAGE"));
            this.defaultBallRadius =  Float.parseFloat(propertyFileLogic.getValue("BALL_RADIUS"));

            this.defaultWallXSize = Float.parseFloat(propertyFileLogic.getValue("WALL_X_SIZE"));
            this.defaultWallYSize = Float.parseFloat(propertyFileLogic.getValue("WALL_Y_SIZE"));

            this.defaultPlatformXSize = Float.parseFloat(propertyFileLogic.getValue("PLATFORM_X_SIZE"));
            this.defaultPlatformYSize = Float.parseFloat(propertyFileLogic.getValue("PLATFORM_Y_SIZE"));
            propertyFileLogic.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method generates all the game objects with the specified values.
     *
     * @param numberOfPlayers - the total number of players
     * @param numberOfaiPlayers - number of ControllerAI
     * @param numberOfBalls - number of balls
     */
    public GameSettings(int numberOfPlayers, int numberOfaiPlayers, int numberOfBalls) {

        this.numberOfPlayers = numberOfPlayers;
        this.numberOfaiPlayers = numberOfaiPlayers;
        this.numberOfBall = numberOfBalls;

        loadAllGameValues();
        createGameFiles();
    }


    /**
     * Creates all the balls placed at the random location between the platform and the wall.
     *
     * @param numberOfBalls - number of balls to be generated
     */
    private void createBalls(int numberOfBalls) {

        PlatformData platform = platforms.get(0);
        WallData wall = walls.get(0);

        // Setting default location just in case
        float xMin = 0;
        float xMax = 0;
        float yMin = 0;
        float yMax = 0;

        float ballRadius =  defaultBallRadius;
        float ballSpeed = defaultBallSpeed;

        for (int i = 0; i < numberOfBalls; i++) {

            // Randomly choose the side
            int side = (int)Math.floor(Math.random() * 4);
            switch (side) {
                case 0: // Left side
                    logger.trace("left");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = platform.getXCoordinate() - platform.getxSize();

                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                case 1: // Right side
                    logger.trace("right");
                    xMin = platform.getXCoordinate() + platform.getxSize();
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                case 2: // Upper side
                    logger.trace("up");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMax = platform.getYCoordinate() - platform.getySize();
                    yMin = wall.getYCoordinate() - wall.getySize() + ballRadius;
                    break;
                case 3: // Lower side
                    logger.trace("down");
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballRadius;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballRadius;

                    yMin = platform.getYCoordinate() + platform.getySize();
                    yMax = wall.getYCoordinate() + wall.getySize() - ballRadius;
                    break;
                default:
                    logger.error("Wrong random ball set up");

            }

            // setting
            float newBallXCoordinate = getRandom(xMin,xMax);
            float newBallYCoordinate = getRandom(yMin,yMax);


            // the number of tries allowed to try to randomise it
            int counter = 0;
            boolean emptySpace = false;

            // Looks if can find a random place without colliding with other balls
            while (!emptySpace) {

                emptySpace = true;
                // generate the random location based on the given min and max values
                newBallXCoordinate = getRandom(xMin,xMax);
                newBallYCoordinate = getRandom(yMin,yMax);

                // Check if collides with any other balls
                for (BallData createdBall : balls) {
                    if (MatrixMath.distanceAB(new Matrix(newBallXCoordinate, newBallYCoordinate), createdBall.getCoordinates()) <= ballRadius * 2) {
                        emptySpace = false;
                    }
                }

                if (counter < 10) {
                    counter = counter + 1;
                } else {
                    logger.trace("Too long to find a random location for the ball ");
                    break;

                }
            }

            // set random location
            BallData newBall = new BallData("ball " + i,false,newBallXCoordinate,newBallYCoordinate,ObjectState.IDLE,ballSpeed,ballRadius);
            balls.add(newBall);
        }

    }

    /**
     * Created the wall with the current defaults.
     */
    private void createWall() {
        // TODO magic number
        WallData wall = new WallData(80,45);

        wall.setxSize(defaultWallXSize);

        wall.setySize(defaultWallYSize);
        walls.add(wall);
    }

    /**
     * Generates the platform with a given defaults.
     */
    private void createPlatform() {
        // TODO magic number
        PlatformData platform = new PlatformData(80,45);

        platform.setxSize(defaultPlatformXSize);
        platform.setWidth(platform.getxSize() * 2);

        platform.setySize(defaultPlatformYSize);
        platform.setHeight(platform.getySize() * 2);

        platforms.add(platform);

    }

    /**
     * Generates the player and randomly generates their location.
     * @param numberOfPlayers - total number of players
     * @param numberofaiplayers - number of ai
     */
    private void createPlayers(int numberOfPlayers, int numberofaiplayers) {
        PlatformData platform = platforms.get(0);
        int playerHealth = defaultPlayerHealth;
        float playerRadius = defaultPlayerRadius;
        float playerSpeed = defaultPlayerSpeed;
        float distanceFromBoarder = 5;
        // TODO magic number

        for (int i = 0; i < numberOfPlayers; i++) {

            // generates the random location of the player
            float xRandom = getRandom(platform.getXCoordinate() - platform.getxSize() + distanceFromBoarder,
                    platform.getXCoordinate() + platform.getxSize() - distanceFromBoarder);

            float yRandom = getRandom(platform.getYCoordinate() - platform.getySize() + distanceFromBoarder,
                    platform.getYCoordinate() + platform.getySize() - distanceFromBoarder);

            // Creates the player object
            PlayerData newPlayer = new PlayerData(names.get(i),playerHealth,xRandom,yRandom, ObjectState.IDLE, playerSpeed,playerRadius);

            // checks if need to make an ControllerAI, current players or other players
            if (numberofaiplayers > 0) {
                ai.add(newPlayer);
                numberofaiplayers--;
            } else {
                if (i == numberOfPlayers - 1) {
                    currentPlayer = newPlayer;
                } else {
                    players.add(newPlayer);
                }
            }
        }

    }


    /**
     * Creates the game session and gives it game time.
     */
    private void createGameSessionData() {
        // TODO magic number
        this.gameSessionData = new GameSessionData(60);
        gameSessionData.setPowerUpSchedule(
            PowerUpManager.generatePowerUpSchedule(gameSessionData.getTimeLeft(), platforms.get(0))
        );
    }

    /**
     * Creates and returns game session with all the created game objects.
     * @return created game session
     */
    public GameSession createSession() {
        // TODO give the gameSessionData into gameSessionData
        return new GameSession(currentPlayer, players, ai, balls, platforms.get(0), walls.get(0), gameSessionData);
    }

    /**
     * Gives the file creation the right sequence.
     */
    private void createGameFiles() {
        // Platform and walls have to be generated before the ball and
        createPlatform();
        createWall();
        createPlayers(numberOfPlayers, numberOfaiPlayers);
        createBalls(numberOfBall);
        createGameSessionData();
    }

   public enum Difficulty {
        HARD, MEDIUM, EASY
    }

    /**
     * Sets the game difficulty.
     * 1 - easy 2 balls ai at 9
     * 2 - medium  4 balls ai at 7
     * 3 - hard  6 balls ai at 6
     */
    public static void changeDifficulty(Difficulty level) {

        float speed = 0;
        int damage = 0 ;
        int number = 0;
        int ai = 0;

        switch (level) {
            case MEDIUM:
                speed = 0.6f;
                damage = 10;
                number = 4;
                ai = 2;
                break;
            case HARD:
                speed = 0.8f;
                damage = 20;
                number = 6;
                ai = 3;
                break;
            case EASY:
                speed = 0.5f;
                damage = 5;
                number = 4;
                ai = 1;
                break;
        }

        try {
            PropertyReader propertyFileLogic = new PropertyReader("logic.properties");

            propertyFileLogic.setValue("BALL_SPEED",Float.toString(speed));
            propertyFileLogic.setValue("BALL_DAMAGE",Integer.toString(damage));
            propertyFileLogic.setValue("SINGLE_PLAYER_BALLS",Integer.toString(number));
            propertyFileLogic.setValue("MULTI_PLAYER_BALLS",Integer.toString(number));
            propertyFileLogic.setValue("SINGLE_PLAYER_AI",Integer.toString(ai));
            propertyFileLogic.setValue("SINGLE_PLAYER_PLAYERS",Integer.toString(ai +1));
        } catch (IOException e) {
           logger.error("Could not load the file to change difficulty");
        }

    }


    /**
     * returns (all the players - ai - current player).
     * @return returns (all the players - ai - current player)
     */
    public ArrayList<PlayerData> getPlayers() {
        return players;
    }

    /**
     * returns all the ai players.
     * @return returns all the ai players
     */
    public ArrayList<PlayerData> getAi() {
        return ai;
    }

    /**
     * returns all the balls.
     * @return returns all the balls
     */
    public ArrayList<BallData> getBalls() {
        return balls;
    }

    /**
     * returns the platform.
     * @return returns the platform
     */
    public ArrayList<PlatformData> getPlatform() {
        return platforms;
    }

    /**
     * returns the game session.
     * @return returns the game session
     */
    public GameSessionData getGameSessionData() {
        return gameSessionData;
    }

    /**
     * returns the wall object.
     * @return returns the wall object
     */
    public ArrayList<WallData> getWall() {
        return walls;
    }

    /**
     * returns the current player object.
     * @return returns the current player object
     */
    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * returns the default player health from properties file.
     * @return returns the default player health from properties file
     */
    public static int getDefaultPlayerHealth() {
        return defaultPlayerHealth;
    }

    /**
     * returns the default player speed from properties file.
     * @return returns the default player speed from properties file
     */
    public static float getDefaultPlayerSpeed() {
        return defaultPlayerSpeed;
    }

    /**
     * returns the default player max charge from properties file.
     * @return returns the default player max charge from properties file
     */
    public static int getDefaultPlayerMaxCharge() {
        return defaultPlayerMaxCharge;
    }

    /**
     * returns the default ball max timer from properties file.
     * @return returns the default ball max timer from properties file
     */
    public static int getBallMaxTimer() {
        return defaultBallMaxTimer;
    }

    /**
     * returns the default ball time decrement from properties file.
     * @return returns the default ball time decrement from properties file
     */
    public static int getBallTimerDecrement() {
        return defaultBallTimerDecrement;
    }

    /**
     * returns the default ball speed from properties file.
     * @return returns the default ball speed from properties file
     */
    public static float getDefaultBallSpeed() {
        return defaultBallSpeed;
    }

    /**
     * returns the default ball damage from properties file.
     * @return returns the default ball damage from properties file
     */
    public static int getDefaultBallDamage() {
        return defaultBallDamage;
    }
}
