package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Repeats the Job infinite amount of times or the number that is specified. Until Fails
 * @author Roman
 */
public class RepeatSuccess extends Job {

    private static Logger logger = LogManager.getLogger(RepeatSuccess.class);

    private final Job job;
    private int times;
    private int originalTimes;


    /**
     * Initialise the RepeatSuccess Class.
     *
     * @param job The job to be repeated infinite amount of times
     */
    public RepeatSuccess(Job job) {
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
    public RepeatSuccess(Job job, int times) {
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
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        logger.debug("Starting RepeatSuccess Job");

        if (job.isRunning()) {
            job.act(ai, players, balls, platform, session);
        }

        if (job.isFailure()) {
            fail();
            logger.debug("Finishing RepeatSuccess Job with fail");
        } else if (job.isSuccess()) {
            if (times == 1) {
                succeed();
                logger.debug("Finishing RepeatSuccess Job with success");
                return;
            } else {
                times--;
                job.reset();
                job.start();
            }
        }



    }

    @Override
    public void start() {
        super.start();
        this.job.start();
    }


}
