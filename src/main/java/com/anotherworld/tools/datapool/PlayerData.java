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
    private int health;
    private final int maxHealth;

    public PlayerData(String characterID, int health,
                      float xCoordinate, float yCoordinate,
                      ObjectState state, float speed, float radius) {
        super(xCoordinate, yCoordinate, state, speed, radius);
        this.characterID = characterID;
        this.health = health;
        this.maxHealth = health;
    }

    public void copyObject(PlayerData data) {
        this.characterID = data.getCharacterID();
        this.health = data.getHealth();
//        this.maxHealth = data.getMaxHealth();
        super.copyObject(data);
    }

    public String getCharacterID() { return characterID; }
    public void setCharacterID(String characterID) { this.characterID = characterID; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    
    public int getMaxHealth() {
        return this.maxHealth;
    }
    
}
