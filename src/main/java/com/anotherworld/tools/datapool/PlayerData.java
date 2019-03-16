package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.PlayerDisplayData;

import java.io.Serializable;

/**
 * Represents the raw data of a player shared throughout the system.
 * @author Alfi S
 */

public class PlayerData extends MovableData implements PlayerDisplayData, Serializable {

    private String characterID;

    private final int maxHealth;
    private int health;

    private int chargeLevel;

    private long timeStartedCharging;
    private int stunTimer;

    private boolean deadByFalling;
    private boolean shielded;
    private boolean timeStopper;

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

    public void copyObject(PlayerData data) {
        this.health = data.getHealth();
        this.deadByFalling = data.deadByFalling;
        super.copyObject(data);
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getChargeLevel() { return this.chargeLevel; }
    public void setChargeLevel(int chargeLevel) { this.chargeLevel = chargeLevel; }

    public long getTimeStartedCharging() { return this.timeStartedCharging; }
    public void setTimeStartedCharging(long timeStartedCharging) { this.timeStartedCharging = timeStartedCharging; }

    public int getStunTimer() { return this.stunTimer; }
    public void setStunTimer(int stunTimer) { this.stunTimer = stunTimer; }

    public boolean isDeadByFalling() { return this.deadByFalling; }
    public void setDeadByFalling(boolean deadByFalling) { this.deadByFalling = deadByFalling; }

    public boolean isShielded() { return this.shielded; }
    public void setShielded(boolean shielded) { this.shielded = shielded; }

    public boolean isTimeStopper() { return this.timeStopper; }
    public void setTimeStopper(boolean timeStopper) { this.timeStopper = timeStopper; }
}
