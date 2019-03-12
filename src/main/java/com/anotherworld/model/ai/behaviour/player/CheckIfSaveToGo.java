package com.anotherworld.model.ai.behaviour.player;

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



public class CheckIfSaveToGo extends Job {

    private static Logger logger = LogManager.getLogger(CheckIfSaveToGo.class);

    private ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;

    //The allowed safe distance between the ball and the player
    private float safeDistance = 2;

    @Override
    public void reset() {

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        aiPosition = ai.getCoordinates();
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;

        logger.trace("Starting the Job");
//        if (AIDataPool.IsDataPresent()) {
//            ArrayList<Ball> immenentDanger = AIDataPool.getImminentDangerBalls();
//            System.out.println(immenentDanger.toString());
//            if (!immenentDanger.isEmpty()) {
//                Ball firstBall = immenentDanger.get(0);
//                Matrix neigbhour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),ai.getCoordinates());
//                if (MatrixMath.distanceAB(ai.getCoordinates(),neigbhour) <= ai.getRadius() + firstBall.getRadius() + safeDistance) {
//                    ai.setVelocity(0,0);
//                    logger.info("AI stopped danger ahead");
//                    fail();
//                    return;
//                }
//                logger.trace("Balls are far away");
//
//            } else {
//                logger.trace("No danger balls near");
//                succeed();
//            }
//
//        } else {
//            logger.error("No data pool data");
//            succeed();
//        }

        sortBallLevels();
        if (imminentDangerBalls.isEmpty()) {
            succeed();
            logger.trace("All good no bad balls");
        } else {
            // Loops forward in future
            Matrix newAiLocation = new Matrix(ai.getXCoordinate() + ai.getXVelocity(),ai.getYCoordinate() + ai.getYVelocity());
            Ball firstBall = imminentDangerBalls.get(0);

            Matrix neigbhour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),aiPosition);
            if (MatrixMath.distanceAB(aiPosition,neigbhour) <= ai.getRadius() + firstBall.getRadius() + safeDistance) {
                logger.trace("Withing the danger zone need to move out");
                succeed();
                return;
            }


            Matrix newLocationNeigbhour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),newAiLocation);
            if (MatrixMath.distanceAB(newAiLocation,newLocationNeigbhour) <= ai.getRadius() + firstBall.getRadius() + safeDistance + 2) {
                ai.setVelocity(0,0);
                logger.trace("AI stopped danger ahead");
                fail();
                return;
            }

            logger.trace("Optimal distance from danger balls");
            succeed();

        }


    }

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

    }

    private boolean canAffect(Ball ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition);
    }

    private boolean isClose(Ball ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius() +  4;
    }
}
