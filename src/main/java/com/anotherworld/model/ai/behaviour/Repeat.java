package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Repeats the Job infinite amount of times or the number that is specified.
 * @author Roman
 */
public class Repeat extends Job {

    private static Logger logger = LogManager.getLogger(Repeat.class);

    private final Job job;
    private int times;
    private int originalTimes;


    /**
     * Initialise the Repeat Class.
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
     * Initialise the Repeat Class.
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {

        logger.debug("Starting Repeat Job");

        if (job.isFailure()) {
            fail();
            logger.debug("Finishing Repeat Job with fail");
        } else if (job.isSuccess()) {
            if (times == 0) {
                succeed();
                logger.debug("Finishing Repeat Job with success");
                return;
            } else {
                times--;
                job.reset();
                job.start();
            }
        }
        if (job.isRunning()) {
            job.act(ai, players, balls, platform);
        }

    }

    @Override
    public void start() {
        super.start();
        this.job.start();
    }


}
