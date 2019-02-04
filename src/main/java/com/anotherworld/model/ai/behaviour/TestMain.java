package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args){



        logger.error("Fuck");
        Player ai = new Player("Bob",5,0,0, ObjectState.IDLE,true);
        ai.setRadius(1);

        Ball ball = new Ball(4,4,ObjectState.MOVING);
        ball.setSpeed(1);
        ball.setAngle(315);
        ball.setxVelocity(-1);
        ball.setyVelocity(0);
        ball.setRadius(1);
        ball.setDamage(true);
        System.out.println(ball.canDamage() +""+ ball.getxVelocity());
        AvoidBall job = new AvoidBall(ai,null,new Ball[] {ball},null);

        job.start();
        for (int i = 0; i < 5; i ++) {
            job.act();
        }
    }
}
