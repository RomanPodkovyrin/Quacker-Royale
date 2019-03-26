package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.BallData;

/**
 * Class that contains logic for ball objects.
 *
 * @author Alfi S.
 */
public class Ball {

    /**
     * Reduces the timer that determines how long a ball should be in a
     * state where it can damage players.
     * @param ballData The ball to update.
     * @param amount The amount to reduce the timer by.
     */
    public static void reduceTimer(BallData ballData, int amount) {
        ballData.setTimer(ballData.getTimer() - amount);
    }

}
