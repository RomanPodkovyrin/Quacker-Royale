package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.tools.maths.MatrixMath;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Tells ai in which direction to dash and when do dash.
 * fail - pointing or dashing
 * true - not charging
 * @author roman
 */
public class PointAndDash extends Job  {
    private static Logger logger = LogManager.getLogger(PointAndDash.class);

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        if (ai.getState().equals(ObjectState.CHARGING)) {
            ai.setTimeStartedCharging(session.getTicksElapsed());
            ai.setState(ObjectState.CHARGING);
            if ((session.getTicksElapsed() - ai.getTimeStartedCharging()) % 20 == 0) {
                Player.incrementChargeLevel(ai);
            }

            ArrayList<PlayerData> targetPlayers = BlackBoard.sortTargetPlayers(ai,players);
            if (targetPlayers.isEmpty()) {
                logger.trace("No players to target, Stopping");
                ai.setState(ObjectState.IDLE);
                ai.setSpeed(GameSettings.getDefaultPlayerSpeed());
                ai.setVelocity(0,0);
                succeed();
                return;
            }

            ai.setVelocity(0,0);
            if (ai.getChargeLevel() >= GameSettings.getDefaultPlayerMaxCharge() * 1) {
                logger.trace("Reached full charge");
                GameSession.updatePlayer(ai,new ArrayList<>(),session);
                ai.setTimeStartedCharging(session.getTicksElapsed());
                ai.setState(ObjectState.DASHING);


                // Points it in the direction of the closest player
                PlayerData closestPlayer = targetPlayers.get(0);
                Matrix point = MatrixMath.pointsVector(ai.getCoordinates(),closestPlayer.getCoordinates()).normalize();
                ai.setVelocity(point.getX(),point.getY());

            }
            fail();
            return;


        }
        succeed();



    }
}
