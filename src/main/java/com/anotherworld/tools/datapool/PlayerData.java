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

    public PlayerData(String objectID, int health,
                      float xCoordinate, float yCoordinate,
                      ObjectState state, float speed, float radius) {
        super(objectID, xCoordinate, yCoordinate, state, speed, radius);
        this.health = health;
        this.maxHealth = health;
        this.chargeLevel = 0;
    }

    public void copyObject(PlayerData data) {
        this.health = data.getHealth();
//        this.maxHealth = data.getMaxHealth();
        super.copyObject(data);
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getChargeLevel() { return this.chargeLevel; }
    public void setChargeLevel(int chargeLevel) { this.chargeLevel = chargeLevel; }
    
}
