package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class does a look ahead for ai and sees what happens if ai keeps going
 * in the current direction, (checks if goes into the danger zone).
 * @author roman
 */
public class CheckIfSaveToGo extends Job {

    private static Logger logger = LogManager.getLogger(CheckIfSaveToGo.class);

    private ArrayList<BallData> possibleDangerBalls = new ArrayList<>();
    private ArrayList<BallData> dangerBalls = new ArrayList<>();
    private ArrayList<BallData> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;

    //The allowed safe distance between the ball and the player
    private float safeDistance = 2;

    public CheckIfSaveToGo(){

    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        aiPosition = ai.getCoordinates();
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;

        logger.trace("Starting the Job");

        if (ai.isTimeStopper() || ai.isShielded()) {
//            logger.trace("Ai is shielded or has a time stop");
            succeed();
            logger.trace("AI is invulnerable move on");
            return;
        }

        sortBallLevels();
        if (imminentDangerBalls.isEmpty()) {
            succeed();
            logger.trace("All good no bad balls");
        } else {

            // looks forward in future
            Matrix lookAhead = new Matrix(ai.getXCoordinate() + ai.getXVelocity(),ai.getYCoordinate() + ai.getYVelocity());
            BallData firstBall = imminentDangerBalls.get(0);

            Matrix neigbhour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),aiPosition);

            // Already int the danger zone at the current moment
            if (MatrixMath.distanceAB(aiPosition,neigbhour) <= ai.getRadius() + firstBall.getRadius() + safeDistance) {
                logger.trace("Withing the danger zone need to move out");
                succeed();
                return;
            }


            Matrix lookAheadNeigbhour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),lookAhead);

            // In danger in the look ahead
            if (MatrixMath.distanceAB(lookAhead,lookAheadNeigbhour) <= ai.getRadius() + firstBall.getRadius() + safeDistance + 2) {
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

        for (BallData ball: balls) {

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

    private boolean canAffect(BallData ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition);
    }

    private boolean isClose(BallData ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius() +  4;
    }
}
