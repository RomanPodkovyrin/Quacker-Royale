package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Checks if there is a neutral ball on the map.
 *
 * @author roman
 */
public class NeutralBallCheck extends Job {
    private static Logger logger = LogManager.getLogger(NeutralBallCheck.class);

    public NeutralBallCheck(){

    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {
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
