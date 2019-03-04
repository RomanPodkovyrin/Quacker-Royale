package com.anotherworld.tools.datapool;

import java.io.Serializable;

/**
 * Misc. data about the current GameSession.
 * @author Alfi S
 */
public class GameSessionData implements Serializable {

    private long ticksElapsed;
    private long timeToNextStage;
    private long timeLeft;

    public GameSessionData(long totalGameTime) {
        this.timeLeft        = totalGameTime;
        this.timeToNextStage = totalGameTime / 3; //TODO: Integrate '3' with config file.
        this.ticksElapsed    = 0;
    }

    public void copyObject(GameSessionData data) {
        this.ticksElapsed = data.ticksElapsed;
        this.timeToNextStage = data.timeToNextStage;
        this.timeLeft = data.timeLeft;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public long getTicksElapsed() { return ticksElapsed; }

    public void setTicksElapsed(long ticksElapsed) { this.ticksElapsed = ticksElapsed; }

    public long getTimeToNextStage() { return timeToNextStage; }

    public void incrementTicksElapsed() { this.ticksElapsed++; }

    public void decrementTimeLeft() { this.timeLeft--; }
}
