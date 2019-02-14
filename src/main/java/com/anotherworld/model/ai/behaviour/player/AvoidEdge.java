package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *Makes sure that the AI doesn't come too close to the edge.
 * success - no edge to avoid
 * fail - near the edge
 * @author Roman
 */
public class AvoidEdge extends Job {
    private static Logger logger = LogManager.getLogger(AvoidEdge.class);


    public AvoidEdge() {
        super();
    }

    @Override
    public void reset() {
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        float distanceFromEdge = ai.getRadius();
        logger.debug("Running AvoidEdge");

        // get x y Matrix of the Platform
        //TODO change it
        Matrix platformCoordinates = new Matrix(platform.getXCoordinate(),platform.getYCoordinate());

        Matrix place = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());

        // Checks if the AI is near the horizontal edge
        if (Math.abs(place.getX()) >= platform.getXSize() - distanceFromEdge) {
            logger.trace("AI too close to the x edge");
            ai.setYVelocity(-ai.getYVelocity());
            ai.setXVelocity(-ai.getXVelocity());
            fail();
            logger.info("Moving to in direction " + ai.getVelocity());


        // Checks if the AI is near the vertical edge
        } else if (Math.abs(place.getY()) >= platform.getYSize() - distanceFromEdge) {
            logger.trace("AI too close to the y edge");

            ai.setYVelocity(-ai.getYVelocity());
            ai.setXVelocity(-ai.getXVelocity());
            fail();
            logger.info("Moving to in direction" + ai.getVelocity());
        }
        logger.trace("Finishing AvoidEdge with success: no edges to avoid");
        succeed();


    }

    @Deprecated
    private boolean isPointing(Matrix directions, int angle) {
        float aiAngle = MatrixMath.vectorAngle(directions);

        if (((angle - 90) % 360) > aiAngle | ((angle + 90) % 360) < aiAngle) {
            return true;
        }
        return false;
    }

}
