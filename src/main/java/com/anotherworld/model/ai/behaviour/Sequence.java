package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Executes the given jobs in order until one of then fails.
 * @author Roman
 */
public class Sequence extends Job {

    private static Logger logger = LogManager.getLogger(Sequence.class);


    private Queue<Job> jobs;
    private Queue<Job> originalJobs;
    private Job currentJob;

    /**
     * Initialise the Sequence Job.
     *
     * @param jobs The Queue of jobs to be executed
     */
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {

        logger.info("Starting Sequence Job");

        if (jobs.isEmpty()) {
            succeed();
            logger.info("Finishing Sequence Job with success");
            return;
        } else if (currentJob.isSuccess()) {
            currentJob = jobs.poll();
        } else if (currentJob.isFailure()) {
            fail();
            logger.info("Finishing Sequence Job with fail");
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
