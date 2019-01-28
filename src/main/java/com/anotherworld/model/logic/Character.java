package com.anotherworld.model.logic;

public class Character {
    private String characterID;
    private int health;
    private float xCoordinate;
    private float yCoordinate;

    public Character(String characterID, int health, float xCoordinate, float yCoordinate) {
        this.characterID = characterID;
        this.health = health;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public String getCharacterID() { return characterID; }
    public void setCharacterID(String characterID) { this.characterID = characterID; }

    public int getHealth() { return health; }
    public void setHealth(int health ) { this.health = health; }

    public float getXCoordinate() { return xCoordinate; }
    public float getYCoordinate() { return yCoordinate; }
    public void setCoordinates(float xCoordinate, float yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
