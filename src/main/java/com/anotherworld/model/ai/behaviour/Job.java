package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

import java.util.ArrayList;

/**
 * Represents the job which has 3 states file success or running.
 * @author Roman
 */
public abstract class Job {

    public enum JobState {
        SUCCESS,FAILURE, RUNNING
    }

    protected JobState state;

    protected PlayerData ai;
    protected ArrayList<PlayerData> players;
    protected ArrayList<BallData> balls;
    protected Platform platform;
    protected GameSessionData session;

    /**
     * Call when need to initialise a new Job.
     */
    public Job() {

    }

    /**
     * Starts the Job and sets the Job state to RUNNING.
     */
    public void start() {
        this.state = JobState.RUNNING;
    }

    /**
     * Resets the job.
     */
    public abstract void reset();

    /**
     * Tells the job to act based on the current situation.
     *
     * @param ai The AIController player who is doing the job
     * @param players The other players on the board
     * @param balls All the balls on the boards
     * @param platform The platform
     */
    public abstract void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session);

    /**
     * Sets all te given data to the variables.
     *
     * @param ai The AIController player who is doing the job
     * @param players The other players on the board
     * @param balls All the balls on the boards
     * @param platform The platform
     */
    protected void setData(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;
    }

    /**
     * Sets the Job state to SUCCESS.
     */
    protected void succeed() {
        this.state = JobState.SUCCESS;
    }

    /**
     * Sets the Job state to FAILURE.
     */
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
