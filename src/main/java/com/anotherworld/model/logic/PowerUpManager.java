package com.anotherworld.model.logic;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;
import com.anotherworld.tools.maths.Matrix;

import java.util.ArrayList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles generation and representation of powerups in the game
 * Only one power up will appear on the platform at a time,
 * and the time of its appearance will be randomised.
 *
 * @author Alfi S.
 */

public class PowerUpManager {
    
    private static Logger logger = LogManager.getLogger(PowerUpManager.class);

    /**
     * Generates a Power Up at a random location within the range of the platform.
     * @param platform platform determining the range of the coordinates for the powerup
     * @return linked list scheduling the power up spawns.
     */
    public static ArrayList<PowerUpData> generatePowerUpSchedule(long totalTime, PlatformData platform) {

        ArrayList<PowerUpData> output = new ArrayList<>();
        Random random = new Random();

        for (long i = totalTime; i > 0; i -= 5) {

            float generationProbability = (float) Math.random();

            if (generationProbability < .7) { //TODO: Magic Number
                float x  = platform.getXCoordinate()
                        + (platform.getxSize() - (PlatformData.getxShrink() * (platform.getStage() - 1)))
                        * ((float) Math.random());
                float y  = platform.getYCoordinate()
                        + (platform.getySize() - (PlatformData.getyShrink() * (platform.getStage() - 1)))
                        * ((float) Math.random());

                Matrix coordinates = new Matrix(x,y);

                int choice = random.nextInt(PowerUpType.values().length);
                PowerUpType type = PowerUpType.values()[choice];

                output.add(new PowerUpData(coordinates, type, i));
            }

        }

        return output;
    }

    /**
     * Sets the current power up to the latest power up in the schedule.
     * @param data the game session data which holds all the power ups.
     */
    public static void spawnPowerUp(GameSessionData data) {
        PowerUpData currentPowerUp = data.getCurrentPowerUp();

        if (data.getPowerUpIndex() + 1 < data.getPowerUpSchedule().size()) {
            if (data.getPowerUpSchedule().get(data.getPowerUpIndex() + 1).getSpawnTime() == data.getTimeLeft()) {
                currentPowerUp.setState(ObjectState.INACTIVE);
                data.setCurrentPowerUp(data.getPowerUpIndex() + 1);
                data.getCurrentPowerUp().setState(ObjectState.ACTIVE);
            }
        }
    }

    /**
     * When a player collects a powerup, the function applies the effects.
     * depending on the type of the powerup
     * @param player The player collecting the powerup
     * @param data The game session data which holds all the information.
     */
    public static void collect(PlayerData player, GameSessionData data) {
        PowerUpData powerUp = data.getCurrentPowerUp();
        if (powerUp.getState() == ObjectState.ACTIVE) {
            if (Physics.checkCollision(player, powerUp.getCoordinates(), powerUp.getRadius())) {
                //Apply effects
                switch (powerUp.getPowerUpType()) {
                    case HEAL:
                        logger.debug(player.getObjectID() + " was healed");
                        player.setHealth(GameSettings.getDefaultPlayerHealth());
                        AudioControl.health();
                        break;

                    case TIME_STOP:
                        logger.debug(player.getObjectID() + " stopped time");
                        player.setTimeStopper(true);
                        data.setTimeStopped(true);
                        data.setTimeStopCounter(3); // TODO: Yet another magic number
                        AudioControl.time();
                        break;

                    case SHIELD:
                        logger.debug(player.getObjectID() + " has a shield");
                        player.setShielded(true);
                        AudioControl.shield();
                        break;

                    default:
                        break;
                }

                powerUp.setState(ObjectState.INACTIVE);
            }
        }
    }
}
