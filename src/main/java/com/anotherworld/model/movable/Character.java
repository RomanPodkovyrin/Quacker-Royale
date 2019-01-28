package com.anotherworld.model.movable;

public class Character extends AbstractMovable {
    private String characterID;
    private int health;

    public Character(String characterID, int health, float xCoordinate,
                     float yCoordinate, ObjectState state) {
        super(xCoordinate, yCoordinate, state);
        this.characterID = characterID;
        this.health = health;
    }

    public String getCharacterID() { return characterID; }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }

    public int getHealth() { return health; }

    public void setHealth(int health) {
        this.health = health;
    }
}
