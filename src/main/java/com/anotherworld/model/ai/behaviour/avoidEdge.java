package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

public class avoidEdge extends Job {

    public avoidEdge() {
        super();
    }

    @Override
    public void reset() {
        reset();
    }

    @Override
    public void act(Player ai, Player[] players, Ball[] balls, Platform platform) {

    }

    private boolean checkIfNearEdge() {
        return true;
    }
}
