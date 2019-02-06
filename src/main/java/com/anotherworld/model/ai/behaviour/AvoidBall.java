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
    public void act(Player ai, Player[] players, Ball[] balls, Platform platform) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;

        aiDirection = new Matrix(ai.getXVelocity(),ai.getYVelocity());
        aiPosition = new Matrix(ai.getXCoordinate(),ai.getXCoordinate());

        System.out.println("direction " + aiDirection + " positon" + aiPosition + "Angle: " + ai.getAngle());
        if (isRunning() & ai.getHealth() == 0) {
            fail();
            return;
        }

        if (!isAIsafe()) {
            //avoid the ball
            // sort bolls
            //sortBalls();
            // first go opposite
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
        Matrix ballPosition = new Matrix(dangerBalls.get(0).getXCoordinate(),dangerBalls.get(0).getYCoordinate());
        Matrix ballDirection = new Matrix(dangerBalls.get(0).getXVelocity(), dangerBalls.get(0).getYVelocity());

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

        for(Ball ball: balls){
            Matrix p = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
            Matrix d = new Matrix(ball.getXVelocity(), ball.getYVelocity());

            System.out.println("Ball: direction " + d + " positon" + p);
            if (ball.canDamage()) {
                possibleDangerBalls.add(ball);
                System.out.println("Possible " + Arrays.toString(possibleDangerBalls.toArray()));
                if (canAffect(ball)) {
                    dangerBalls.add(ball);
                    System.out.println("danger " + Arrays.toString(dangerBalls.toArray()));
                    Matrix ballPosition = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
                    Matrix ballDirection = new Matrix(ball.getXVelocity(), ball.getYVelocity());

                    if (MatrixMath.distanceAB(ballPosition,aiPosition) <= ball.getRadius() + ai.getRadius()) {
                        imminentDangerBalls.add(ball);
                        System.out.println("imminent " + Arrays.toString(imminentDangerBalls.toArray()));
                    }
                }
            }
        }
    }

    private boolean isAIsafe() {
        sortBalls();
        boolean save = dangerBalls.isEmpty();
        logger.trace("AI is " + (save ? "Save" : "in Danger"));
        return save;
    }

    private boolean canAffect(Ball ball) {
        Matrix ballPosition = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
        Matrix ballDirection = new Matrix(ball.getXVelocity(), ball.getYVelocity());
        System.out.println("Perpendicular "+MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition));
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition) ;//& isClose(ball);
    }

    private boolean isClose(Ball ball ) {
        Matrix ballPosition = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
        Matrix ballDirection = new Matrix(ball.getXVelocity(), ball.getYVelocity());

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius();
    }
}
