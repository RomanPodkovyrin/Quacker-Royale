package com.anotherworld.model.ai.behaviour.player.survival;

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
 * Looks if health power up. And directs ControllerAI towards it. Fail if no power up, Succeed if health power up is present.
 * @author roman
 */
public class GetHealth extends Job {

    private static Logger logger = LogManager.getLogger(GetHealth.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {


        PowerUpData powerUP = session.getCurrentPowerUp();
        if (powerUP.getState().equals(ObjectState.ACTIVE)) {


            if (powerUP.getPowerUpType().equals(PowerUpType.HEAL)) {
                Matrix destination = powerUP.getCoordinates();
                logger.trace("Getting Heal power up from: " + destination);
                BlackBoard.moveTo(ai,destination);
                succeed();
                return;
            }

        }
        logger.trace("No power up is present");
        fail();
        return;


    }
}
