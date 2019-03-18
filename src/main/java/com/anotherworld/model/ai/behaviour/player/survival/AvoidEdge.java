package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.maths.Maths;
import java.util.ArrayList;
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
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {

        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;
        float distanceFromEdge = ai.getRadius();
        logger.debug("Running AvoidEdge");

        // get x y Matrix of the Platform
        Matrix platformCoordinates = new Matrix(platform.getXCoordinate(),platform.getYCoordinate());

        // Generates the vector matrix from players location to teh center of the platform
        Matrix vectorFromPlatformCenter = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());
        Matrix toCenter = MatrixMath.pointsVector(ai.getCoordinates(),platformCoordinates);
        toCenter = new Matrix(Maths.floatDivision(toCenter.getX(), Math.abs(toCenter.getX())) * ai.getSpeed(),
                Maths.floatDivision(toCenter.getY(), Math.abs(toCenter.getY())) * ai.getSpeed());

        // Checks if the AI is near the horizontal edge
        if (Math.abs(vectorFromPlatformCenter.getX()) >= platform.getXSize() - distanceFromEdge) {
            logger.trace("AI too close to the x edge " + distanceFromEdge + " " + (platform.getXSize() - distanceFromEdge));

            ai.setVelocity(toCenter.getX(),toCenter.getY());
            fail();
            logger.trace("Moving to in direction " + ai.getVelocity());
            return;

        // Checks if the AI is near the vertical edge
        } else if (Math.abs(vectorFromPlatformCenter.getY()) >= platform.getYSize() - distanceFromEdge) {
            logger.trace("AI too close to the y edge " + vectorFromPlatformCenter + " " + (platform.getYSize() - distanceFromEdge));
            ai.setVelocity(toCenter.getX(),toCenter.getY());
            fail();
            logger.trace("Moving to in direction" + ai.getVelocity());
            return;
        }


        logger.trace("Finishing AvoidEdge with success: no edges to avoid");
        succeed();

    }

}
