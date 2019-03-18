package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.PlayerData;

public class Player {

    public static int getChargeLevel(PlayerData playerData) {
        return playerData.getChargeLevel();
    }

    public static void setChargeLevel(PlayerData playerData, int chargeLevel) {
        playerData.setChargeLevel(chargeLevel);
    }

    public static long getTimeStartedCharging(PlayerData playerData) {
        return playerData.getTimeStartedCharging();
    }

    public static void setTimeStartedCharging(PlayerData playerData, long timeStartedCharging) {
        playerData.setTimeStartedCharging(timeStartedCharging);
    }

    public static void incrementChargeLevel(PlayerData playerData) {
        playerData.setChargeLevel(playerData.getChargeLevel() + 1);
    }

    public static int getStunTimer(PlayerData playerData) {
        return playerData.getStunTimer();
    }

    public static void setStunTimer(PlayerData playerData, int stunTimer) {
        playerData.setStunTimer(stunTimer);
    }

    public static void decrementStunTimer(PlayerData playerData) {
        if (playerData.getStunTimer() > 0)
            playerData.setStunTimer(playerData.getStunTimer() - 1);
    }

    public static boolean isDead(PlayerData playerData) {
        return playerData.getState().equals(ObjectState.DEAD);
    }

    public static void setDeadByFalling(PlayerData playerData, boolean deadByFalling) {
        playerData.setDeadByFalling(deadByFalling);
    }

    public static boolean isShielded(PlayerData playerData) {
        return playerData.isShielded();
    }

    public static void setShielded(PlayerData playerData, boolean shielded) {
        playerData.setShielded(shielded);
    }

    public static boolean isTimeStopper(PlayerData playerData) {
        return playerData.isTimeStopper();
    }

    public static void setTimeStopper(PlayerData playerData, boolean timeStopper) {
        playerData.setTimeStopper(timeStopper);
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
