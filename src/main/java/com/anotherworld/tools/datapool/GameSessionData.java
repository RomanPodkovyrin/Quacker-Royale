package com.anotherworld.tools.datapool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents the information about the running game session.
 * Also handles the power ups within the session.
 *
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
    private ArrayList<PowerUpData> powerUpSchedule;
    int currentPowerUp;

    /**
     * Class constructor for the game data.
     * @param totalGameTime The total duration of the game session in seconds.
     */
    public GameSessionData(long totalGameTime) {
        this.timeLeft        = totalGameTime;
        this.timeToNextStage = totalGameTime / 3; //TODO: Integrate '3' with config file.
        this.ticksElapsed    = 0;
        this.timeStopCounter = 0;
        this.timeStopped     = false;
        this.rankings        = new LinkedList<>();
        this.powerUpSchedule = new ArrayList<>();
        this.currentPowerUp  = 0;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied
     */
    public void copyObject(GameSessionData data) {
        this.timeLeft        = data.timeLeft;
        this.timeToNextStage = data.timeToNextStage;
        this.ticksElapsed    = data.ticksElapsed;
        this.timeStopCounter = data.timeStopCounter;
        this.timeStopped     = data.timeStopped;
        this.rankings        = data.rankings;
        this.currentPowerUp  = data.currentPowerUp;

        for (int i = 0 ; i < powerUpSchedule.size(); i++) {
            this.powerUpSchedule.get(i).copyObject(data.powerUpSchedule.get(i));
        }
    }

    /**
     * Gets the amount of time left in the game session.
     * @return amount of time left in the game in seconds.
     */
    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * Gets the amount of cycles that has elapsed since the game began.
     * @return The amount of cycles that has elapsed since the game began.
     */
    public int getTicksElapsed() {
        return ticksElapsed;
    }

    /**
     * Gets the amount of time until the platform and wall shrinks.
     * @return amount of time in seconds until the platform and wall shrinks.
     */
    public long getTimeToNextStage() {
        return timeToNextStage;
    }

    /**
     * Increments the amount of cycles that has elapsed.
     */
    public void incrementTicksElapsed() {
        this.ticksElapsed++;
    }

    /**
     * Decrements the number of seconds left in the game.
     */
    public void decrementTimeLeft() {
        this.timeLeft--;
    }

    /**
     * Gets the amount of time remaining that the time is stopped.
     * @return amount of time remaining that the time is stopped.
     */
    public int getTimeStopCounter() {
        return this.timeStopCounter;
    }

    /**
     * Sets the duration that the time is stopped.
     * @param timeStopCounter amount of time that the time is stopped.
     */
    public void setTimeStopCounter(int timeStopCounter) {
        this.timeStopCounter = timeStopCounter;
    }

    /**
     * Decrements the counter that determines the duration that the time is stopped.
     */
    public void decrementTimeStopCounter() {
        this.timeStopCounter--;
    }

    /**
     * Checks whether or not the time is currently stopped by a power up.
     * @return true if the time is stopped, false otherwise.
     */
    public boolean isTimeStopped() {
        return this.timeStopped;
    }

    /**
     * Sets whether or not the time is currently stopped by a power up.
     * @param timeStopped boolean determining whether or not the time is stopped.
     */
    public void setTimeStopped(boolean timeStopped) {
        this.timeStopped = timeStopped;
    }

    /**
     * Gets the rankings of the players based on the order they died.
     * @return linked list representing the rankings of the players in a game.
     */
    public LinkedList<String> getRankings() {
        return this.rankings;
    }

    /**
     * Gets the schedule of power up spawns.
     * @return linked list representing the schedule of power up spawns.
     */
    public ArrayList<PowerUpData> getPowerUpSchedule() {
        return this.powerUpSchedule;
    }

    /**
     * Sets the schedule of power up spawns.
     * @param schedule the schedule determining the spawn times of the power ups.
     */
    public void setPowerUpSchedule(ArrayList<PowerUpData> schedule) {
        this.powerUpSchedule = schedule;
    }

    /**
     * Gets of the current power up that is spawned.
     * @return the power up that is currently spawned.
     */
    public PowerUpData getCurrentPowerUp() {
        return this.powerUpSchedule.get(currentPowerUp);
    }

    /**
     *
     */
    public int getPowerUpIndex() {
        return this.currentPowerUp;
    }

    /**
     * Sets the current power up that is spawned to an index in the power up schedule.
     * @param powerUpIndex The index of the power up to spawn.
     */
    public void setCurrentPowerUp(int powerUpIndex) {
        this.currentPowerUp = powerUpIndex;
    }
}
