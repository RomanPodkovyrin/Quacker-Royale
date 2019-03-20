package com.anotherworld.tools.datapool;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Misc. data about the current GameSession.
 * @author Alfi S
 */
public class GameSessionData implements Serializable {

    // Time based elements of the game
    private int ticksElapsed;
    private long timeToNextStage;
    private long timeLeft;

    // Time stopping mechanic in the game
    private int timeStopCounter;
    private boolean timeStopped;

    // Ordered list of player rankings.
    private LinkedList<String> rankings;

    // Power up elements of the game
    private LinkedList<PowerUpData> powerUpSchedule;
    PowerUpData currentPowerUp;

    public GameSessionData(long totalGameTime) {
        this.timeLeft        = totalGameTime;
        this.timeToNextStage = totalGameTime / 3; //TODO: Integrate '3' with config file.
        this.ticksElapsed    = 0;
        this.timeStopCounter = 0;
        this.timeStopped     = false;
        this.rankings        = new LinkedList<>();
        this.powerUpSchedule = new LinkedList<>();
        this.currentPowerUp  = null;
    }

    public void copyObject(GameSessionData data) {
        this.timeLeft        = data.timeLeft;
        this.timeToNextStage = data.timeToNextStage;
        this.ticksElapsed    = data.ticksElapsed;
        this.timeStopCounter = data.timeStopCounter;
        this.timeStopped     = data.timeStopped;
        this.rankings        = data.rankings;
        this.powerUpSchedule = data.powerUpSchedule;
        if (data.currentPowerUp != null)
            this.currentPowerUp.copyObject(data.currentPowerUp);
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public int getTicksElapsed() {
        return ticksElapsed;
    }

    public long getTimeToNextStage() {
        return timeToNextStage;
    }

    public void incrementTicksElapsed() {
        this.ticksElapsed++;
    }

    public void decrementTimeLeft() {
        this.timeLeft--;
    }

    public int getTimeStopCounter() {
        return this.timeStopCounter;
    }

    public void setTimeStopCounter(int timeStopCounter) {
        this.timeStopCounter = timeStopCounter;
    }

    public void decrementTimeStopCounter() {
        this.timeStopCounter--;
    }

    public boolean isTimeStopped() {
        return this.timeStopped;
    }

    public void setTimeStopped(boolean timeStopped) {
        this.timeStopped = timeStopped;
    }

    public LinkedList<String> getRankings() {
        return this.rankings;
    }

    public LinkedList<PowerUpData> getPowerUpSchedule() {
        return this.powerUpSchedule;
    }

    public void setPowerUpSchedule(LinkedList<PowerUpData> schedule) {
        this.powerUpSchedule = schedule;
    }

    public PowerUpData getCurrentPowerUp() {
        return this.currentPowerUp;
    }

    public void setCurrentPowerUp(PowerUpData powerUp) {
        this.currentPowerUp = powerUp;
    }
}
