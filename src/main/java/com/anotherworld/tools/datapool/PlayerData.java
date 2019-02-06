package com.anotherworld.tools.datapool;

import com.anotherworld.view.data.CircleDisplayData;

/**
 * Represents the raw data of a player shared throughout the system.
 * @author Alfi S
 */

public class PlayerData extends MovableData implements CircleDisplayData {

    private String characterID;
    private int health;

    public String getCharacterID() { return characterID; }
    public void setCharacterID(String characterID) { this.characterID = characterID; }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}
