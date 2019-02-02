package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

/**
 * @author Roman P
 */
public class AI {
    private Player aiPlayer;
    private Player[] otherPlayers;
    private Ball[] balls;

    public AI(Player aiPlayer, Player[] othePlayers, Ball[] balls){
        this.aiPlayer = aiPlayer;
        this.otherPlayers = othePlayers;
        this.balls = balls;
    }

    /**
     * Is Called when AI needs to make a decision based
     * on the current state of the game session
     */
    public void makeAMove(){

        Matrix aiDirectionVector = new Matrix(aiPlayer.getxVelocity(), aiPlayer.getyVelocity());
        Matrix aiPositionVector = new Matrix(aiPlayer.getxCoordinate(), aiPlayer.getyCoordinate());

        //Avoiding balls
        for (Ball ball: balls){
            Matrix ballVelocity = new Matrix(ball.getxVelocity(),ball.getyVelocity());
            Matrix ballLocation = new Matrix(ball.getxCoordinate(), ball.getxCoordinate());
            if (MatrixMath.isPerpendicular(ballVelocity,ballLocation,aiPositionVector)){

            }

        }
    }

//    public float vecToLine(Matrix startPoint, Matrix directionVector){
//
//
//
//        return ;
//    }

    /**
     * [p1] <- x
     * [p2] <- y
     */

}
