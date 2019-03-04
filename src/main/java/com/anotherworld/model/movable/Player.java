package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.PlayerData;

public class Player extends AbstractMovable {
    private boolean aiEnabled;

    PlayerData playerData;
    long timeStartedCharging;

    public Player(PlayerData playerData, boolean aiEnabled) {
        super(playerData);
        this.playerData = playerData;
        this.timeStartedCharging = 0;
    }

    public String getCharacterID() { return playerData.getObjectID(); }

    public void setCharacterID(String characterID) {
        playerData.setObjectID(characterID);
    }

    public int getHealth() { return playerData.getHealth(); }

    public void setHealth(int health) {
        playerData.setHealth(health);
    }

    public int getChargeLevel() { return playerData.getChargeLevel(); }

    public void setChargeLevel(int chargeLevel) { playerData.setChargeLevel(chargeLevel); }

    public long getTimeStartedCharging() { return timeStartedCharging; }

    public void setTimeStartedCharging(long time) { timeStartedCharging = time; }

    public void incrementChargeLevel() { this.setChargeLevel(this.getChargeLevel() + 1); }

    public boolean isDead() { return playerData.getState() == ObjectState.DEAD; }

    public void damage(int damageDealt) {
        playerData.setHealth(playerData.getHealth() - damageDealt);
    }

    public void kill() {
        playerData.setState(ObjectState.DEAD);
        playerData.setSpeed(0);
    }

}
