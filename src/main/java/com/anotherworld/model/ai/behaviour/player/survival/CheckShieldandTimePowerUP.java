package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.GameSessionData;
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {
        //TODO need a way of checking what power up the player has
        fail();

    }
}
