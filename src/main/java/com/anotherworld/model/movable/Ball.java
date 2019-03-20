package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.BallData;

public class Ball {

    public static int getDamage(BallData ballData) {
        return ballData.getDamage();
    }

    public static boolean isDangerous(BallData ballData) {
        return ballData.isDangerous();
    }
    public static void setDangerous(BallData ballData, boolean dangerous) {
        ballData.setDangerous(dangerous);
    }

    public static int getTimer(BallData ballData) {
        return ballData.getTimer();
    }
    public static void setTimer(BallData ballData, int time) {
        ballData.setTimer(time);
    }

    public static void reduceTimer(BallData ballData, int amount) {
        ballData.setTimer(ballData.getTimer() - amount);
    }

}
