package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Makes sure that the AI player stays away from the Dangerous balls.
 * #################################################################
 * STILL WORKING ON THIS CLASS
 * #################################################################
 * @author Roman
 */
public class AvoidBall extends Job {

    private static Logger logger = LogManager.getLogger(AvoidBall.class);

    private ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;
    private Matrix aiDirection;

    /**
     * Initialises the Job.
     */
    public AvoidBall() {
        super();
    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;

        aiDirection = ai.getVelocity();
        aiPosition = ai.getCoordinates();
        //sorting the balls based on distance
        sortObject(this.balls);

        //System.out.println("direction " + aiDirection + " position" + aiPosition + "Angle: " + ai.getAngle());
        if (isRunning() & ai.getHealth() == 0) {
            fail();
            return;
        }

        if (!isAIsafe()) {
            moveAway();
        } else {
            succeed();
            return;
        }
    }

    /**
     * Points the AI players in the opposite direction of the dangerous ball.
     */
    private void moveAway() {
        //######################################################
        // loads the first value which is the close balls
        Matrix ballPosition;
        Matrix ballDirection ;

        if (!imminentDangerBalls.isEmpty()) {
            ballPosition = imminentDangerBalls.get(0).getCoordinates();
            ballDirection = imminentDangerBalls.get(0).getVelocity();
        } else if (!dangerBalls.isEmpty()) {
            ballPosition  = dangerBalls.get(0).getCoordinates();
            ballDirection = dangerBalls.get(0).getVelocity();
        } else {
            //save
            succeed();
            return;
        }

        Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ballPosition, ballDirection),aiPosition);
        Matrix vector = MatrixMath.pointsVector(aiPosition, neighbour);
        System.out.println(vector);
        ai.setAngle(MatrixMath.vectorAngle(MatrixMath.flipMatrix(vector)));
        //temp
        ai.setXVelocity(-vector.getX());
        ai.setYVelocity(-vector.getY());
        System.out.println(vector.getY());
    }

    /**
     * Sorts balls based on their distance from the AI player.
     *
     * @param objects The object to be sorted based on the distance from the AI
     * @return returns an ArrayList of Balls starting with the closes one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),aiPosition))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),aiPosition)));
        return objects;
    }

    /**
     * Takes all the balls and sorts them based on their danger class.
     * <p>
     * possibleDangerBalls  - Balls that are dangerous
     * dangerBalls          - Balls that are dangerous and perpendicular to the AI player
     * imminentDangerBalls  - Balls that are dangerous, perpendicular and close to the AI player
     * </p>
     */
    private void sortBalls() {
        dangerBalls.clear();
        possibleDangerBalls.clear();
        imminentDangerBalls.clear();

        for (Ball ball: balls) {
            Matrix p = ball.getCoordinates();
            Matrix d = ball.getVelocity();
            logger.debug("There are:");

            if (ball.isDangerous()) {
                possibleDangerBalls.add(ball);
                logger.debug ("Possibly Dangerous Balls: " + possibleDangerBalls.size());

                if (canAffect(ball)) {
                    dangerBalls.add(ball);
                    logger.debug("Dangerour Balls: " + dangerBalls.size());

                    if (isClose(ball)) {
                        imminentDangerBalls.add(ball);
                        logger.debug("Imminently Dangerous Balls: " + imminentDangerBalls.size());
                    }
                }
            }
        }
    }

    /**
     * Checks if the AI in danger of the balls.
     *
     * @return true if the ball is headed AI's way or falls if ball is headed in the direction of the AI
     */
    private boolean isAIsafe() {
        sortBalls();
        boolean save = dangerBalls.isEmpty() | imminentDangerBalls.isEmpty();
        logger.debug("AI is " + (save ? "Save" : "in Danger"));
        return save;
    }

    private boolean canAffect(Ball ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition);
    }

    private boolean isClose(Ball ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius();
    }
}
