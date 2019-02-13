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

import static com.anotherworld.tools.maths.Maths.getRandom;

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
        setXY();
        newDestination = false;
    }

    private void setXY(){
        float xcoordinate = getRandom(platform.getXCoordinate() - platform.getXSize() + ai.getRadius(),
                platform.getXCoordinate() + platform.getXSize() - ai.getRadius());

        float ycoordinate = getRandom(platform.getYCoordinate() - platform.getYSize() + ai.getRadius(),
                platform.getYCoordinate() + platform.getYSize() - ai.getRadius());
        destination = new Matrix(xcoordinate, ycoordinate);
        logger.debug("Set x y");
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;


        logger.debug("Starting WalkAbout Job");

        if (newDestination){
            setXY();
            newDestination = false;
        }

        if (isRunning() & ai.getHealth() == 0) {
            fail();
            logger.debug("Finishing WalkAbout Job with fail");
            return;
        }

        if (isNear() & isRunning()) {
            logger.debug("Finished WalkAbout with success");
            succeed();
            newDestination = true;
            return;
        } else if (isRunning()){
            move();
            fail();
            logger.debug("Still walking");
        }
    }

    private void move() {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);
        //ai.setAngle(MatrixMath.vectorAngle(MatrixMath.flipMatrix(vector)));
        //temp
        ai.setXVelocity(vector.getX() / Math.abs(vector.getX()) * ai.getSpeed());
        ai.setYVelocity(vector.getY() / Math.abs(vector.getY()) * ai.getSpeed());
        logger.debug("Moving to " + destination);
    }
    private boolean isNear() {
        boolean x = ai.getXCoordinate() <= (ai.getRadius() + destination.getX()) & ai.getXCoordinate() >= (destination.getX() - ai.getRadius());
        boolean y = ai.getYCoordinate() <= (ai.getRadius() + destination.getY()) & ai.getYCoordinate() >= (destination.getY() - ai.getRadius());

        return y & x;
    }
}
