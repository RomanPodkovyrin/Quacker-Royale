package com.anotherworld.model.physics;

import static org.junit.Assert.assertEquals;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.model.physics.Physics;
import com.anotherworld.settings.GameSettings;
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
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        Matrix coord = aball.getCoordinates();
        Physics.move(aball);
        assertEquals(coord.getX(), aball.getXCoordinate(), 0.0f);
        assertEquals(coord.getY(), aball.getYCoordinate(), 0.0f);

        aball.setXVelocity(0.5f);
        Physics.move(aball);
        float xCoord = (aball.getXVelocity() * aball.getSpeed()) + coord.getX();
        // assert statements
        assertEquals(xCoord, aball.getXCoordinate(), 0.0f);
        aball.setVelocity(0.0f, 0.5f);
        float yCoord = aball.getYCoordinate();
        Physics.move(aball);
        yCoord += (aball.getYVelocity() * aball.getSpeed());
        assertEquals(yCoord, aball.getYCoordinate(), 0.0f);

        aball.setVelocity(-0.5f, -0.5f);
        Physics.move(aball);
        xCoord += aball.getXVelocity() * aball.getSpeed();
        yCoord += aball.getYVelocity() * aball.getSpeed();
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
        float northBound = wall.getYCoordinate() - wall.getYSize();
        aball.setCoordinates(aball.getXCoordinate(), northBound);
        Physics.bouncedWall(aball, wall);
        float expected = northBound + aball.getRadius();
        yVelocity = Math.abs(yVelocity);
        assertEquals(expected, aball.getYCoordinate(), 0.0f);
        assertEquals(yVelocity, aball.getYVelocity(), 0.0f);
        float eastBound = wall.getXCoordinate() - wall.getXSize();
        aball.setCoordinates(eastBound, aball.getYCoordinate());
        Physics.bouncedWall(aball, wall);
        expected = eastBound + aball.getRadius();
        xVelocity = Math.abs(xVelocity);
        assertEquals(expected, aball.getXCoordinate(), 0.0f);
        assertEquals(xVelocity, aball.getXVelocity(), 0.0f);
        float southBound = wall.getYCoordinate() + wall.getYSize();
        aball.setCoordinates(aball.getXCoordinate(), southBound);
        Physics.bouncedWall(aball, wall);
        expected = southBound - aball.getRadius();
        yVelocity = -Math.abs(yVelocity);
        assertEquals(expected, aball.getYCoordinate(), 0.0f);
        assertEquals(yVelocity, aball.getYVelocity(), 0.0f);
        float westBound = wall.getXCoordinate() + wall.getXSize();
        aball.setCoordinates(westBound, aball.getYCoordinate());
        Physics.bouncedWall(aball, wall);
        expected = westBound - aball.getRadius();
        xVelocity = -Math.abs(xVelocity);
        assertEquals(expected, aball.getXCoordinate(), 0.0f);
        assertEquals(xVelocity, aball.getXVelocity(), 0.0f);

    }

    @Test
    public void collisionShouldHappen() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        BallData b = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);

        Ball aball = new Ball(a);
        Ball bball = new Ball(b);
        assertEquals(true, Physics.checkCollision(aball, bball));
        bball.setCoordinates(0, 0);
        assertEquals(false, Physics.checkCollision(aball, bball));
    }

    @Test
    public void updateAttributesWhenCollisionHappened() {
        BallData a = new BallData("bob", false, 91.1f, 52.3f,
                ObjectState.MOVING, 12.1f, 3.0f);
        Ball aball = new Ball(a);
        PlayerData b = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.IDLE, 12.1f, 3.0f);
        Player player = new Player(b, false);
        Physics.collided(aball, player);
        b.setCoordinates(aball.getXCoordinate(), aball.getYCoordinate());
        Physics.collided(aball, player);
    }
    @Test
    public void playerShouldIncreaseshisSpeedWhenChargeApplies() {
        PlayerData b = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.IDLE, 0f, 3.0f);
        Player steve = new Player(b, false);
        steve.setChargeLevel(3);
        Physics.charge(steve);
        float expectedSpeed = GameSettings.getDefaultPlayerSpeed()*(1+3);
        assertEquals(expectedSpeed, steve.getSpeed(),0.0f);
        assertEquals(3,steve.getChargeLevel(),0);
    }
    
    @Test
    public void MovingCoordinationOfPlayersToSafeCoordinates(){
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        Ball aball = new Ball(a);
    }

    // TODO:

}