package com.anotherworld.settings;

import com.anotherworld.audio.BackgroundMusic;
import com.anotherworld.audio.SoundEffects;
import com.anotherworld.control.GameSessionController;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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


    public GameSettings(int numberOfPlayers, int numberOfAIPlayers, int numberOfBalls, boolean musicSound, boolean effectsSound) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberofAIPlayers = numberOfAIPlayers;
        this.numberOfBall = numberOfBalls;
        this.musicSound = musicSound;
        this.effectsSound = effectsSound;

    }

    public GameSession createSession() {
        createGameFiles();
        return new GameSession(currentPlayer, players, ai, balls, platforms.get(0), walls.get(0));
    }

    private void createGameFiles () {
        createPlatform();
        createWall();
        createPlayers(numberOfPlayers,numberofAIPlayers);
        createBalls(numberOfBall);
    }

    public void changeDifficulty() {
        //TODO: Think of difficulty settings.
    }

    private void createPlayers(int numberOfPlayers, int numberofAIPlayers) {
        PlatformData platform = platforms.get(0);
        for (int i = 0; i < numberOfPlayers; i++) {
            float distanceFromBoarder = 5;
            float xRandom = getRandom(platform.getXCoordinate() - platform.getxSize() + distanceFromBoarder,
                                        platform.getXCoordinate() + platform.getxSize() - distanceFromBoarder);

            float yRandom = getRandom(platform.getYCoordinate() - platform.getySize() + distanceFromBoarder,
                                        platform.getYCoordinate() + platform.getySize() - distanceFromBoarder);

            PlayerData newPlayer = new PlayerData(names.get(i),10,xRandom,yRandom, ObjectState.IDLE, 0.2f,2);
            if (numberofAIPlayers > 0) {
                ai.add(newPlayer);
                numberofAIPlayers--;
            } else {
                if (i == numberOfPlayers-1) currentPlayer = newPlayer;
                else players.add(newPlayer);
            }
        }

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

    private void createBalls(int numberOfBalls) {
        //need number of balls somewhere

        PlatformData platform = platforms.get(0);
        WallData wall = walls.get(0);

        for (int i = 0; i < numberOfBalls; i++) {

            int side = getRandom(0,3);
            float xMin = 0;
            float xMax = 0;

            float yMin = 0;
            float yMax = 0;

            float ballR = 3;
            switch (side) {
                case 0: // Left side
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballR;
                    xMax = platform.getXCoordinate() - platform.getxSize() - ballR;

                    yMin = wall.getYCoordinate() - wall.getySize() + ballR;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballR;
                    break;
                case 1: // Right side
                    xMin= platform.getXCoordinate() + platform.getxSize() + ballR;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballR;

                    yMin = wall.getYCoordinate() - wall.getySize() + ballR;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballR;
                    break;
                case 2: // Upper side
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballR;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballR;

                    yMax = platform.getYCoordinate() - platform.getySize() - ballR;
                    yMin = wall.getYCoordinate() - wall.getySize() + ballR;
                    break;
                case 4: // Lower side
                    xMin = wall.getXCoordinate() - wall.getxSize() + ballR;
                    xMax = wall.getXCoordinate() + wall.getxSize() - ballR;

                    yMin = platform.getYCoordinate() + platform.getySize() + ballR;
                    yMax = wall.getYCoordinate() + wall.getySize() - ballR;
                    break;
                default:
                    logger.error("Wrong random ball set up");

            }

            // set random location with random direction
            // probably towards the middle
            BallData newBall = new BallData(false,getRandom(xMin,xMax),getRandom(yMin,yMax),ObjectState.IDLE,0.5f,3);
            balls.add(newBall);
        }

    }

    private void createWall() {
        //again where is the center
        walls.add(new WallData(50, 50));
    }

    private void createPlatform() {
       // new PlatformData();
        //Where is a center
        platforms.add(new PlatformData(50,50));

    }

    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }
}
