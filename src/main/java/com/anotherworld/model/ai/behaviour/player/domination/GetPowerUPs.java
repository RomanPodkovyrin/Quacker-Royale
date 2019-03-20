package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.AITools;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Gets either time stop or shield power up.
 * @author roman
 */
public class GetPowerUPs extends Job {

    private static Logger logger = LogManager.getLogger(GetPowerUPs.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        try {
            PowerUpData powerUP = session.getCurrentPowerUp();
            PowerUpType type = powerUP.getPowerUpType();
            if (type.equals(PowerUpType.TIME_STOP) || type.equals(PowerUpType.SHIELD)) {
                logger.trace("Getting " + type +" power up");
                Matrix destination = powerUP.getCoordinates();
                AITools.moveTo(ai,destination);
                succeed();
                return;
            }
        } catch (NullPointerException e) {
            logger.trace("No power up is present");
            fail();
            return;
        }
    }
}
