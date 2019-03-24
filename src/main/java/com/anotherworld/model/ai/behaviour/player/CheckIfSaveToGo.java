package com.anotherworld.model.ai.behaviour.player;

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
 * This class does a look ahead for ai and sees what happens if ai keeps going
 * in the current direction, (checks if goes into the danger zone).
 * @author roman
 */
public class CheckIfSaveToGo extends Job {

    private static Logger logger = LogManager.getLogger(CheckIfSaveToGo.class);

    // All the balls classed on their danger levels
    private ArrayList<BallData> possibleDangerBalls = new ArrayList<>();
    private ArrayList<BallData> dangerBalls = new ArrayList<>();
    private ArrayList<BallData> imminentDangerBalls = new ArrayList<>();

    //The allowed safe distance between the ball and the player
    private float safeDistance = 2;

    public CheckIfSaveToGo(){

    }

    @Override
    public void reset() {

    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;

        Matrix aiPosition = ai.getCoordinates();

        logger.trace("Starting the Job");

        if (ai.isTimeStopper() || ai.isShielded()) {
            succeed();
            logger.trace("AIController is invulnerable move on");
            return;
        }

        BlackBoard.sortBallLevels(ai,balls,dangerBalls,possibleDangerBalls,imminentDangerBalls);
        if (imminentDangerBalls.isEmpty()) {
            succeed();
            logger.trace("All good no bad balls");
        } else {

            // looks forward in future
            Matrix lookAhead = new Matrix(ai.getXCoordinate() + ai.getXVelocity(),ai.getYCoordinate() + ai.getYVelocity());
            BallData firstBall = imminentDangerBalls.get(0);

            Matrix neighbour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),aiPosition);

            // Already in the danger zone at the current moment
            if (MatrixMath.distanceAB(aiPosition,neighbour) <= ai.getRadius() + firstBall.getRadius() + safeDistance) {
                logger.trace("Within the danger zone need to move out");
                succeed();
                return;
            }

            Matrix lookAheadNeighbour = MatrixMath.nearestNeighbour(new Line(firstBall.getCoordinates(),firstBall.getVelocity()),lookAhead);

            // In danger in the look ahead
            if (MatrixMath.distanceAB(lookAhead,lookAheadNeighbour) <= ai.getRadius() + firstBall.getRadius() + (Math.pow(safeDistance,2))) {
                ai.setVelocity(0,0);
                logger.trace("AIController stopped danger ahead");
                fail();
                return;
            }

            logger.trace("Optimal distance from danger balls");
            succeed();
        }
    }
}
