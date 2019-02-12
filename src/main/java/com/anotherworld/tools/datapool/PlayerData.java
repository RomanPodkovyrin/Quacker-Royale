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

    public PlayerData(String characterID, int health,
                      float xCoordinate, float yCoordinate,
                      ObjectState state, float speed, float radius) {
        super(xCoordinate, yCoordinate, state, speed, radius);
        this.characterID = characterID;
        this.health = health;
    }

    public String getCharacterID() { return characterID; }
    public void setCharacterID(String characterID) { this.characterID = characterID; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}
