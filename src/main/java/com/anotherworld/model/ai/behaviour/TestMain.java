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

        Ball ball = new Ball(4,4,ObjectState.MOVING);
        ball.setSpeed(1);
        ball.setAngle(315);
        AvoidBall job = new AvoidBall(ai,null,new Ball[] {ball},null);

        for (int i = 0; i < 5; i ++) {
            job.act();
        }
    }
}
