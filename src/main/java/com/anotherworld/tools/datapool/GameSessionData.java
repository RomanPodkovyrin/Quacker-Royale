package com.anotherworld.tools.datapool;

import java.io.Serializable;

/**
 * Misc. data about the current GameSession.
 * @author Alfi S
 */
public class GameSessionData implements Serializable {

    private long timeLeft;

    GameSessionData(long totalGameTime) {
        this.timeLeft = totalGameTime;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public long setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }
}
