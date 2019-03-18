package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Executes the given jobs in order until one of then fails.
 *
 * @author Roman
 */
public class SequenceSuccess extends Job {

    private static Logger logger = LogManager.getLogger(SequenceSuccess.class);


    private ArrayList<Job> jobs;

    /**
     * Initialise the SequenceSuccess Job.
     *
     * @param jobs The Queue of jobs to be executed
     */
    public SequenceSuccess(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {
        logger.trace("Starting SequenceSuccess Job");


        if (jobs.isEmpty()) {
            succeed();
            logger.trace("Finishing SequenceSuccess Job with success: Empty");
            return;
        }

        for (Job currentJob: jobs) {
            currentJob.start();

            if (currentJob.isRunning()) {
                logger.trace("Running current Job");
                currentJob.act(ai, players, balls, platform,session);
            }

            if (currentJob.isFailure()) {
                fail();
                logger.trace("Finishing SequenceSuccess Job with fail: job failed");
                return;
            } else if (currentJob.isSuccess()) {
                logger.trace("SequenceSuccess getting next job: Job succeeded");
            }
        }

        succeed();
        logger.trace("Finishing SequenceSuccess Job with success: all Jobs succeeded");
        return;




    }

    @Override
    public void start() {
        super.start();
    }
}
