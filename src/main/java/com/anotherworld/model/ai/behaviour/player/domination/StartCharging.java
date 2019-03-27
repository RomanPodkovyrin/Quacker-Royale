package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;
import com.anotherworld.tools.maths.MatrixMath;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Tells ai when to start charging.
 * fail - can start charging
 * succeed - do not charge
 */
public class StartCharging extends Job {

    private static Logger logger = LogManager.getLogger(StartCharging.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        if (ai.getState().equals(ObjectState.CHARGING)) {
            logger.trace("Already charging");
            succeed();
            return;
        }
        if (ai.getState().equals(ObjectState.DASHING)) {
            logger.trace("Already dashing");
            succeed();
            return;
        }


        if (ai.getHealth() < ai.getMaxHealth() * 0.2) {
            logger.trace("Low on health do not risk charging");
            succeed();
            return;
        }

        ArrayList<PlayerData> closePlayers = BlackBoard.sortTargetPlayers(ai, players);

        if (closePlayers.isEmpty()) {
            logger.trace("No players left alive, no one to charge at");
            succeed();
            return;
        }

        PlayerData closestPlayer = closePlayers.get(0);
        if (MatrixMath.distanceAB(closestPlayer.getCoordinates(),ai.getCoordinates()) > ai.getRadius() * 10) {
            logger.trace("Players are too far away");
            succeed();
            return;
        }

        if (Maths.getRandom(0,150) != 1) {
            succeed();
            return;
        }



        // Can start charging
        System.out.println("Start charging");
        ai.setTimeStartedCharging(session.getTicksElapsed());
        ai.setState(ObjectState.CHARGING);

    }
}
