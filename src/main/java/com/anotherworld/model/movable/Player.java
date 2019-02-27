package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.PlayerData;

public class Player extends AbstractMovable {
    private boolean aiEnabled;

    PlayerData playerData;

    public Player(PlayerData playerData, boolean aiEnabled) {
        super(playerData);
        this.playerData = playerData;
    }

    public String getCharacterID() { return playerData.getObjectID(); }

    public void setCharacterID(String characterID) {
        playerData.setObjectID(characterID);
    }

    public int getHealth() { return playerData.getHealth(); }

    public void setHealth(int health) {
        playerData.setHealth(health);
    }

    public boolean isAIEnabled() { return aiEnabled; }

    public boolean isDead() { return playerData.getState() == ObjectState.DEAD; }

    public void damage(int damageDealt) {
        playerData.setHealth(playerData.getHealth() - damageDealt);
    }

    public void kill() {
        playerData.setState(ObjectState.DEAD);
        playerData.setSpeed(0);
    }

}
