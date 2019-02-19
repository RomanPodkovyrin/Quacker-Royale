package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.anotherworld.tools.maths.Maths.getRandom;

/**
 * Sets random coordinates for the ai to walk to.
 * success - reached the coordinate
 * fail - still walking to the coordinate
 *
 * @author roman
 */
public class WalkAbout extends Job {


    private static Logger logger = LogManager.getLogger(WalkAbout.class);


    private Matrix destination;
    private boolean newDestination;


    public WalkAbout() {
        super();
        newDestination = true;
    }

    @Override
    public void reset() {
        start();
        setRandomCoordinates();
        newDestination = false;
    }

    /**
     * Sets a random coordinates for the ai to walk to.
     */
    private void setRandomCoordinates() {
        float xcoordinate = getRandom(platform.getXCoordinate() - platform.getXSize() + ai.getRadius(),
                platform.getXCoordinate() + platform.getXSize() - ai.getRadius());

        float ycoordinate = getRandom(platform.getYCoordinate() - platform.getYSize() + ai.getRadius(),
                platform.getYCoordinate() + platform.getYSize() - ai.getRadius());
        destination = new Matrix(xcoordinate, ycoordinate);
        logger.trace("Set Random coordinates to: " + destination);
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;


        logger.trace("Starting WalkAbout Job");

        if (newDestination) {
            logger.trace("Need a new random coordinates");
            setRandomCoordinates();
            newDestination = false;
        }


        if (isNear() & isRunning()) {
            logger.debug("Finished WalkAbout with success");
            succeed();
            newDestination = true;
            return;
        } else if (isRunning()) {
            move();
            fail();
            logger.info("Still walking to the coordinate");
        }
    }

    /**
     * Moves ai in the direction of the set coordinates.
     */
    private void move() {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);
//        ai.setXVelocity(vector.getX() / Math.abs(vector.getX()) * ai.getSpeed());
//        ai.setYVelocity(vector.getY() / Math.abs(vector.getY()) * ai.getSpeed());
        vector.normalizeThis();
        ai.setXVelocity(vector.getX() * ai.getSpeed());
        ai.setYVelocity(vector.getY() * ai.getSpeed());
        logger.info("Walking about: Moving to " + destination);
    }

    /**
     * tells whether ai is near the random coordinates.
     *
     * @return true - near the coordinate, false - not close
     */
    private boolean isNear() {
        boolean x = ai.getXCoordinate() <= (ai.getRadius() + destination.getX()) & ai.getXCoordinate() >= (destination.getX() - ai.getRadius());
        boolean y = ai.getYCoordinate() <= (ai.getRadius() + destination.getY()) & ai.getYCoordinate() >= (destination.getY() - ai.getRadius());

        return y & x;
    }
}
