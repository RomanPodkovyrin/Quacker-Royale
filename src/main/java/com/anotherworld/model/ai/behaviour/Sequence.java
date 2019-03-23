package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;

import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Executes the given jobs in order until no more jobs left.
 * @author Roman
 */
public class Sequence extends Job {

    private static Logger logger = LogManager.getLogger(Sequence.class);


    private ArrayList<Job> jobs;

    /**
     * Initialise the Sequence Job.
     *
     * @param jobs The Queue of jobs to be executed
     */
    public Sequence(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        logger.trace("Starting Sequence Job");

        if (jobs.isEmpty()) {
            succeed();
            logger.trace("Finishing Sequence Job with success: No jobs");
            return;
        }

        for (Job currentJob: jobs) {
            currentJob.start();

            if (currentJob.isRunning()) {
                logger.trace("Running current Job");
                currentJob.act(ai,players,balls,platform,session);
            }

            if (currentJob.isSuccess() | currentJob.isFailure()) {
                logger.trace("Getting next job");
            }
        }

        succeed();
        logger.trace("Finishing SequenceSuccess Job with success: All jobs succeeded");
        return;





    }

    @Override
    public void start() {
        super.start();
    }
}
