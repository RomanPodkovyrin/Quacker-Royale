package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args){


        System.out.println( Math.toDegrees(Math.atan2(0,-1)));
        System.out.println(MatrixMath.vectorAngle(new Matrix(-3,-5)));
        logger.error("Fuck");
        Player ai = new Player("Bob",5,0,0, ObjectState.IDLE,true);
        ai.setRadius(1);
        ai.setAngle(90);
        ai.setYVelocity(1);

        Ball ball = new Ball(4,4,ObjectState.MOVING);
        ball.setSpeed(1);
        ball.setAngle(315);
        ball.setXVelocity(-1);
        ball.setYVelocity(0);
        ball.setRadius(1);
        ball.setDamage(true);
        System.out.println(ball.canDamage() +""+ ball.getXVelocity());
        AvoidBall job = new AvoidBall(ai,null,new Ball[] {ball},null);

        job.start();
        for (int i = 0; i < 5; i ++) {
            job.act();
        }
    }
}
