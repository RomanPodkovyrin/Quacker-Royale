package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

public abstract class Job {
    public enum JobState {
        SUCCESS,FAILURE, RUNNING
    }
    protected JobState state;

    protected Player ai;
    protected Player[] players;
    protected Ball[] balls;
    protected Platform platform;


    public Job(Player ai, Player[] players, Ball[] balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
    }

    public void start() {
        this.state = JobState.RUNNING;
    }

    public abstract void reset();

    public abstract void act();

    protected void succeed() {
        this.state = JobState.SUCCESS;
    }

    protected void fail() {
        this.state = JobState.FAILURE;
    }

    public boolean isSuccess() {
        return state.equals(JobState.SUCCESS);
    }

    public boolean isFailure() {
        return state.equals(JobState.FAILURE);
    }

    public boolean isRunning() {
        return state.equals(JobState.RUNNING);
    }

    public JobState getState() {
        return state;
    }
}
