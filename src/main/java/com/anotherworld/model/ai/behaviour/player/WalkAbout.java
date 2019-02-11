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

public class WalkAbout extends Job {


    private static Logger logger = LogManager.getLogger(WalkAbout.class);


    private Matrix destination;


    public WalkAbout() {
        super();
    }

    @Override
    public void start() {
        super.start();
    }
    @Override
    public void reset() {
        start();
        setXY();
    }

    private void setXY(){
        float xcoordinate = getRandom(platform.getXCoordinate() - platform.getXSize() + ai.getRadius(),
                platform.getXCoordinate() + platform.getXSize() - ai.getRadius());

        float ycoordinate = getRandom(platform.getYCoordinate() - platform.getYSize() + ai.getRadius(),
                platform.getYCoordinate() + platform.getYSize() - ai.getRadius());
        destination = new Matrix(xcoordinate, ycoordinate);

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;


        logger.debug("Starting WalkAbout Job");

        if (getState() == null){
            setXY();
        }

        if (isRunning() & ai.getHealth() == 0) {
            fail();
            logger.debug("Finishing WalkAbout Job with fail");
            return;
        }

        if (isNear() & isRunning()) {
            logger.debug("Finished WalkAbout with success");
            succeed();
            return;
        } else if (isRunning()){

        }
    }

    private void move() {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);
        //ai.setAngle(MatrixMath.vectorAngle(MatrixMath.flipMatrix(vector)));
        //temp
        ai.setXVelocity(vector.getX());
        ai.setYVelocity(vector.getY());
        logger.debug("Moving to " + destination);
    }
    private boolean isNear() {
        boolean x = ai.getXCoordinate() <= (ai.getRadius() + destination.getX()) & ai.getXCoordinate() >= (destination.getX() - ai.getRadius());
        boolean y = ai.getYCoordinate() <= (ai.getRadius() + destination.getY()) & ai.getYCoordinate() >= (destination.getY() - ai.getRadius());

        return y & x;
    }

    private float getRandom(float min, float max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }
}
