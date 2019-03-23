package com.anotherworld.model.ai.behaviour.player;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.player.domination.ChaseBall;
import com.anotherworld.model.ai.behaviour.player.peace.WalkAbout;
import com.anotherworld.model.ai.behaviour.player.survival.AvoidBall;
import com.anotherworld.model.ai.behaviour.player.survival.AvoidEdge;
import com.anotherworld.model.ai.behaviour.player.survival.AvoidNeutralPlayer;
import com.anotherworld.model.ai.behaviour.player.survival.NeutralBallCheck;
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
    private PlayerData currentAI;
    private ArrayList<PlayerData> otherPlayers = new ArrayList<>();
    private ArrayList<BallData> balls = new ArrayList<>();
    private Platform platform;
    private GameSessionData session;

    @Before
    public void setup(){

        // Create platform
        PlatformData tempPlatform = new PlatformData(80,45);
        platform = new Platform(tempPlatform);

        tempPlatform.setxSize(70);

        tempPlatform.setySize(50);


        // Create players
        currentAI = new PlayerData("AI",100,80,45, ObjectState.IDLE, 1f,1f);
        currentAI.setVelocity(0,0);
        otherPlayers.clear();
        otherPlayers.add(new PlayerData("player",100,50,30, ObjectState.IDLE, 1f,1f));

        // Create balls
        balls.clear();
        balls.add(new BallData("ball " ,false,80,40, ObjectState.IDLE,1f,3.0f));

        session = new GameSessionData(10);
    }

    @Test
    public void avoidBallTest() {

        float delta = 0.00001f;

        // Testing neutral balls, should not move
        // Testing if stays still
        BallData ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(0,1);
        ball.setCoordinates(81,ball.getYCoordinate());
        AvoidBall job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(0,1);
        ball.setCoordinates(79,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,45);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if stays still
        ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,44);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);




        // Testing dangerous balls

        // Testing if going avoid from the ball to left
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(0,1);
        ball.setCoordinates(81,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(-1f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to right
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(0,1);
        ball.setCoordinates(79,ball.getYCoordinate());
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(1f,currentAI.getXVelocity(), delta);
        assertEquals(0f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to bottom
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,46);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(-1f,currentAI.getYVelocity(), delta);

        // Testing if going avoid from the ball to top
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,44);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0f,currentAI.getXVelocity(), delta);
        assertEquals(1f,currentAI.getYVelocity(), delta);

        // Testing if moves away if wall is headed head on
        currentAI.setVelocity(0,0);
        ball = balls.get(0);
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(50,45);
        job = new AvoidBall();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertTrue(!(currentAI.getXVelocity() == 0 & currentAI.getYVelocity() ==0)  );
    }


    @Test
    public void avoidEdgeTest() {
        float delta = 0.00001f;


        // Moves away from bottom edge
        AvoidEdge job = new AvoidEdge();
        currentAI.setCoordinates(80,94);
        currentAI.setVelocity(0,0);
        job.act(currentAI,otherPlayers,balls,platform,session);

        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(-1,currentAI.getYVelocity(),delta);


        // Moves away from top edge
        job = new AvoidEdge();
        currentAI.setCoordinates(80,-4);
        currentAI.setVelocity(0,0);
        job.act(currentAI,otherPlayers,balls,platform,session);

        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(1,currentAI.getYVelocity(),delta);

        // Moves away from left edge
        job = new AvoidEdge();
        currentAI.setCoordinates(11,45);
        currentAI.setVelocity(0,0);
        job.act(currentAI,otherPlayers,balls,platform,session);

        assertEquals(1,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);

        // Moves away from right edge
        job = new AvoidEdge();
        currentAI.setCoordinates(149,45);
        currentAI.setVelocity(0,0);
        job.act(currentAI,otherPlayers,balls,platform,session);

        assertEquals(-1,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);

        // does not move
        job = new AvoidEdge();
        currentAI.setCoordinates(80,45);
        currentAI.setVelocity(0,0);
        job.act(currentAI,otherPlayers,balls,platform,session);

        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);
    }

    @Test
    public void chaseBallTest() {
        float delta = 0.00001f;

        // Should not chase the ball
        BallData ball = balls.get(0);
        currentAI.setVelocity(0,0);
        ball.setDangerous(true);
        ChaseBall job = new ChaseBall();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);


        // Should chase ball right
        ball.setDangerous(false);
        ball.setCoordinates(75,10);
        ball.setVelocity(0,1);
        currentAI.setVelocity(0,0);
        job = new ChaseBall();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(-1,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);

        // Should chase ball left
        ball.setDangerous(false);
        ball.setCoordinates(85,10);
        ball.setVelocity(0,1);
        currentAI.setVelocity(0,0);
        job = new ChaseBall();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(1,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);

        // Should chase ball to bottom
        ball.setDangerous(false);
        ball.setCoordinates(10,50);
        ball.setVelocity(1,0);
        currentAI.setVelocity(0,0);
        job = new ChaseBall();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(1,currentAI.getYVelocity(),delta);

        // Should chase ball to top
        ball.setDangerous(false);
        ball.setCoordinates(10,40);
        ball.setVelocity(1,0);
        currentAI.setVelocity(0,0);
        job = new ChaseBall();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(-1,currentAI.getYVelocity(),delta);
    }

    @Test
    public void checkIfSaveToGoTest() {
        float delta = 0.00001f;

        // Stays stationary because ball is neutral
        BallData ball = balls.get(0);
        ball.setDangerous(false);
        ball.setVelocity(1,0);
        ball.setCoordinates(10,45);
        currentAI.setVelocity(0,1);
        currentAI.setCoordinates(80,45);
        CheckIfSaveToGo job = new CheckIfSaveToGo();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(1,currentAI.getYVelocity(),delta);

        // needs to move out
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(10,41);
        currentAI.setVelocity(0,1);
        currentAI.setCoordinates(80,45);
        job = new CheckIfSaveToGo();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(1,currentAI.getYVelocity(),delta);


        // Save to move
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(10,37);
        currentAI.setVelocity(0,1);
        currentAI.setCoordinates(80,45);
        job = new CheckIfSaveToGo();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(1,currentAI.getYVelocity(),delta);


        // danger need to stop
        ball.setDangerous(true);
        ball.setVelocity(1,0);
        ball.setCoordinates(10,37);
        currentAI.setVelocity(0,-1);
        currentAI.setCoordinates(80,45);
        job = new CheckIfSaveToGo();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertEquals(0,currentAI.getYVelocity(),delta);

    }

    @Test
    public void avoidNeutralPlayerTest() {
        float delta = 0.00001f;

        // Too close move away
        PlayerData player = otherPlayers.get(0);
        AvoidNeutralPlayer job = new AvoidNeutralPlayer();
        player.setCoordinates(80,45);
        player.setVelocity(1,0);
        currentAI.setCoordinates(82,45);
        currentAI.setVelocity(-1,0);
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(0,currentAI.getXVelocity(),delta);
        assertTrue(currentAI.getYVelocity() == 1 | currentAI.getYVelocity() == -1);

         // Too far way keep going
        player = otherPlayers.get(0);
        job = new AvoidNeutralPlayer();
        player.setCoordinates(80,45);
        player.setVelocity(1,0);
        currentAI.setCoordinates(83,45);
        currentAI.setVelocity(-1,0);
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(-1,currentAI.getXVelocity(),delta);
        assertEquals(0, currentAI.getYVelocity(),delta);


        // Player is dead no one to avoid
        player = otherPlayers.get(0);
        job = new AvoidNeutralPlayer();
        player.setCoordinates(80,45);
        player.setVelocity(1,0);
        player.setState(ObjectState.DEAD);
        currentAI.setCoordinates(82,45);
        currentAI.setVelocity(-1,0);
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(-1,currentAI.getXVelocity(),delta);
        assertEquals(0, currentAI.getYVelocity(),delta);
    }

    @Test
    public void neutralBallCheckTest() {
        BallData ball = balls.get(0);
        ball.setDangerous(false);
        NeutralBallCheck job = new NeutralBallCheck();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(Job.JobState.SUCCESS, job.getState());

        ball.setDangerous(true);
        job = new NeutralBallCheck();
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertEquals(Job.JobState.FAILURE, job.getState());

    }

    @Test
    public void walkAboutTest(){
        WalkAbout job = new WalkAbout();
        currentAI.setCoordinates(80,45);
        currentAI.setVelocity(0,0);
        job.start();
        job.act(currentAI,otherPlayers,balls,platform,session);
        assertTrue(currentAI.getYVelocity() != 0 | currentAI.getXVelocity() != 0);
    }


}
