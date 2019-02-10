package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.behaviour.Sequence;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args){
        PlayerData newPlayer = new PlayerData("Bob",10,5,5, ObjectState.IDLE, 0,5);
        Player player = new Player(newPlayer,true);

        BallData newBall = new BallData(true,0,0,ObjectState.IDLE,0,10);
        Ball ball = new Ball(newBall);
        ArrayList<Ball> balls = new ArrayList<>();
        balls.add(ball);

       // PlatformData newPlatform = new PlatformData();

        Queue<Job> ballAvoid = new LinkedList<Job>();
        //ballAvoid.add(new AvoidEdge());
        ballAvoid.add(new AvoidBall());
        ballAvoid.add(new AvoidBall());
        //ballAvoid.add(new ChaseBall());
        Job job  = new Repeat(new Sequence(ballAvoid));

        job.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("##########test " + i);
            job.act(player,null,balls,null);

        }
    }
}
