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
 * Repeats the Job infinite amount of times or the number that is specified. Does not care if fails or succeeds.
 * @author Roman
 */
public class Repeat extends Job {

    private static Logger logger = LogManager.getLogger(Repeat.class);

    private final Job job;
    private int times;
    private int originalTimes;


    /**
     * Initialise the RepeatSuccess Class.
     *
     * @param job The job to be repeated infinite amount of times
     */
    public Repeat(Job job) {
        super();
        this.job = job;
        this.times = -1; // infinite
        this.originalTimes = times;
    }

    /**
     * Initialise the RepeatSuccess Class.
     *
     * @param job The job to be repeated
     * @param times The number of time to repeat the job
     */
    public Repeat(Job job, int times) {
        super();
        if (times < 1) {
            throw new RuntimeException("Times needs to be positive.");
        }
        this.job = job;
        this.times = times;
        this.originalTimes = times;
    }

    @Override
    public void reset() {
        this.times = originalTimes;

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {

        logger.trace("Starting Repeat Job");

        if (job.isRunning()) {
            job.act(ai, players, balls, platform, session);
        }

        if (job.isFailure() | job.isSuccess()) {
            if (times == 1) {
                logger.debug("Finishing Repeat Job with success");
                succeed();
                return;
            } else {
                times--;
                job.reset();
                job.start();
            }
        }
        logger.debug("Repeat the job again");



    }

    @Override
    public void start() {
        super.start();
        this.job.start();
    }


}
