package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.Random;

/**
 *Makes sure that the AI doesn't come too close to the edge
 * @author Roman
 */
public class avoidEdge extends Job {

    public avoidEdge() {
        super();
    }

    @Override
    public void reset() {
        reset();
    }

    @Override
    public void act(Player ai, Player[] players, Ball[] balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;


        // get x y Matrix of the Platform
        //##############################################
        Matrix platformCoordinates = new Matrix(0,0); // Change it
        // #############################################
        Random random = new Random();

        Matrix place = MatrixMath.pointsVector(platformCoordinates,ai.getCoordinates());
        if (Math.abs(place.getX()) >= platform.getXSize() + 10) {
            // too close to x
            // go Left Or Right
//            random.nextBoolean()?;
            ai.setYVelocity(0);
            ai.setXVelocity(random.nextBoolean()? 10: -10);
            succeed();
            return;
        }

        if (Math.abs(place.getY()) >= platform.getYSize() + 10 ) {
            // too close to y
            // go Up or Down
            ai.setXVelocity(0);
            ai.setYVelocity(random.nextBoolean()? 10: -10);
            succeed();
            return;
        }
        fail();

    }

}
