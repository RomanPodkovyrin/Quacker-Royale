package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {

        job.start();
        job.act(ai,players,balls,platform);

        logger.trace("Succeeder Succeeded");
        succeed();

    }
}
