package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * If the player is charging it stops them from doing.
 * Fail - need to stop charging
 * true - not charging
 */
public class StopCharging extends Job {
    private static Logger logger = LogManager.getLogger(StopCharging.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        // Check is ai is charging
        if (ai.getState().equals(ObjectState.CHARGING)) {
            // Stop charging
            ai.setVelocity(0,0);
            ai.setState(ObjectState.IDLE);
            ai.setSpeed(GameSettings.getDefaultPlayerSpeed());

            fail();
            return;
        }

        succeed();
        return;
    }

}
