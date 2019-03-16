package com.anotherworld.model.logic;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.Player;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

/**
 * Handles generation and representation of powerups in the game
 * Only one power up will appear on the platform at a time,
 * and the time of its appearance will be randomised.
 *
 * @author Alfi S.
 */

public class PowerUpManager {

    /**
     * Generates a Power Up at a random location within the range of the platform.
     * @param platform platform determining the range of the coordinates for the powerup
     * @return linked list scheduling the power up spawns.
     */
    public static LinkedList<PowerUpData> generatePowerUpSchedule(long totalTime, PlatformData platform) {

        LinkedList<PowerUpData> output = new LinkedList<>();
        Random random = new Random();

        for (long i = totalTime; i > 0; i--) {

            float generationProbability = (float) Math.random();

            if (generationProbability < .1) { //TODO: Magic Number
                float x  = platform.getxSize() * (random.nextFloat() * 2 - 1);
                float y  = platform.getySize() * (random.nextFloat() * 2 - 1);

                Matrix coordinates = new Matrix(x,y);
                int choice = random.nextInt(PowerUpType.values().length);
                PowerUpType type = PowerUpType.values()[choice];

                output.addLast(new PowerUpData(coordinates, type, i));
            }

        }

        return output;
    }

    /**
     * Sets the current power up to the latest power up in the schedule.
     * @param data the game session data which holds all the power ups.
     */
    public static void spawnPowerUp(GameSessionData data) {
        PowerUpData currentPowerUp;
        if ((currentPowerUp = data.getPowerUpSchedule().peek()) != null) {
            if (currentPowerUp.getSpawnTime() == data.getTimeLeft()) {
                data.getPowerUpSchedule().pop();
                data.setCurrentPowerUp(Optional.of(currentPowerUp));
            }
        }
    }

    /**
     * When a player collects a powerup, the function applies the effects.
     * depending on the type of the powerup
     * @param player The player collecting the powerup
     * @param data The game session data which holds all the information.
     */
    public static void collect(Player player, GameSessionData data) {
        Optional<PowerUpData> pu = data.getCurrentPowerUp();
        if (pu.isPresent()) {
            PowerUpData powerUp = pu.get();
            if (Physics.checkCollision(player, powerUp.getCoordinates(), powerUp.getRadius())) {
                //Apply effects
                switch (powerUp.getPowerUpType()) {
                    case HEAL:
                        player.setHealth(GameSettings.getDefaultPlayerHealth());
                        //TODO: Play heal sound effect
                        break;

                    case TIME_STOP:
                        player.setTimeStopper(true);
                        data.setTimeStopped(true);
                        //TODO: Play time stop sound effect
                        break;

                    case SHIELD:
                        player.setShielded(true);
                        //TODO: Play shield pickup sound effect
                        break;

                    default:
                        break;
                }

                //Delete the current power up
                data.setCurrentPowerUp(Optional.empty());
            }
        }
    }

}
