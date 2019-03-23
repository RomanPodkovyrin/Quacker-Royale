package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class makes AI go around the player if one of them is abstraction the way.
 * @author roman
 */
public class AvoidNeutralPlayer extends Job {

    private static Logger logger = LogManager.getLogger(AvoidNeutralPlayer.class);

    public AvoidNeutralPlayer(){

    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        for (PlayerData player: players) {

            if (!Player.isDead(player)) {

                // Checks the distance between the player and the ai
                // TODO magic number
                if (MatrixMath.distanceAB(player.getCoordinates(), ai.getCoordinates()) <= player.getRadius() + ai.getRadius() + 0.5) {
                    //TODO do i need it, it makes ai look silly
//                    fail();
//                    logger.debug("Avoiding player " + player.getObjectID());
//                    Matrix vector = MatrixMath.pointsVector(player.getCoordinates(),ai.getCoordinates());
//                    Line line = new Line(player.getCoordinates(), vector);
//                    Matrix orthogonal = line.getOrthogonalVector();
//
//                    // Tells ai to move orthogonaly
//                    BlackBoard.moveIn(ai,orthogonal);
//                    return;
                }
            }
        }

        succeed();
        logger.trace("No player to avoid");

    }
}
