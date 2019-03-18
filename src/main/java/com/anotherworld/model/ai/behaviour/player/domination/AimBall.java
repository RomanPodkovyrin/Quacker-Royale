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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * This Job aims to ball towards players.
 *<p>success - aiming
 * fail - too far away or nothing to aim
 * </p>
 */
public class AimBall extends Job {

    private static Logger logger = LogManager.getLogger(AimBall.class);

    public AimBall(){

    }

    @Override
    public void reset() {

    }

    /**
     * - left side
     * + right side
     * relative to the vector.
     *
     * @param vectorCheck
     * @param line
     * @param degree
     * @return
     */
    private boolean onTheSide(Matrix vectorCheck, Matrix line, float degree) {
        float lineDegree = MatrixMath.vectorAngle(line);
        float checkDegree = MatrixMath.vectorAngle(vectorCheck);
        return withingTheRange(checkDegree, (lineDegree - 180) % 360, lineDegree);
    }

    private boolean withingTheRange(float check, float from, float to) {
        if (from < to) {
            return check < to & check > from;
        } else if (from > to) {
            return check < to | check > from;
        } else return from == to & to == check;

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform, GameSessionData session) {
        //TODO either finish me or kill me

        this.ai = ai;
        this.players = players;
        this.balls = balls;
        this.platform = platform;
        this.session = session;

        Ball targetBall = null;
        Player targetPlayer = null;
        logger.info("Aim ball start");
        // find the furthest ball
        balls = sortObject(balls);
        outerloop:
        for (Ball ball: balls) {
            if (!ball.isDangerous()) {
                targetBall = ball;
                Matrix fromAItoBall = MatrixMath.pointsVector(ai.getCoordinates(), ball.getCoordinates());
                Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ball.getCoordinates(), ball.getVelocity()), ai.getCoordinates());
                Matrix vectorTocheck = MatrixMath.pointsVector(ai.getCoordinates(), ball.getCoordinates());
                Matrix vectorToNormal = MatrixMath.pointsVector(ai.getCoordinates(), neighbour);
                if (onTheSide(vectorTocheck, vectorToNormal, -180)) {
                    //means ball is on the left side
                    for (Player player : players) {
                        Matrix vectorToPlayer = MatrixMath.pointsVector(ai.getCoordinates(), player.getCoordinates());
                        if (onTheSide(vectorToPlayer, vectorToNormal, -180)) {
                            //can aim
                            targetPlayer = player;
                            logger.info("Found A player To aim");
                            break outerloop;

                        }
                        // check other player
                    }
                } else {
                    //means ball is on the right side
                    for (Player player : players) {
                        Matrix vectorToPlayer = MatrixMath.pointsVector(ai.getCoordinates(), player.getCoordinates());
                        if (onTheSide(vectorToPlayer, vectorToNormal, 180)) {
                            //can aim
                            targetPlayer = player;
                            logger.info("Found A player To aim");
                            break outerloop;
                        }
                        // check other player
                    }
                }
            }
        }

        if (targetBall == null | targetPlayer == null) {
            succeed();
            logger.info("Nothing to aim");
            return;
        }

        // find the target player


        Matrix neighbour = MatrixMath.nearestNeighbour(new Line(targetBall.getCoordinates(),targetBall.getVelocity()),ai.getCoordinates());

        Matrix fromNeighbourToPlayer = MatrixMath.pointsVector(neighbour,targetPlayer.getCoordinates());

        float hypotenuse = targetPlayer.getRadius() + targetBall.getRadius();

        float angle1 = (MatrixMath.vectorAngle(MatrixMath.pointsVector(neighbour,ai.getCoordinates())) - MatrixMath.vectorAngle(fromNeighbourToPlayer)) % 360;
        float angle2 = (MatrixMath.vectorAngle(MatrixMath.pointsVector(ai.getCoordinates(),neighbour)) - MatrixMath.vectorAngle(fromNeighbourToPlayer)) % 360;
        float angle = angle1;
        System.out.println(angle);
        float adjacent = (float ) (hypotenuse * Math.cos(angle));
        float opposite = (float ) (hypotenuse * Math.sin(angle));

        // normalizing the objects
        Matrix normalized = targetBall.getVelocity().normalize();
        Matrix newLocationOnRoute = neighbour.add(normalized);

        Matrix normalizedD = MatrixMath.pointsVector(ai.getCoordinates(),neighbour).normalize();
        Matrix targetLocation = newLocationOnRoute.add(normalizedD);
        Matrix walk = MatrixMath.pointsVector(ai.getCoordinates(),targetLocation);

        float ballRate = Math.abs(MatrixMath.magnitude(MatrixMath.pointsVector(targetBall.getCoordinates(),walk))) / targetBall.getSpeed();
        float aiRate = Math.abs(MatrixMath.magnitude(MatrixMath.pointsVector(ai.getCoordinates(),neighbour))) / ai.getSpeed();
        if (ballRate < aiRate) {
            succeed();
            logger.info("Too far away won't get there on time");
            return;
        }

        ai.setXVelocity((walk.getX() / Math.abs(walk.getX())) *  ai.getSpeed());
        ai.setYVelocity((walk.getY() / Math.abs(walk.getY())) *  ai.getSpeed());
        logger.info("There is something to aim");
        fail();
    }

    /**
     * Sorts balls based on their distance from the AI player.
     *
     * @param objects The object to be sorted based on the distance from the AI
     * @return returns an ArrayList of Balls starting with the furthest one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects) {

        objects.sort((o2, o1) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
        return objects;
    }
}
