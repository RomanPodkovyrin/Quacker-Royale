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
        reset();
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        float distanceFromEdge = ai.getRadius();


        // get x y Matrix of the Platform
        //##############################################
        Matrix platformCoordinates = new Matrix(platform.getDistanceX(),platform.getDistanceY()); // Change it
        // #############################################
        Random random = new Random();

        Matrix place = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());

        if (Math.abs(place.getX()) >= platform.getDistanceX() - distanceFromEdge) {
            // too close to x
            // go Left Or Right
//            random.nextBoolean()?;
            if ((place.getX() < 0 & isPointing(ai.getCoordinates(),0)) | (place.getX() > 0 & isPointing(ai.getCoordinates(),180))) {
                ai.setYVelocity(0);
                ai.setXVelocity(random.nextBoolean()? 1: -1);
                succeed();
                return;
            }



        }

        if (Math.abs(place.getY()) >= platform.getDistanceY() - distanceFromEdge) {
            // too close to y
            // go Up or Down
            if ((place.getY() < 0 & isPointing(ai.getCoordinates(),270)) | (place.getY() > 0 & isPointing(ai.getCoordinates(),90))) {
                ai.setXVelocity(0);
                ai.setYVelocity(random.nextBoolean() ? 1 : -1);
                succeed();
                return;
            }
        }

        fail();

    }

    private boolean isPointing(Matrix directions, int angle) {
        float aiAngle = MatrixMath.vectorAngle(directions);

        if (((angle - 90) % 360) > aiAngle | ((angle + 90) % 360) < aiAngle ) {
            return true;
        }
        return false;
    }

}
