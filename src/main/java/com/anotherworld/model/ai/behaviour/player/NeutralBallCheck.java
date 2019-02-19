package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class NeutralBallCheck extends Job {
    private static Logger logger = LogManager.getLogger(NeutralBallCheck.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        for (Ball ball:balls) {
            if (!ball.isDangerous()) {
                succeed();
                logger.info("found Neutral Ball");
                return;
            }
        }
        logger.info("No neutral Balls");
        fail();

    }
}
