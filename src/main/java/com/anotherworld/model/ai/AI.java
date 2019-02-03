package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Roman P
 */
public class AI {
    private Player aiPlayer;
    private Player[] otherPlayers;
    private Ball[] balls;
    private Platform platform;

    private AIstate state;
    private Matrix aiVector;
    private Matrix aiPosition;

    /**
     *
     * @param aiPlayer pass the reference to the ai player
     * @param otherPlayers the rest of the players on the map(user and ai controlled)
     * @param balls all the balls on the map
     */
    public AI(Player aiPlayer, Player[] otherPlayers, Ball[] balls, Platform platform){
        this.aiPlayer = aiPlayer;
        this.otherPlayers = otherPlayers;
        this.balls = balls;
        this.platform = platform;

        this.state = AIstate.IDLE;
        this.aiVector = new Matrix(aiPlayer.getxVelocity(), aiPlayer.getyVelocity());
        this.aiPosition = new Matrix(aiPlayer.getxCoordinate(), aiPlayer.getyCoordinate());


    }

    /**
     * Is called when AI needs to make a decision based
     * on the current state of the game session
     */
    public void action(){

        aiVector = new Matrix(aiPlayer.getxVelocity(), aiPlayer.getyVelocity());
        aiPosition = new Matrix(aiPlayer.getxCoordinate(), aiPlayer.getyCoordinate());


        if(this.state == AIstate.IDLE | this.state == AIstate.AVOIDING){
            avoidTheBall();
        } else if (this.state == AIstate.CHASING){
            //check if the vector of choice is crossing the danger balls path

        } else if (this.state == AIstate.AIMING){

        } else if (this.state == AIstate.IDLE){

        } else if (this.state == AIstate.GETTOMIDDLE){

        }

        something();
    }

    private boolean canAffect(Ball ball){
        Matrix ballLocation = new Matrix(ball.getxCoordinate(),ball.getyCoordinate());
        Matrix ballDirection = new Matrix(ball.getxVelocity(), ball.getyVelocity());

        return ball.canDamage() & MatrixMath.isPerpendicular(ballDirection,ballLocation,aiPosition);
    }


    private void avoidTheBall(){
        ArrayList<Ball> dangerBalls = new ArrayList<>();
        for(Ball ball: this.balls){

            if(canAffect(ball)){
                dangerBalls.add(ball);
            }
        }
        sortObject(dangerBalls);

        for(Ball ball: dangerBalls){

        }

    }

    /**
     * Sorts balls based on their distance from the AI player
     * @param objects
     * @return returns a n ArrayList of Balls starting with the closes one
     */
    private ArrayList<Ball> sortObject(ArrayList<Ball> objects){
//        for(AbstractMovable object: objects){
//        }
//        Collections.sort(objects,
//                (o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getxCoordinate(),o1.getyCoordinate()),aiPosition))
//                        .compareTo(MatrixMath.distanceAB(new Matrix(o2.getxCoordinate(),o2.getyCoordinate()),aiPosition)));

        objects.sort((o1, o2) -> ((Float)MatrixMath.distanceAB(new Matrix(o1.getxCoordinate(),o1.getyCoordinate()),aiPosition))
                .compareTo(MatrixMath.distanceAB(new Matrix(o2.getxCoordinate(),o2.getyCoordinate()),aiPosition)));
        return objects;
    }

    private void something() {

        ArrayList<Ball> dangerBalls = new ArrayList<>();
        ArrayList<Ball> neutralBalls = new ArrayList<>();

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
            if (MatrixMath.isPerpendicular(ballVelocity,ballLocation,aiPosition)){


            }

        }
    }


    /**
     * [p1] <- x
     * [p2] <- y
     */

}
