package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.tools.maths.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *Makes sure that the ControllerAI doesn't come too close to the edge.
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
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {

        float distanceFromEdge = ai.getRadius()*2;
        logger.debug("Running AvoidEdge");

        // get x y Matrix of the Platform
        Matrix platformCoordinates = new Matrix(platform.getXCoordinate(),platform.getYCoordinate());

        // Generates the vector matrix from players location to the center of the platform
        Matrix vectorFromPlatformCenter = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());
        Matrix toCenter = MatrixMath.pointsVector(ai.getCoordinates(),platformCoordinates);
        toCenter = new Matrix(Maths.floatDivision(toCenter.getX(), Math.abs(toCenter.getX())),
                Maths.floatDivision(toCenter.getY(), Math.abs(toCenter.getY())));

        // Checks if the AI is near the horizontal edge
        if (Math.abs(vectorFromPlatformCenter.getX()) >= platform.getXSize() - distanceFromEdge) {
            logger.trace("ControllerAI too close to the x edge " + distanceFromEdge + " " + (platform.getXSize() - distanceFromEdge));

            ai.setVelocity(toCenter.getX(),toCenter.getY());
            fail();
            logger.trace("Moving to in direction " + ai.getVelocity());
            return;

        // Checks if the AI is near the vertical edge
        } else if (Math.abs(vectorFromPlatformCenter.getY()) >= platform.getYSize() - distanceFromEdge) {
            logger.trace("ControllerAI too close to the y edge " + vectorFromPlatformCenter + " " + (platform.getYSize() - distanceFromEdge));
            ai.setVelocity(toCenter.getX(),toCenter.getY());
            fail();
            logger.trace("Moving to in direction" + ai.getVelocity());
            return;
        }


        logger.trace("Finishing AvoidEdge with success: no edges to avoid");
        succeed();

    }

}
