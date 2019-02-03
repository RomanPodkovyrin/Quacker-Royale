package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;

public class avoidBall extends Job {

    private ArrayList<Ball> possibleDangerBalls = new ArrayList<>();
    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDangerBalls = new ArrayList<>();
    private Matrix aiPosition;
    private Matrix aiDirection;

    public avoidBall(Player ai, Player[] players, Ball[] balls, Platform platform ){
        super(ai,players,balls,platform);

    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act() {
        aiDirection = new Matrix(ai.getxVelocity(),ai.getyCoordinate());
        aiPosition = new Matrix(ai.getxCoordinate(),ai.getxCoordinate());

        if(isRunning() & ai.getHealth() ==0){
            fail();
            return;
        }

        if(!isAISafe()){
            //avoid the ball
            // sort bolls
            sortBalls();
            // first go opposite
        }
    }

    private void moveAway(){
        Matrix ballPosition = new Matrix(imminentDangerBalls.get(0).getxCoordinate(),imminentDangerBalls.get(0).getyCoordinate());
        Matrix ballDirection = new Matrix(imminentDangerBalls.get(0).getxVelocity(), imminentDangerBalls.get(0).getyVelocity());

        MatrixMath.nearestNeighbour(new Line(ballPosition,ballDirection),aiPosition);
        Matrix vector = MatrixMath.pointsVector(aiPosition,MatrixMath.nearestNeighbour(new Line(ballPosition,ballDirection),aiPosition));
        ai.setAngle(MatrixMath.vectorAngle());
    }

    /**
     * Sorts balls based on their distance from the AI player
     * @param objects
     * @return returns a n ArrayList of Balls starting with the closes one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects){

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getxCoordinate(),o1.getyCoordinate()),aiPosition))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getxCoordinate(),o2.getyCoordinate()),aiPosition)));
        return objects;
    }

    private void sortBalls(){

        for(Ball ball: balls){
            if(ball.canDamage()){
                possibleDangerBalls.add(ball);
                if(canAffect(ball)){
                    dangerBalls.add(ball);

                    Matrix ballPosition = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
                    Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());

                    if(MatrixMath.distanceAB(ballPosition,aiPosition) <= ball.getRadius() + ai.getRadius()){
                        imminentDangerBalls.add(ball);
                    }
                }
            }
        }
    }

    private boolean isAISafe() {
        return imminentDangerBalls.isEmpty();
    }

    private boolean canAffect(Ball ball){
        Matrix ballPosition = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
        Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());

        return ball.canDamage() & MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition) & isClose(ball);
    }

    private boolean isClose(Ball ball ){
        Matrix ballPosition = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
        Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());

        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius();
    }
}
