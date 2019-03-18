package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.PlayerData;

public class Player {

    public static void incrementChargeLevel(PlayerData playerData) {
        playerData.setChargeLevel(playerData.getChargeLevel() + 1);
    }


    public static void decrementStunTimer(PlayerData playerData) {
        if (playerData.getStunTimer() > 0)
            playerData.setStunTimer(playerData.getStunTimer() - 1);
    }

    public static boolean isDead(PlayerData playerData) {
        return playerData.getState().equals(ObjectState.DEAD);
    }

    public static boolean isCharging(PlayerData playerData) {
        return playerData.getState().equals(ObjectState.CHARGING);
    }

    public static boolean isDashing(PlayerData playerData)  {
        return playerData.getState().equals(ObjectState.DASHING);
    }

    public static void damage(PlayerData playerData, int damageDealt) {
        playerData.setHealth(playerData.getHealth() - damageDealt);
    }

    public static void kill(PlayerData playerData) {
        playerData.setState(ObjectState.DEAD);
        playerData.setSpeed(0);
    }
}
