package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.aiMathsTools.Line;
import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;

public class avoidBall extends Job {

    private ArrayList<Ball> dangerBalls = new ArrayList<>();
    private ArrayList<Ball> imminentDengerBalls = new ArrayList<>();

    public avoidBall(Player ai, Player[] players, Ball[] balls, Platform platform ){
        super(ai,players,balls,platform);

    }

    @Override
    public void reset() {
        start();
    }

    @Override
    public void act() {
        if(isRunning() & ai.getHealth() ==0){
            fail();
            return;
        }

        if(isAISafe(ai)){

        }
    }

    private void sortBalls(){
        for(Ball ball: balls){

        }
    }

    private boolean isAISafe(Player ai) {

        return false;
    }

//    private boolean canAffect(Ball ball){
//        Matrix ballPosition = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
//        Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());
//
//        return ball.canDamage() & MatrixMath.isPerpendicular(ballDirection,ballPosition,aiPosition) & isClose(ball);
//    }
//
//    private boolean isClose(Ball ball ){
//        Matrix ballPosition = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
//        Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());
//
//        return MatrixMath.distanceToNearestPoint(new Line(ballPosition,ballDirection),aiPosition) <= ai.getRadius() + ball.getRadius();
//    }
}
