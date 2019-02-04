package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.Queue;

public class Sequence extends Job {

    private Queue<Job> jobs;
    private Queue<Job> originalJobs;
    private Job currentJob;

    public Sequence(Queue<Job> jobs) {
        this.jobs = jobs;
        this.originalJobs = jobs;
        if (jobs.isEmpty()) {
            succeed();
            return;
        }
        this.currentJob = jobs.poll();
    }

    @Override
    public void reset() {
        this.jobs = originalJobs;
        this.currentJob = jobs.poll();

    }

    @Override
    public void act(Player ai, Player[] players, Ball[] balls, Platform platform) {

        if (jobs.isEmpty()) {
            succeed();
            return;
        } else if (currentJob.isSuccess()) {
            currentJob = jobs.poll();
        } else if (currentJob.isFailure()) {
            fail();
            return;
        }

        if (currentJob.isRunning()) {
            currentJob.act(ai,players,balls,platform);
        }


    }

    @Override
    public void start() {
        super.start();
        this.currentJob.start();
    }
}
