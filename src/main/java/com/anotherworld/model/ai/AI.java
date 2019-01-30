package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

public class AI {
    private Player aiPlayer;
    private Player[] otherPlayers;
    private Ball[] balls;

    public AI(Player aiPlayer, Player[] othePlayers, Ball[] balls){
        this.aiPlayer = aiPlayer;
        this.otherPlayers = othePlayers;
        this.balls = balls;
    }

    public void think(){

        Matrix aiDirectionVector = new Matrix(aiPlayer.getxVelocity(), aiPlayer.getyVelocity());
        Matrix aiPositionVector = new Matrix(aiPlayer.getxCoordinate(), aiPlayer.getyCoordinate());

        //Avoiding balls
        for (Ball ball: balls){

        }
    }

//    public float vecToLine(Matrix startPoint, Matrix directionVecotor){
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
