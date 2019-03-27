package com.anotherworld.model.ai.behaviour.player.peace;

import static com.anotherworld.tools.maths.Maths.getRandom;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



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
        float xcoordinate = getRandom(platform.getXCoordinate() - platform.getXSize() + ai.getRadius() * 2,
                platform.getXCoordinate() + platform.getXSize() - ai.getRadius() * 2);

        float ycoordinate = getRandom(platform.getYCoordinate() - platform.getYSize() + ai.getRadius() * 2,
                platform.getYCoordinate() + platform.getYSize() - ai.getRadius() * 2);
        destination = new Matrix(xcoordinate, ycoordinate);
        logger.trace("Set Random coordinates to: " + destination);
    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        setData(ai,players,balls,platform,session);

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
            logger.trace("Still walking to the coordinate");
        }
    }

    /**
     * Moves ai in the direction of the set coordinates.
     */
    private void move() {
        BlackBoard.moveTo(ai,destination);
        logger.trace("Walking about: Moving to " + destination);

        if (!platform.isOnPlatform(destination)) {
            logger.trace("destination no longer on the platform");
            succeed();
            newDestination = true;
        }
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
