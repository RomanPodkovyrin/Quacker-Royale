package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Inverts fail into success and vice versa.
 *
 * @author roman
 */
public class Inverter extends Job {

    private static Logger logger = LogManager.getLogger(Inverter.class);
    private Job job;

    public Inverter(Job job) {
        this.job = job;
    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        logger.trace("Starting the Inverter Job");
        job.start();
        job.act(ai,players,balls,platform,session);

        if (job.isFailure()) {
            succeed();
            logger.trace("Job returned Fail inverting to Success");
            return;
        } else if (job.isSuccess()) {
            fail();
            logger.trace("Job returned Success inverting to Fail");
            return;
        } else {
            logger.trace("Job is still running, leaving state as running");
            return;
        }


    }
}
