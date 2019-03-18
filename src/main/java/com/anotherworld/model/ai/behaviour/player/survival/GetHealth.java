package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.AITools;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Looks if health power up. And directs AI towards it. Fail if no power up, Succeed if health power up is present.
 * @author roman
 */
public class GetHealth extends Job {

    private static Logger logger = LogManager.getLogger(GetHealth.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        try {
            PowerUpData powerUP = session.getCurrentPowerUp().get();
            if (powerUP.getPowerUpType().equals(PowerUpType.HEAL)) {
                Matrix destination = powerUP.getCoordinates();
                logger.trace("Getting Heal power up from: " + destination);
                AITools.moveTo(ai,destination);
                succeed();
                return;
            }
        } catch (NoSuchElementException e) {
            logger.trace("No power up is present");
            fail();
            return;
        }

    }
}
