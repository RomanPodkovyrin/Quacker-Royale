package com.anotherworld.model.ai.behaviour.player.domination;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Job class which makes the player chase the neutral ball.
 * @author roman
 */
public class ChaseBall extends Job {

    private static Logger logger = LogManager.getLogger(ChaseBall.class);

    public  ChaseBall() {
        super();
    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(PlayerData ai, ArrayList<PlayerData> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {
        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;

        logger.debug("Starting ChaseBall Job");

        //Todo by chasing the closes ball it is hard to get it on time
        sortObject(balls);

        for (Ball ball: balls) {

            // Checks if the ball is currently not dangerous and on the wall
            if (!ball.isDangerous() & isRunning() & platform.isOnPlatform(ball.getCoordinates())) {
                logger.debug("Chasing the Ball at " + ball.getCoordinates());

                Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ball.getCoordinates(),ball.getVelocity()),ai.getCoordinates());
                Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), neighbour);

                // Checks if it is already near the ball
                if (MatrixMath.distanceAB(ai.getCoordinates(),neighbour) <= ball.getRadius() + ai.getRadius()) {
                    succeed();
                    return;
                }

                if (vector.getX() != 0) {
                    ai.setXVelocity(Maths.floatDivision(vector.getX() , Math.abs(vector.getX())) * ai.getSpeed());
                }
                if (vector.getY() != 0) {
                    ai.setYVelocity(Maths.floatDivision(vector.getY() , Math.abs(vector.getY())) * ai.getSpeed());
                }
                succeed();
                return;
            } else {
                logger.debug("Finishing ChaseBall with fail: nothing to chase");
                ai.setXVelocity(0);
                ai.setYVelocity(0);
                fail();
                return;
            }
        }
    }

    /**
     * Sorts balls based on their distance from the AI player.
     *
     * @param objects The object to be sorted based on the distance from the AI
     * @return returns an ArrayList of Balls starting with the closes one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
        return objects;
    }
}
