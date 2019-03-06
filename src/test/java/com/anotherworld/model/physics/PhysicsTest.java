package com.anotherworld.model.physics;

import static org.junit.Assert.assertEquals;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class PhysicsTest {
    
    @Test
    public void theCoordinateOfAnobjectShouldUpdate() {
        Physics.setUp();
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        Matrix coord = aball.getCoordinates();
        Physics.move(aball);
        assertEquals(coord.getX(), aball.getXCoordinate(), 0.0f);
        assertEquals(coord.getY(), aball.getYCoordinate(), 0.0f);

        aball.setXVelocity(0.5f);
        Physics.move(aball);
        float xCoord = aball.getXVelocity() + coord.getX();
        // assert statements
        assertEquals(xCoord, aball.getXCoordinate(), 0.0f);
        aball.setVelocity(0.0f, 0.5f);
        float yCoord = aball.getYCoordinate();
        Physics.move(aball);
        yCoord += aball.getYVelocity();
        assertEquals(yCoord, aball.getYCoordinate(), 0.0f);

        aball.setVelocity(-0.5f, -0.5f);
        Physics.move(aball);
        xCoord += aball.getXVelocity();
        yCoord += aball.getYVelocity();
        assertEquals(xCoord, aball.getXCoordinate(), 0.0f);
        assertEquals(yCoord, aball.getYCoordinate(), 0.0f);
    }

    @Test
    public void theBallShouldChangeTheVelocityWhenItHitWalls() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        float yVelocity = 1.0f;
        float xVelocity = 2.0f;
        aball.setYVelocity(yVelocity);
        aball.setXVelocity(xVelocity);
        WallData wallb = new WallData(45.0f, 45.0f);
        Wall wall = new Wall(wallb);
        float northBound = wall.getYCoordinate() + wall.getYSize() / 2;
        aball.setCoordinates(aball.getXCoordinate(), northBound);
        Physics.bouncedWall(aball, wall);
        float expected = northBound + aball.getRadius();
        yVelocity *= -1;
        assertEquals(expected, aball.getYCoordinate(), 0.0f);
        assertEquals(yVelocity, aball.getYVelocity(), 0.0f);
        float eastBound = wall.getXCoordinate() + wall.getXSize() / 2;
        aball.setCoordinates(eastBound, aball.getYCoordinate());
        Physics.bouncedWall(aball, wall);
        expected = eastBound - aball.getRadius();
        xVelocity *= -1;
        assertEquals(expected, aball.getXCoordinate(), 0.0f);
        assertEquals(xVelocity, aball.getXVelocity(), 0.0f);
    }

    @Test
    public void collisionShouldHappen() {
        Physics.setUp();
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        Ball bball = new Ball(a);
        assertEquals(true, Physics.checkCollision(aball, bball));
        bball.setCoordinates(0, 0);
        assertEquals(false, Physics.checkCollision(aball, bball));
    }

    @Test
    public void updateAttributesWhenCollisionHappened() {
        Physics.setUp();
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        PlayerData b = new PlayerData("Steve", 90, 91.1f, 52.3f, null, 12.1f,
                3.0f);
        Player player = new Player(b, false);
        Physics.collided(aball, player);
        b.setCoordinates(aball.getXCoordinate(), aball.getYCoordinate());
        Physics.collided(aball, player);
    }
}