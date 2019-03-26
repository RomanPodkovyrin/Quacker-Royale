package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Job class which makes the player chase the neutral ball.
 * @author roman
 */
public class ChaseBall extends Job {

    private static Logger logger = LogManager.getLogger(ChaseBall.class);

    public  ChaseBall() {
        super();
    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        logger.debug("Starting ChaseBall Job");

        if (ai.isTimeStopper()) {
            logger.debug("Has time stop don't chase the ball");
            fail();
            return;
        }

        //Todo by chasing the closes ball it is hard to get it on time
        BlackBoard.sortBalls(ai,balls);

        for (BallData ball: balls) {

            // Checks if the ball is currently not dangerous and on the platform
            if (!ball.isDangerous() & isRunning() & platform.isOnPlatform(ball.getCoordinates())) {
                logger.debug("Chasing the Ball at " + ball.getCoordinates());

                Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ball.getCoordinates(),ball.getVelocity()),ai.getCoordinates());
                Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), neighbour);

                // Checks if it is already near the ball
                if (MatrixMath.distanceAB(ai.getCoordinates(),neighbour) <= ball.getRadius() + ai.getRadius()) {
                    fail();
                    logger.trace("Touched the ball");
                    return;
                }

                // Check if ball is already targeted by someone else
                if (!BlackBoard.targetBall(ai, ball)) {
                    logger.trace("Ball " + ball.getObjectID() + " is already targeted by someone");
                    continue;
                }

                // Walk to that ball
                if (vector.getX() != 0) {
                    ai.setXVelocity(Maths.floatDivision(vector.getX(), Math.abs(vector.getX())));
                }
                if (vector.getY() != 0) {
                    ai.setYVelocity(Maths.floatDivision(vector.getY(), Math.abs(vector.getY())));
                }
                logger.trace("Chasing the ball");
                succeed();
                return;
            } else {
                logger.debug("Finishing ChaseBall with fail: nothing to chase");
                BlackBoard.stopTargetingBall(ai);
                fail();
                return;
            }
        }

        logger.trace("can't target any balls");
        fail();
        return;
    }

}
