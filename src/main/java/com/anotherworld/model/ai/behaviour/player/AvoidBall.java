package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.AIDataPool;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Makes sure that the AI player stays away from the Dangerous balls.
 *  success - AI is safe no need to avoid balls
 *  fail - AI is not safe need to avoid balls
 * @author Roman
 */
public class AvoidBall extends Job {

    private static Logger logger = LogManager.getLogger(AvoidBall.class);

    private ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;
    private Matrix aiDirection;

    //The allowed safe distance between the ball and the player
    private float safeDistance = 2;

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

        logger.trace("Starting the AvoidBall Job");
        aiDirection = ai.getVelocity();
        aiPosition = ai.getCoordinates();

        //Sorts the balls based on their distance to the AI
        sortObject(this.balls);


        if (!isAIsafe()) {
            logger.trace("Moving away from the Ball");
            moveAway();
            fail();
            return;
        } else {
            logger.trace("Finishing AvoidBall Job with success");
            succeed();
            return;
        }
    }

    /**
     * Points the AI players in the opposite direction of the dangerous ball.
     */
    private void moveAway() {
        // loads the first value which is the close balls
        Matrix ballPosition;
        Matrix ballDirection;

        if (!imminentDangerBalls.isEmpty()) {
            ballPosition = imminentDangerBalls.get(0).getCoordinates();
            ballDirection = imminentDangerBalls.get(0).getVelocity();
        } else {
            logger.error("AI is safe computation error");
            succeed();
            return;
        }

        // Getting the neighbour in the ball direction vector
        Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ballPosition, ballDirection),aiPosition);
        // Vector from AI to the closes point
        Matrix vector = MatrixMath.pointsVector(aiPosition, neighbour);

        // - reverses the vectors, so ai goes in the opposite direction of the Ball
        ai.setXVelocity((-vector.getX() / Math.abs(vector.getX())) * ai.getSpeed());
        ai.setYVelocity((-vector.getY() / Math.abs(vector.getY())) * ai.getSpeed());

        logger.info("Avoiding Ball at location " + ballPosition);
        logger.info("Walking at Vector " + ai.getVelocity());

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
    private void sortBallLevels() {
        //Clears old lists
        dangerBalls.clear();
        possibleDangerBalls.clear();
        imminentDangerBalls.clear();

        for (Ball ball: balls) {

            if (ball.isDangerous()) {
                possibleDangerBalls.add(ball);

                if (canAffect(ball)) {
                    dangerBalls.add(ball);

                    if (isClose(ball)) {
                        imminentDangerBalls.add(ball);

                    }
                }
            }
        }
        logger.trace("Possibly Dangerous Balls: " + possibleDangerBalls.size());
        logger.trace("Dangerous Balls: " + dangerBalls.size());
        logger.trace("Imminently Dangerous Balls: " + imminentDangerBalls.size());
        AIDataPool.setBalls(possibleDangerBalls,dangerBalls,imminentDangerBalls);

    }

    /**
     * Checks if the AI in danger of the balls.
     *
     * @return true if the ball is headed AI's way or falls if ball is headed in the direction of the AI
     */
    private boolean isAIsafe() {
        //Classed balls into danger categories
        sortBallLevels();
        boolean tooFar = true;


        if (!imminentDangerBalls.isEmpty()) {


            float distanceFromTheBall = MatrixMath.distanceAB(ai.getCoordinates(),imminentDangerBalls.get(0).getCoordinates());
            tooFar = distanceFromTheBall >= platform.getYSize();

            Ball ball = imminentDangerBalls.get(0);
            Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ball.getCoordinates(),ball.getVelocity()),ai.getCoordinates());
            float ballRate = MatrixMath.distanceAB(ball.getCoordinates(),neighbour)/ ball.getSpeed();
            float aiRate = MatrixMath.distanceAB(ai.getCoordinates(),neighbour)/ ai.getSpeed();
            if (ballRate < aiRate) {
                logger.info("Too far away safe");
                tooFar = false;
            }

        }

        //TODO fix the length prediction
        boolean save =  imminentDangerBalls.isEmpty()| !tooFar;
        logger.trace("AI is " + (save ? "Save" : "in Danger"));
        return save;
    }

    /**
     * Checks it the ball is coming in the direction of the ai.
     *
     * @param ball the ball to be checked
     * @return  true - can affect false cannot
     */
    private boolean canAffect(Ball ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition);
    }

    /**
     * Checks whether the the ball is to close to the AI.
     * @param ball the ball to be checked
     * @return  true - too close, false at a safe distance
     */
    private boolean isClose(Ball ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius() + safeDistance;
    }
}
