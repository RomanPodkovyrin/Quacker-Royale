package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Succeeds no matter what.
 *
 * @author roman
 */
public class Succeeder extends Job {

    private static Logger logger = LogManager.getLogger(Succeeder.class);

    private Job job;

    public Succeeder(Job job) {
        this.job = job;
    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        job.start();
        job.act(ai,players,balls,platform,session);

        logger.trace("Succeeder Succeeded");
        succeed();

    }
}
