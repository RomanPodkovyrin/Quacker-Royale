package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Executes the given jobs in order until no more jobs left
 * @author Roman
 */
public class Sequence extends Job {

    private static Logger logger = LogManager.getLogger(Sequence.class);


    private ArrayList<Job> jobs;
//    private final Queue<Job> originalJobs;
//    private Job currentJob;

    /**
     * Initialise the Sequence Job.
     *
     * @param jobs The Queue of jobs to be executed
     */
    public Sequence(ArrayList<Job> jobs) {
        this.jobs = jobs;
//        this.originalJobs = new LinkedList<>(jobs);
//        if (jobs.isEmpty()) {
//            succeed();
//            return;
//        }
//        this.currentJob = jobs.poll();
//        currentJob.start();
    }

    @Override
    public void reset() {
//        this.jobs = new LinkedList<>(originalJobs);
//        this.currentJob = jobs.poll();
//
//        currentJob.reset();
//        currentJob.start();

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        logger.debug("Starting Sequence Job");
        for (Job currentJob: jobs) {
            currentJob.start();

            if (currentJob.isRunning()) {
                currentJob.act(ai,players,balls,platform);
            } else if (jobs.isEmpty()) {
                succeed();
                logger.debug("Finishing Sequence Job with success");
                return;
            } else if (currentJob.isSuccess() | currentJob.isFailure()) {
                logger.debug("Sequence getting next job");
//                currentJob = jobs.poll();
//                currentJob.start();
            }
        }

        succeed();
        logger.debug("Finishing SequenceSuccess Job with success");
        return;





    }

    @Override
    public void start() {
        super.start();
//        this.currentJob.start();
    }
}
