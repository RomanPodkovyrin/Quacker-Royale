package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Checks if Currently have shield or time power up.
 * @author roman
 */
public class CheckShieldandTimePowerUP extends Job {

    private static Logger logger = LogManager.getLogger(CheckShieldandTimePowerUP.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        //TODO need a way of checking what power up the player has
        if (ai.isTimeStopper() || ai.isShielded()) {
            logger.trace("Ai is shielded or has a time stop");
            succeed();
//            BlackBoard.setState(ai.getObjectID(), BlackBoard.AI_State.INVULNERABLE);
            return;
        }
//        BlackBoard.setState(ai.getObjectID(), BlackBoard.AI_State.NORMAL);
        fail();

    }
}
