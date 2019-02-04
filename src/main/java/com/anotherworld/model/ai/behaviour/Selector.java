package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;
import java.util.Queue;

public class Selector extends Job {

    private  Queue<Job> jobs;
    private Queue<Job> originalJobs;
    private Job currentJob;

    public Selector(Queue<Job> jobs) {
        this.jobs = jobs;
        this.originalJobs = jobs;
        if (jobs.isEmpty()) {
            isSuccess();
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


        if (currentJob.isSuccess()) {
            succeed();
            return;
        } else if (jobs.isEmpty()) {
            fail();
            return;
        } else if (currentJob.isFailure()) {
            currentJob = jobs.poll();
            currentJob.start();
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
