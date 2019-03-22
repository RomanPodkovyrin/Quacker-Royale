package com.anotherworld.model.ai;

import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.maths.Maths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class BlackBoard {
    private static Logger logger = LogManager.getLogger(BlackBoard.class);
    //TODO clean up this class
    //The allowed safe distance between the ball and the player
    private static float safeDistance = 4;
    private static Map<String, String> targetedBalls = new HashMap<>();
    private static Map<String, String> targetedPlayers = new HashMap<>();
    private static Map<String, AI_State> playersStates = new HashMap<>();
    private static float acceptableHealthPercentage = 0.5f;

    public static float getAcceptableHealthPercentage() {
        return acceptableHealthPercentage;
    }

    public static void moveTo(PlayerData ai, Matrix destination) {
        Matrix vector = MatrixMath.pointsVector(ai.getCoordinates(), destination);

        moveIn(ai,vector);

    }

    public static void moveIn(PlayerData ai, Matrix direction) {
        ai.setXVelocity(Maths.floatDivision(direction.getX(), Math.abs(direction.getX())));
        ai.setYVelocity(Maths.floatDivision(direction.getY(), Math.abs(direction.getY())));
    }

    /**
     * Sorts balls based on their distance from the AI player.
     *
     * @param objects The object to be sorted based on the distance from the AI
     * @return returns an ArrayList of Balls starting with the closes one
     */
    public static ArrayList<BallData> sortBalls(PlayerData ai, ArrayList<BallData> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),ai.getCoordinates()))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),ai.getCoordinates())));
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
    public static boolean canAffect(PlayerData ai, BallData ball) {
        Matrix ballPosition = ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();
        return MatrixMath.isPerpendicular(ballDirection,ballPosition,ai.getCoordinates());
    }

    /**
     * Checks whether the the ball is to close to the AI.
     * @param ball the ball to be checked
     * @return  true - too close, false at a safe distance
     */
    public static boolean isClose(PlayerData ai, BallData ball) {
        Matrix ballPosition =  ball.getCoordinates();
        Matrix ballDirection = ball.getVelocity();

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),ai.getCoordinates()) <= ai.getRadius() + ball.getRadius() + safeDistance;
    }

    /**
     *
     * @param targetPlayer target player id
     * @param ai ai aid
     * @return
     */
    public static boolean isPlayerTargeted(String targetPlayer, String ai) {
        return !(targetedPlayers.containsKey(targetPlayer) | targetedPlayers.get(targetPlayer).equals(ai));
    }

    public static void targetThePlayer(String targetPlayer, String ai) {
        targetedPlayers.put(targetPlayer,ai);
    }

    public static void stopTargetingPlayer(String targetPlayer, String ai) {
        targetedPlayers.remove(targetPlayer);
    }

    public static void setState(String id, AI_State state) {
        playersStates.put(id,state);
    }

    public static AI_State getState(String id) {
        return playersStates.get(id);
    }

    public static void main() {

    }

    public enum AI_State {
        NORMAL,INVULNERABLE
    }
}
