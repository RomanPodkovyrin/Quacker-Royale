package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class AvoidBall extends Job {

    private ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;
    private Matrix aiDirection;

    public AvoidBall(Player ai, Player[] players, Ball[] balls, Platform platform ) {
        super(ai,players,balls,platform);

    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act() {
        aiDirection = new Matrix(ai.getXVelocity(),ai.getYVelocity());
        aiPosition = new Matrix(ai.getXCoordinate(),ai.getXCoordinate());

        System.out.println("direction " + aiDirection + " positon" +aiPosition + "Angle: " + ai.getAngle());
        if(isRunning() & ai.getHealth() ==0){
            fail();
            return;
        }

        if(!isAISafe()){
            //avoid the ball
            // sort bolls
            //sortBalls();
            System.out.println("Fuck run");
            // first go opposite
            moveAway();
        } else {
            System.out.println("Safe");
            succeed();
            return;
        }
    }

    private void moveAway() {
        Matrix ballPosition = new Matrix(dangerBalls.get(0).getXCoordinate(),dangerBalls.get(0).getYCoordinate());
        Matrix ballDirection = new Matrix(dangerBalls.get(0).getXVelocity(), dangerBalls.get(0).getYVelocity());

        Matrix neighbour = MatrixMath.nearestNeighbour(new Line(ballPosition, ballDirection),aiPosition);
        Matrix vector = MatrixMath.pointsVector(aiPosition, neighbour);
        System.out.println(vector);
        ai.setAngle(MatrixMath.vectorAngle(MatrixMath.flipVector(vector)));
        //temp
        ai.setXVelocity(-vector.getX());
        ai.setYVelocity(-vector.getY());
        System.out.println(vector.getY());
    }

    /**
     * Sorts balls based on their distance from the AI player
     * @param objects
     * @return returns an ArrayList of Balls starting with the closes one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects) {

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getXCoordinate(),o1.getYCoordinate()),aiPosition))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getXCoordinate(),o2.getYCoordinate()),aiPosition)));
        return objects;
    }

    private void sortBalls() {
        dangerBalls.clear();
        possibleDangerBalls.clear();
        imminentDangerBalls.clear();

        for(Ball ball: balls){
            Matrix p = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
            Matrix d = new Matrix(ball.getXVelocity(), ball.getYVelocity());

            System.out.println("Ball: direction " + d + " positon" +p);
            if(ball.canDamage()){
                possibleDangerBalls.add(ball);
                System.out.println("Possible " + Arrays.toString(possibleDangerBalls.toArray()));
                if(canAffect(ball)){
                    dangerBalls.add(ball);
                    System.out.println("danger " + Arrays.toString(dangerBalls.toArray()));
                    Matrix ballPosition = new Matrix(ball.getXCoordinate(),ball.getYCoordinate());
                    Matrix ballDirection = new Matrix(ball.getXVelocity(), ball.getYVelocity());

                    if(MatrixMath.distanceAB(ballPosition,aiPosition) <= ball.getRadius() + ai.getRadius()){
                        imminentDangerBalls.add(ball);
                        System.out.println("imminent " + Arrays.toString(imminentDangerBalls.toArray()));
                    }
                }
            }
        }
    }

    private boolean isAISafe() {
        sortBalls();
        return dangerBalls.isEmpty();
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
