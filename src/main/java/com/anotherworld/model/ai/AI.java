package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;

/**
 * @author Roman P
 */
public class AI {
    private Player aiPlayer;
    private Player[] otherPlayers;
    private Ball[] balls;

    private AIstate state;

    /**
     *
     * @param aiPlayer pass the reference to the ai player
     * @param otherPlayers the rest of the players on the map(user and ai controlled)
     * @param balls all the balls on the map
     */
    public AI(Player aiPlayer, Player[] otherPlayers, Ball[] balls){
        this.aiPlayer = aiPlayer;
        this.otherPlayers = otherPlayers;
        this.balls = balls;
        this.state = AIstate.IDLE;
    }

    /**
     * Is called when AI needs to make a decision based
     * on the current state of the game session
     */
    public void makeAMove(){

        Matrix aiDirectionVector = new Matrix(aiPlayer.getxVelocity(), aiPlayer.getyVelocity());
        Matrix aiPositionVector = new Matrix(aiPlayer.getxCoordinate(), aiPlayer.getyCoordinate());

        ArrayList<Ball> dangerBalls = new ArrayList<Ball>();
        ArrayList<Ball> neutralBalls = new ArrayList<Ball>();

        //check Balls state
        for(Ball ball: this.balls){
            /*if ball denger
                dangerBalls.add(ball);
              else
                neutralBalls.add(ball);
             */
        }

        if (dangerBalls.isEmpty()){
            // think about chasing neutral balls
            // find the closes path line or ball location
            // position them self it the middle of their path
            // add the ball at other players

        }else {
            // thing about avoiding dangerous balls
            // if too close to the path of the dangerous ball
            // find the most optimal direction (considering other dangerous balls)
            // if not go to neutral balls logic
            // need to ensure that danger balls are still avoided
        }

        //Avoiding balls
        for (Ball ball: balls){
            Matrix ballVelocity = new Matrix(ball.getxVelocity(),ball.getyVelocity());
            Matrix ballLocation = new Matrix(ball.getxCoordinate(), ball.getxCoordinate());

            // Checks if the ball is heading towards the AI
            if (MatrixMath.isPerpendicular(ballVelocity,ballLocation,aiPositionVector)){


            }

        }
    }



    /**
     * [p1] <- x
     * [p2] <- y
     */

}
