package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PlayerBehaviourTest {
    private Player currentAI;
    private ArrayList<Player> otherPlayers = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private Platform platform;

    @Before
    public void setup(){

        // Create platform
        platform = new Platform(new PlatformData(80,45));

        platform.setXSize(75);

        platform.setYSize(50);


        // Create players
        currentAI = new Player(new PlayerData("AI",100,80,45, ObjectState.IDLE, 1f,1f),true);
        currentAI.setVelocity(0,0);
        otherPlayers.clear();
        otherPlayers.add(new Player(new PlayerData("player",100,50,30, ObjectState.IDLE, 1f,1f),false));

        // Create balls
        balls.clear();
        balls.add(new Ball( new BallData("ball " ,false,80,10, ObjectState.IDLE,1f,3.0f)));


    }

    @Test
    public void avoidBallTest() {

        float delta = 0.00001f;

        // Testing neutral balls, should not move
        // Testing if stays still
        Ball ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(0,1);
        ball.setCoordinates(81,ball.getYCoordinate());
        AvoidBall job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(0,1);
        ball.setCoordinates(79,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,45);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,44);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);




        // Testing dangerous balls

        // Testing if going avoid from the ball to left
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(0,1);
        ball.setCoordinates(81,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(-1f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to right
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(0,1);
        ball.setCoordinates(79,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(1f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to bottom
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,46);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(-1f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to top
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,44);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(1f,currentAI.getYVelocity(), delta);

        // Testing if moves away if wall is headed head on
        currentAI.setVelocity(0,0);
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,45);
        job = new AvoidBall();
        System.out.println(currentAI.getVelocity());
        job.act(currentAI,otherPlayers,balls,platform);
        System.out.println(currentAI.getVelocity());
        assertTrue(!(currentAI.getXVelocity() == 0 & currentAI.getYVelocity() ==0)  );
    }


    @Test
    public void avoidEdgeTest() {

        AvoidEdge job = new AvoidEdge();
        job.act(currentAI,otherPlayers,balls,platform);



    }

}
