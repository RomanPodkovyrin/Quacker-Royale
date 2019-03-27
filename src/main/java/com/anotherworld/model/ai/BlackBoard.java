package com.anotherworld.model.ai;

import com.anotherworld.tools.maths.Line;
import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.tools.maths.MatrixMath;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This class has some method which are shared between different jobs as well as it allows to plan actions for ai.
 * @author roman
 */
public class BlackBoard {
    private static Logger logger = LogManager.getLogger(BlackBoard.class);


    //The allowed safe distance between the ball and the player
    private static float safeDistance = 2;
    // Player id - ball target
    private static Map<String, String> targetedBalls = new HashMap<>();
    private static float acceptableHealthPercentage = 0.5f;

    /**
     * Used to set up BlackBoard values.
     */
    public static void setUp() {
        try {
            PropertyReader aiProperties = new PropertyReader("ai.properties");
            safeDistance = Integer.parseInt(aiProperties.getValue("SAFE_BALLS_DISTANCE"));
            acceptableHealthPercentage = Float.parseFloat(aiProperties.getValue("OPTIMAL_HEALTH"));
        } catch (IOException e) {
            logger.error("Could not load values, relying on defaults");
        }

    }

    public static float getSafeDistance() {
        return safeDistance;
    }


    public static float getAcceptableHealthPercentage() {
        return acceptableHealthPercentage;
    }

    /**
     * Targets the the given ball by the given ai.
     * @param ai - ai which targets the balls
     * @param ball - the ball which is targeted
     * @return - true can target or already targeted by this ai, false targeted by someone else
     */
    public static boolean targetBall(PlayerData ai, BallData ball) {
        String aiID = ai.getObjectID();
        String ballID = ball.getObjectID();

        for (Map.Entry<String, String> entry : targetedBalls.entrySet()) {
            String id = entry.getKey();
            String targetBall = entry.getValue();
            if (targetBall.equals(ballID)) {
                return id.equals(aiID);
            }
        }

        targetedBalls.put(aiID, ballID);
        return true;
    }

    /**
     * Stops targeting what ever ball is being targeted by this ai.
     * @param ai - ai which stops targeting the ball
     */
    public static void stopTargetingBall(PlayerData ai) {
        targetedBalls.remove(ai.getObjectID());
    }

    /**
     * Moves ai to a given destination.
     * @param ai - ai to be moved
     * @param destination - destination where ai is moving
     */

    public static void moveTo(PlayerData ai, Matrix destination) {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);

        moveIn(ai,vector);

    }

    /**
     * Directs ai in a given direction.
     * @param ai - ai to be directed
     * @param direction - direction where ai is going to be directed
     */
    public static void moveIn(PlayerData ai, Matrix direction) {
        ai.setXVelocity(Maths.floatDivision(direction.getX(), Math.abs(direction.getX())));
        ai.setYVelocity(Maths.floatDivision(direction.getY(), Math.abs(direction.getY())));
    }

    /**
     * Sorts balls based on their distance from the ControllerAI player.
     *
     * @param objects The object to be sorted based on the distance from the ControllerAI
     * @return returns an ArrayList of Balls starting with the closes one
     */
    public static ArrayList<BallData> sortBalls(PlayerData ai, ArrayList<BallData> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
        return objects;
    }

    /**
     * Sorts players based on their distance from the ControllerAI player. Also discards players which are dead
     *
     * @param objects The object to be sorted based on the distance from the ControllerAI
     * @return returns an ArrayList of players starting with the closes one
     */
    public static ArrayList<PlayerData> sortTargetPlayers(PlayerData ai, ArrayList<PlayerData> objects) {
        ArrayList<PlayerData> players = new ArrayList<>(objects);
        ArrayList<PlayerData> dead = new ArrayList<>();
        for (PlayerData player : players) {
            if (player.getState().equals(ObjectState.DEAD)) {
                dead.add(player);
            }
        }

        //Remove all dead players
        players.removeAll(dead);


        players.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
        return players;
    }

    /**
     * Takes all the balls and sorts them based on their danger class.
     * <p>
     * possibleDangerBalls  - Balls that are dangerous
     * dangerBalls          - Balls that are dangerous and perpendicular to the ControllerAI player
     * imminentDangerBalls  - Balls that are dangerous, perpendicular and close to the ControllerAI player
     * </p>
     */
    public static void sortBallLevels(PlayerData ai, ArrayList<BallData> balls, ArrayList<BallData> dangerBalls, ArrayList<BallData> possibleDangerBalls, ArrayList<BallData> imminentDangerBalls) {
        //Clears old lists
        dangerBalls.clear();
        possibleDangerBalls.clear();
        imminentDangerBalls.clear();

        for (BallData ball: balls) {

            if (ball.isDangerous()) {
                possibleDangerBalls.add(ball);

                if (canAffect(ai,ball)) {
                    dangerBalls.add(ball);

                    if (isClose(ai,ball)) {
                        imminentDangerBalls.add(ball);

                    }
                }
            }
        }
        logger.trace("Possibly Dangerous Balls: " + possibleDangerBalls.size());
        logger.trace("Dangerous Balls: " + dangerBalls.size());
        logger.trace("Imminently Dangerous Balls: " + imminentDangerBalls.size());
    }

    /**
     * Checks it the ball is coming in the direction of the ai.
     *
     * @param ball the ball to be checked
     * @return  true - can affect false cannot
     */
    private static boolean canAffect(PlayerData ai, BallData ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,ai.getCoordinates());
    }

    /**
     * Checks whether the the ball is to close to the ControllerAI.
     * @param ball the ball to be checked
     * @return  true - too close, false at a safe distance
     */
    private static boolean isClose(PlayerData ai, BallData ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),ai.getCoordinates()) <= ai.getRadius() + ball.getRadius() + safeDistance;
    }

}
