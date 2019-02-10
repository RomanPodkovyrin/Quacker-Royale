package com.anotherworld.settings;

import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class allows view to call the functions to change individual settings and prepare the game.
 *
 * @author Roman
 */
public class GameSettings {
    private int numberOfPlayers;
    private int numberofAIPlayers;
    private boolean musicSound;
    private boolean effectsSound;

    private ArrayList<PlayerData> players;
    private ArrayList<PlayerData> ai;
    private ArrayList<BallData> balls;
    private PlatformData platform;
    private WallData wall;

    private ArrayList<String> names = new ArrayList<>(Arrays.asList("Boi","Terminator", "Eiker", "DanTheMan", "Loser" ));


    public GameSettings(int numberOfPlayers, int numberOfAIPlayers, boolean musicSound, boolean effectsSound) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberofAIPlayers = numberOfAIPlayers;
        this.musicSound = musicSound;
        this.effectsSound = effectsSound;

    }

    public void changeDifficulty() {
        // decreases players health
        // increases number of balls
        // increases balls speed

    }

    private void createPlayers(int numberOfPlayers, int numberofAIPlayers) {
        for (int i = 0; i < numberOfPlayers;i ++ ) {
            float distanceFromBoarder = 10;
            float xRandom = getRandom(platform.getXCoordinate() - platform.getxSize() + distanceFromBoarder,
                                        platform.getXCoordinate() + platform.getxSize() - distanceFromBoarder);

            float yRandom = getRandom(platform.getYCoordinate() - platform.getySize() + distanceFromBoarder,
                                        platform.getYCoordinate() + platform.getySize() - distanceFromBoarder);

            PlayerData newPlayer = new PlayerData(names.get(i),10,xRandom,yRandom, ObjectState.IDLE, 0,5);
            if (numberofAIPlayers > 0) {
                ai.add(newPlayer);
                numberofAIPlayers--;
            } else {
                players.add(newPlayer);
            }
        }

    }

    private float getRandom(float min, float max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    private void createBalls(int tempBallsNumber) {
        //need number of balls somewhere

        for (int i = 0; i < tempBallsNumber; i++) {

            // set random location with random direction
            // probably towards the middle
            BallData newBall = new BallData(false,0,0,ObjectState.IDLE,0,10);
            balls.add(newBall);
        }

    }

    private void createWall() {

    }

    private void createPlatform() {
       // new PlatformData();

    }


}
