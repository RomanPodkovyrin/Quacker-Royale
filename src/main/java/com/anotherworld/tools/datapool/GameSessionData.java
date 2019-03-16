package com.anotherworld.tools.datapool;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Misc. data about the current GameSession.
 * @author Alfi S
 */
public class GameSessionData implements Serializable {

    // Time based elements of the game
    private int ticksElapsed;
    private long timeToNextStage;
    private long timeLeft;

    // Ordered list of player rankings.
    private LinkedList<String> rankings;

    // Power up elements of the game
    private LinkedList<PowerUpData> powerUpSchedule;
    Optional<PowerUpData> currentPowerUp;

    public GameSessionData(long totalGameTime) {
        this.timeLeft        = totalGameTime;
        this.timeToNextStage = totalGameTime / 3; //TODO: Integrate '3' with config file.
        this.ticksElapsed    = 0;
        this.rankings        = new LinkedList<>();
        this.powerUpSchedule = new LinkedList<>();
        this.currentPowerUp  = Optional.empty();
    }

    public void copyObject(GameSessionData data) {
        this.ticksElapsed = data.ticksElapsed;
        this.timeToNextStage = data.timeToNextStage;
        this.timeLeft = data.timeLeft;
        this.rankings = data.rankings;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public int getTicksElapsed() { return ticksElapsed; }

    public long getTimeToNextStage() { return timeToNextStage; }

    public void incrementTicksElapsed() { this.ticksElapsed++; }

    public void decrementTimeLeft() { this.timeLeft--; }

    public LinkedList<String> getRankings() { return this.rankings; }

    public LinkedList<PowerUpData> getPowerUpSchedule() { return this.powerUpSchedule; }

    public void setPowerUpSchedule(LinkedList<PowerUpData> schedule) { this.powerUpSchedule = schedule; }

    public Optional<PowerUpData> getCurrentPowerUp() { return this.currentPowerUp; }

    public void setCurrentPowerUp(PowerUpData powerUp) {
        this.currentPowerUp = Optional.of(powerUp);
    }
}
