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

    public int getChargeLevel() { return playerData.getChargeLevel(); }

    public void setChargeLevel(int chargeLevel) { playerData.setChargeLevel(chargeLevel); }

    public long getTimeStartedCharging() { return playerData.getTimeStartedCharging(); }

    public void setTimeStartedCharging(long timeStartedCharging) { playerData.setTimeStartedCharging(timeStartedCharging); }

    public void incrementChargeLevel() { this.setChargeLevel(this.getChargeLevel() + 1); }

    public int getStunTimer() { return playerData.getStunTimer(); }

    public void setStunTimer(int stunTimer) { playerData.setStunTimer(stunTimer); }

    public void decrementStunTimer() {
        if (this.getStunTimer() > 0)
            this.setStunTimer(this.getStunTimer() - 1);
    }

    public boolean isDead()     { return playerData.getState().equals(ObjectState.DEAD); }

    public boolean isCharging() { return playerData.getState().equals(ObjectState.CHARGING); }

    public boolean isDashing()  { return playerData.getState().equals(ObjectState.DASHING);}

    public void damage(int damageDealt) {
        playerData.setHealth(playerData.getHealth() - damageDealt);
    }

    public void kill() {
        playerData.setState(ObjectState.DEAD);
        playerData.setSpeed(0);
    }

}
