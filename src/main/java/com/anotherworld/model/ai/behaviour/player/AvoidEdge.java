package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;

/**
 *Makes sure that the AI doesn't come too close to the edge
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
        //##############################################
        //TODO change it
        Matrix platformCoordinates = new Matrix(platform.getXCoordinate(),platform.getYCoordinate()); // Change it
        // #############################################
        Random random = new Random();

        Matrix place = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());

        if (Math.abs(place.getX()) >= platform.getXSize() - distanceFromEdge) {
            // too close to x
            // go Left Or Right
//            random.nextBoolean()?;
            if ((place.getX() < platformCoordinates.getX() & isPointing(ai.getVelocity(),270)) | (place.getX() > platformCoordinates.getX() & isPointing(ai.getVelocity(),90))) {
                ai.setXVelocity(0);
                ai.setYVelocity(random.nextBoolean()? 1: -1);
                fail();
                logger.debug("Near the Edge");
                return;
            }



        } else if (Math.abs(place.getY()) >= platform.getYSize() - distanceFromEdge) {
            // too close to y
            // go Up or Down
            if ((place.getY() < platformCoordinates.getY() & isPointing(ai.getVelocity(),0)) | (place.getY() > platformCoordinates.getY() & isPointing(ai.getVelocity(),180))) {
                ai.setYVelocity(0);
                ai.setXVelocity(random.nextBoolean() ? 1 : -1);
                fail();
                logger.debug("Near the Edge");
                return;
            }
        } else {
            ai.setVelocity(0,0);
        }
        logger.debug("Finishing AvoidEdge with success");
        succeed();

    }

    private boolean isPointing(Matrix directions, int angle) {
        float aiAngle = MatrixMath.vectorAngle(directions);

        if (((angle - 90) % 360) > aiAngle | ((angle + 90) % 360) < aiAngle ) {
            return true;
        }
        return false;
    }

}
