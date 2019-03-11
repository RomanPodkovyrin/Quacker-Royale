package com.anotherworld.tools.datapool;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Misc. data about the current GameSession.
 * @author Alfi S
 */
public class GameSessionData implements Serializable {

    private int ticksElapsed;
    private long timeToNextStage;
    private long timeLeft;
    private LinkedList<String> rankings;

    public GameSessionData(long totalGameTime) {
        this.timeLeft        = totalGameTime;
        this.timeToNextStage = totalGameTime / 3; //TODO: Integrate '3' with config file.
        this.ticksElapsed    = 0;
        this.rankings        = new LinkedList<>();
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

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTicksElapsed() { return ticksElapsed; }

    public void setTicksElapsed(int ticksElapsed) { this.ticksElapsed = ticksElapsed; }

    public long getTimeToNextStage() { return timeToNextStage; }

    public void incrementTicksElapsed() { this.ticksElapsed++; }

    public void decrementTimeLeft() { this.timeLeft--; }

    public LinkedList<String> getRankings() { return this.rankings; }

}
