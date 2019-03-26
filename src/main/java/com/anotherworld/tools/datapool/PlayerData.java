package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.PlayerDisplayData;

import java.io.Serializable;

/**
 * Class that represents the positions, states, and timers of a player.
 * @author Alfi S
 */

public class PlayerData extends MovableData implements PlayerDisplayData, Serializable {

    private final int maxHealth;
    private int health;

    private int chargeLevel;

    private long timeStartedCharging;
    private int stunTimer;

    private boolean deadByFalling;
    private boolean shielded;
    private boolean timeStopper;

    /**
     * Class constructor for a player object.
     * @param objectID The id of the player.
     * @param health The starting health of the player.
     * @param xCoordinate The x coordinate of the player.
     * @param yCoordinate The y coordinate of the player.
     * @param state The state of the player.
     * @param speed The speed of the player.
     * @param radius The radius of the player.
     */
    public PlayerData(String objectID, int health,
                      float xCoordinate, float yCoordinate,
                      ObjectState state, float speed, float radius) {
        super(objectID, xCoordinate, yCoordinate, state, speed, radius);
        this.health = health;
        this.maxHealth = health;
        this.chargeLevel = 0;
        this.timeStartedCharging = 0;
        this.stunTimer = 0;
        this.deadByFalling = false;
        this.shielded = false;
        this.timeStopper = false;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied.
     */
    public void copyObject(PlayerData data) {
        this.health = data.getHealth();
        this.deadByFalling = data.deadByFalling;
        super.copyObject(data);
    }

    /**
     * Gets the current health of the player.
     * @return the current health of the player.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the current health of the player.
     * @param health the current health to set.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the maximum health of the player.
     * @return the maximum health of the player.
     */
    public int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Gets the current charge level of the player
     * which determines the speed of the dash.
     * @return the current charge level of the player.
     */
    public int getChargeLevel() {
        return this.chargeLevel;
    }

    /**
     * Sets the current charge level of the player
     * which determines the speed of the dash.
     * @param chargeLevel the charge level to set.
     */
    public void setChargeLevel(int chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

    /**
     * Gets the time the player started charging their dash.
     * @return the time the player started charging their dash.
     */
    public long getTimeStartedCharging() {
        return this.timeStartedCharging;
    }

    /**
     * Sets the time the player started starting their dash.
     * @param timeStartedCharging the starting time to set.
     */
    public void setTimeStartedCharging(long timeStartedCharging) {
        this.timeStartedCharging = timeStartedCharging;
    }

    /**
     * Gets the amount of time left that the player is rendered immobile.
     * @return the amount of time left that the player is stunned.
     */
    public int getStunTimer() {
        return this.stunTimer;
    }

    /**
     * Sets the duration that the player is rendered immobile.
     * @param stunTimer the duration to set.
     */
    public void setStunTimer(int stunTimer) {
        this.stunTimer = stunTimer;
    }

    /**
     * Checks whether the player died by falling off the platform.
     * @return true if the player died by falling, false otherwise.
     */
    public boolean isDeadByFalling() {
        return this.deadByFalling;
    }

    /**
     * Sets whether the player died by falling off the platform.
     * @param deadByFalling whether or not the player fell off the platform.
     */
    public void setDeadByFalling(boolean deadByFalling) {
        this.deadByFalling = deadByFalling;
    }

    /**
     * Checks whether the player has a shield buff.
     * @return true if the player has a shield buff, false otherwise.
     */
    public boolean isShielded() {
        return this.shielded;
    }

    /**
     * Sets whether the player has a shield buff.
     * @param shielded whether the player has a shield buff.
     */
    public void setShielded(boolean shielded) {
        this.shielded = shielded;
    }

    /**
     * Checks whether the player has stopped time.
     * @return true if the player stopped time, false otherwise.
     */
    public boolean isTimeStopper() {
        return this.timeStopper;
    }

    /**
     * Sets whether the player has stopped time.
     * @param timeStopper whether the player stopped time.
     */
    public void setTimeStopper(boolean timeStopper) {
        this.timeStopper = timeStopper;
    }
}
