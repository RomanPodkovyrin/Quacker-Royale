package com.anotherworld.model.ai.behaviour.player.survival;

import com.anotherworld.model.ai.BlackBoard;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

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

    // Classified balls into different categories of importance
    private ArrayList<BallData> possibleDangerBalls = new ArrayList<>();
    private ArrayList<BallData> dangerBalls = new ArrayList<>();
    private ArrayList<BallData> imminentDangerBalls = new ArrayList<>();

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
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        setData(ai,players,balls,platform,session);


        logger.trace("Starting the AvoidBall Job");

        //Sorts the balls based on their distance to the AI
        BlackBoard.sortBalls(this.ai, this.balls);


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
        // loads the first value which is the closest balls
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
        Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ballPosition, ballDirection),ai.getCoordinates());

        // Vector from AI to the closes point
        Matrix direction = MatrixMath.pointsVector(ai.getCoordinates(), neighbour);

        // Check if vectors are zero
        if ((Math.abs(direction.getY() - 0f) < 0.00000001f) & (Math.abs(direction.getX() - 0f) < 0.00000001f)) {
            Line ballLine = new Line(ballPosition,ballDirection);
            direction = ballLine.getOrthogonalVector();
        }

        // reverses the vectors, so ai goes in the opposite direction of the Ball
        direction = MatrixMath.flipMatrix(direction);

        BlackBoard.moveIn(ai,direction);

        logger.trace("Avoiding Ball at location " + ballPosition + " Walking in direction " + ai.getVelocity());

    }

    /**
     * Checks if the AI in danger of the balls.
     *
     * @return true if the ball is headed AI's way or falls if ball is headed in the direction of the AI
     */
    private boolean isAIsafe() {
        // sorts balls into danger categories
        BlackBoard.sortBallLevels(ai,balls,dangerBalls,possibleDangerBalls,imminentDangerBalls);

        boolean tooFar = false; // tells whether the ball is too far to affect the player

        if (!imminentDangerBalls.isEmpty()) {
        // TODO either make me work or purge me
//            float distanceFromTheBall = MatrixMath.distanceAB(ai.getCoordinates(),imminentDangerBalls.get(0).getCoordinates());
////            tooFar = distanceFromTheBall >= platform.getYSize();
//
//            // checks if ai is safe because the ball is too far away
//            BallData ball = imminentDangerBalls.get(0);
//            Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ball.getCoordinates(),ball.getVelocity()),ai.getCoordinates());
//            float ballRate = Maths.floatDivision(MatrixMath.distanceAB(ball.getCoordinates(),neighbour) , ball.getSpeed());
//            float aiRate = Maths.floatDivision(MatrixMath.distanceAB(ai.getCoordinates(),neighbour) , ai.getSpeed());
//            if (ballRate < aiRate) {
//                logger.debug("Too far away safe");
//                tooFar = false;
//            }
        }

        boolean save =  imminentDangerBalls.isEmpty() || tooFar;
        logger.trace("AI is " + (save ? "Save" : "in Danger"));
        return save;
    }

}