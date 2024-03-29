package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.tools.enums.PowerUpType;
import com.anotherworld.tools.maths.Matrix;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



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


        PowerUpData powerUP = session.getCurrentPowerUp();
        PowerUpType type = powerUP.getPowerUpType();
        if (powerUP.getState().equals(ObjectState.ACTIVE)) {
            if (type.equals(PowerUpType.TIME_STOP) || type.equals(PowerUpType.SHIELD)) {
                logger.trace("Getting " + type + " power up");
                Matrix destination = powerUP.getCoordinates();
                BlackBoard.moveTo(ai, destination);
                succeed();
                return;
            }
        }
        logger.trace("No power up is present");
        fail();
        return;

    }
}
