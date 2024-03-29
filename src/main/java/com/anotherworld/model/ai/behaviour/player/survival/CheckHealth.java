package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;
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
        float aiHealth = (float)ai.getHealth();
        int aiMaxHealth = ai.getMaxHealth();
        float  optimalHealth = aiMaxHealth * BlackBoard.getAcceptableHealthPercentage();
        if (aiHealth <= optimalHealth) {
            logger.trace("Low health: " + aiHealth + " / " + aiMaxHealth);
            fail();
            return;
        }
        logger.trace("Good health level");
        succeed();

    }
}
