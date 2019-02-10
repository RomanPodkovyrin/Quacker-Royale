package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args){
//        System.out.println( Math.toDegrees(Math.atan2(0,-1)));
//        System.out.println(MatrixMath.vectorAngle(new Matrix(-3,-5)));
//        //PlayerData pd = new PlayerData();
//        pd.setCharacterID("Bob");
//        pd.setCoordinates(5,0);
//        pd.setAngle(0);
//        pd.setState(ObjectState.IDLE);
//        Player ai = new Player(pd,true);
//        ai.setRadius(1);
//        ai.setAngle(90);
//        ai.setYVelocity(1);
//        BallData bd = new BallData();
//        bd.setCoordinates(4,4);
//        bd.setState(ObjectState.MOVING);
//        bd.setVelocity(-3,-3);
//        Ball ball = new Ball(bd);
//        ball.setSpeed(1);
//        ball.setAngle(315);
//        ball.setXVelocity(-1);
//        ball.setYVelocity(0);
//        ball.setRadius(1);
//        ball.setDangerous(true);
//        System.out.println(ball.isDangerous() +""+ ball.getXVelocity());
//        AvoidBall job = new AvoidBall();
//
//
//        Job repeatJob = new Repeat((new AvoidBall()));
//
//
////        job.start();
//        repeatJob.start();
//        for (int i = 0; i < 5; i ++) {
//            repeatJob.act(ai,null,new Ball[] {ball},null);
//
//
//        }
    }
}
