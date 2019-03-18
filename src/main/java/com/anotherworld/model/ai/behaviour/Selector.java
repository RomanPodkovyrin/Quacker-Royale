package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.GameSessionData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Executes the given jobs in order until one of them succeeds.
 * @author Roman
 *
 */
public class Selector extends Job {

    private static Logger logger = LogManager.getLogger(Selector.class);


    private  ArrayList<Job> jobs;

    /**
     * Initialise the Selector Job.
     *
     * @param jobs The Queue of Jobs to be executed in the given order
     */
    public Selector(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public void reset() {

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {

        

        logger.trace("Starting Selector Job");

        for (Job currentJob: jobs) {
            currentJob.start();

            if (currentJob.isRunning()) {
                logger.trace("Running current Job");
                currentJob.act(ai, players, balls, platform,session);
            }

            if (currentJob.isSuccess()) {
                succeed();
                logger.trace("Finishing Selector Job with success");
                return;
            } else if (currentJob.isFailure()) {
                logger.trace("Job Failed getting next one");
            } else {
                logger.trace("Job is still running");
            }
        }

        fail();
        logger.trace("Finishing Selector Job with fail: no more jobs in the list");

    }

    @Override
    public void start() {
        super.start();
    }
}
