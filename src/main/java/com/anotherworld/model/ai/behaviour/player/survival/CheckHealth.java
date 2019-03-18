package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Checks health level of ai. Fails if low health, succeeds otherwise.
 * @author roman
 */
public class CheckHealth extends Job {
    private static Logger logger = LogManager.getLogger(CheckHealth.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        logger.trace("Checking health level");
        float healthPercentage = 0.5f;
        float aiHealth = (float)ai.getHealth();
        int aiMaxHealth = ai.getMaxHealth();
        float  optimalHealth = aiMaxHealth * healthPercentage;
        if (aiHealth <= optimalHealth) {
            logger.info("Low health: " + aiHealth + " / " + aiMaxHealth);
            fail();
            return;
        }
        logger.trace("Good health level");
        succeed();

    }
}
