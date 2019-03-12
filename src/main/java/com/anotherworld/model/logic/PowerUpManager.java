package com.anotherworld.model.logic;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;

import java.util.LinkedList;
import java.util.Random;

/**
 * Handles generation and representation of powerups in the game.
 * Only one power up will appear on the platform at a time,
 * and the time of its appearance will be randomised.
 *
 * @author Alfi S.
 */

public class PowerUpManager {

    /**
     * Generates a Power Up at a random location within the range of the platform
     * @param platform platform determining the range of the coordinates for the powerup
     * @return linked list scheduling the power up spawns.
     */
    public static LinkedList<PowerUpData> generatePowerUpSchedule(long totalTime, Platform platform) {

        LinkedList<PowerUpData> output = new LinkedList<>();
        Random random = new Random();

        for(long i = totalTime; i > 0; i--) {

            float generationProbability = random.nextFloat();

            if (generationProbability < 0.2) {
                float x  = platform.getXSize() * (random.nextFloat() * 2 - 1);
                float y  = platform.getYSize() * (random.nextFloat() * 2 - 1);

                Matrix coordinates = new Matrix(x,y);
                int choice = random.nextInt(PowerUpType.values().length);
                PowerUpType type = PowerUpType.values()[choice];

                output.addLast(new PowerUpData(coordinates, type, i));
            }

        }

        return output;
    }

}
