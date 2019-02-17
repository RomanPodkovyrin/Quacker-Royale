package com.anotherworld.model.ai;

import com.anotherworld.model.movable.Ball;

import java.util.ArrayList;

public class AIDataPool {
    private static ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private static  ArrayList<Ball> dangerBalls = new ArrayList<>();
    private static ArrayList<Ball> imminentDangerBalls = new ArrayList<>();

    private static boolean isDataPresent = false;

    public static void setBalls(ArrayList<Ball> possibleDangerBalls_,ArrayList<Ball> dangerBalls_,ArrayList<Ball> imminentDangerBalls_) {
        possibleDangerBalls =possibleDangerBalls_;
        dangerBalls = dangerBalls_;
        imminentDangerBalls = imminentDangerBalls_;
        isDataPresent = true;
    }

    public static boolean IsDataPresent() { return isDataPresent; }

    public static ArrayList<Ball> getPossibleDangerBalls() {
        return possibleDangerBalls;
    }

    public static ArrayList<Ball> getDangerBalls() {
        return dangerBalls;
    }

    public static ArrayList<Ball> getImminentDangerBalls() {
        return imminentDangerBalls;
    }
}
