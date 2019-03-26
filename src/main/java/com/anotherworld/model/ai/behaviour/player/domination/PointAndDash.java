package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tells ai in which direction to dash and when do dash.
 * fail - pointing or dashing
 * true - not charging
 */
public class PointAndDash extends Job  {
    private static Logger logger = LogManager.getLogger(PointAndDash.class);
    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        if (ai.getState().equals(ObjectState.CHARGING)) {
            GameSession.updatePlayer(ai,new ArrayList<Input>(Arrays.asList(Input.CHARGE)),session);

            ArrayList<PlayerData> targetPlayers = BlackBoard.sortTargetPlayers(ai,players);
            if (targetPlayers.isEmpty()) {
                //TODO should i make it dash anyway towards the center?
                logger.trace("No players to target, Stopping");
                ai.setState(ObjectState.IDLE);
                ai.setSpeed(GameSettings.getDefaultPlayerSpeed());
//                GameSession.updatePlayer(ai,new ArrayList<Input>(),session);
                ai.setVelocity(0,0);
                System.out.println("No players to target");
                succeed();
                return;
            }

            ai.setVelocity(0,0);
            System.out.println("Ai charge " + ai.getChargeLevel() + " max " + GameSettings.getDefaultPlayerMaxCharge()) ;
            if (ai.getChargeLevel() >= GameSettings.getDefaultPlayerMaxCharge() * 0.5) {
                logger.trace("Reached full charge");
                System.out.println("Dashing");
                ai.setTimeStartedCharging(session.getTicksElapsed());
                ai.setState(ObjectState.DASHING);


                PlayerData closestPlayer = targetPlayers.get(0);

                BlackBoard.moveTo(ai,closestPlayer.getCoordinates());
                System.out.println(ai.getVelocity());
//                GameSession.updatePlayer(ai,new ArrayList<Input>(),session);

            }
            fail();
            return;


        }
        System.out.println("Not charging");
        succeed();



    }
}
