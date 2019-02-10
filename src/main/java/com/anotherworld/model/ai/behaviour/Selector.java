package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Executes the given jobs in order until one of them succeeds.
 * @author  Roman
 *
 */
public class Selector extends Job {

    private static Logger logger = LogManager.getLogger(Selector.class);


    private  Queue<Job> jobs;
    private Queue<Job> originalJobs;
    private Job currentJob;

    /**
     * Initialise the Selector Job.
     *
     * @param jobs The Queue of Jobs to be executed in the given order
     */
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {

        logger.info("Starting Selector Job");
        if (currentJob.isSuccess()) {
            succeed();
            logger.info("Finishing Selector Job with success");
            return;
        } else if (jobs.isEmpty()) {
            fail();
            logger.info("Finishing Selector Job with fail");
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
