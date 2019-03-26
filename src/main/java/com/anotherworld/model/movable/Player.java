package com.anotherworld.model.movable;

import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

/**
 * Class that contains logic for the player objects.
 *
 * @author Alfi S.
 */
public class Player {

    /**
     * Increments the charge level of the player which determines
     * the speed of their dash.
     * @param playerData The player to update.
     */
    public static void incrementChargeLevel(PlayerData playerData) {
        playerData.setChargeLevel(playerData.getChargeLevel() + 1);
    }

    /**
     * Decrements the stun timer of the player which determines
     * how long the player is stunned/unable to move.
     * @param playerData the player to update.
     */
    public static void decrementStunTimer(PlayerData playerData) {
        if (playerData.getStunTimer() > 0) {
            playerData.setStunTimer(playerData.getStunTimer() - 1);
        }
    }

    /**
     * Checks if a player is dead.
     * @param playerData The player to check.
     * @return true if the object state of the player is DEAD, false otherwise.
     */
    public static boolean isDead(PlayerData playerData) {
        return playerData.getState().equals(ObjectState.DEAD);
    }

    /**
     * Checks if the player is charging their dash.
     * @param playerData The player to check.
     * @return true if the object state of the player is CHARGING, false otherwise.
     */
    public static boolean isCharging(PlayerData playerData) {
        return playerData.getState().equals(ObjectState.CHARGING);
    }

    /**
     * Checks if the player is currently dashing.
     * @param playerData The player to check.
     * @return true if the object state of the player is DASHING, false otherwise.
     */
    public static boolean isDashing(PlayerData playerData)  {
        return playerData.getState().equals(ObjectState.DASHING);
    }

    /**
     * Reduces the health of a specified player.
     * @param playerData The player to update.
     * @param damageDealt The amount of damage dealt to the player.
     */
    public static void damage(PlayerData playerData, int damageDealt) {
        playerData.setHealth(playerData.getHealth() - damageDealt);
    }

    /**
     * Kills a player by setting their state to dead and removing their ability to move.
     * @param playerData The player to update.
     * @param byFalling
     */
    public static void kill(PlayerData playerData, boolean byFalling) {
        playerData.setState(ObjectState.DEAD);
        playerData.setSpeed(0);
        playerData.setDeadByFalling(byFalling);
    }

    /**
     * Handles the parameters relating to the players dash.
     * @param gameData the game session data to access time information.
     * @param player the player to dash.
     */
    public static void chargeForward(GameSessionData gameData, PlayerData player) {
        long timeSpent = gameData.getTicksElapsed() - player.getTimeStartedCharging();

        if(isCharging(player)) {
            player.setVelocity(0,0);
            player.setSpeed(0);
        } else if (isDashing(player)) {
            if (timeSpent == 0) {
                Physics.charge(player);
            } else if (timeSpent >= 10) {
                player.setSpeed(GameSettings.getDefaultPlayerSpeed());
                player.setState(ObjectState.IDLE);
                player.setChargeLevel(0);
                player.setVelocity(0,0);
            }
        }
    }

    /**
     * Handles moving a player taking into account time stops.
     * @param gameData the game data to access whether or not the time is stopped.
     * @param player the player to update.
     */
    public static void movePlayer(GameSessionData gameData, PlayerData player) {
        if (!gameData.isTimeStopped()) {
            if (player.isTimeStopper()) {
                player.setTimeStopper(false);
            }
            Physics.move(player);
        } else {
            if (player.isTimeStopper()) {
                Physics.move(player);
            }
        }
    }
}
