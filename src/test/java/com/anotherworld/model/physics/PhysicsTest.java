package com.anotherworld.model.physics;

import static org.junit.Assert.assertEquals;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;

import java.util.ArrayList;

import org.junit.Test;

public class PhysicsTest {

    @Test
    public void theCoordinateOfAnobjectShouldUpdate() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);

        Matrix coord = a.getCoordinates();
        Physics.move(a);
        assertEquals(coord.getX(), a.getXCoordinate(), 0.0f);
        assertEquals(coord.getY(), a.getYCoordinate(), 0.0f);

        a.setXVelocity(0.5f);
        Physics.move(a);
        float xCoord = (a.getXVelocity() * a.getSpeed()) + coord.getX();
        // assert statements
        assertEquals(xCoord, a.getXCoordinate(), 0.0f);
        a.setVelocity(0.0f, 0.5f);
        float yCoord = a.getYCoordinate();
        Physics.move(a);
        yCoord += (a.getYVelocity() * a.getSpeed());
        assertEquals(yCoord, a.getYCoordinate(), 0.0f);

        a.setVelocity(-0.5f, -0.5f);
        Physics.move(a);
        xCoord += a.getXVelocity() * a.getSpeed();
        yCoord += a.getYVelocity() * a.getSpeed();
        assertEquals(xCoord, a.getXCoordinate(), 0.0f);
        assertEquals(yCoord, a.getYCoordinate(), 0.0f);
    }

    @Test
    public void theBallShouldChangeTheVelocityWhenItHitWalls() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        float yVelocity = 1.0f;
        float xVelocity = 2.0f;
        a.setYVelocity(yVelocity);
        a.setXVelocity(xVelocity);
        WallData wallb = new WallData(45.0f, 45.0f);
        Wall wall = new Wall(wallb);
        float northBound = wall.getYCoordinate() - wall.getYSize();
        a.setCoordinates(a.getXCoordinate(), northBound);
        Physics.bouncedWall(a, wall);
        float expected = northBound + a.getRadius();
        yVelocity = Math.abs(yVelocity);
        assertEquals(expected, a.getYCoordinate(), 0.0f);
        assertEquals(yVelocity, a.getYVelocity(), 0.0f);
        float eastBound = wall.getXCoordinate() - wall.getXSize();
        a.setCoordinates(eastBound, a.getYCoordinate());
        Physics.bouncedWall(a, wall);
        expected = eastBound + a.getRadius();
        xVelocity = Math.abs(xVelocity);
        assertEquals(expected, a.getXCoordinate(), 0.0f);
        assertEquals(xVelocity, a.getXVelocity(), 0.0f);
        float southBound = wall.getYCoordinate() + wall.getYSize();
        a.setCoordinates(a.getXCoordinate(), southBound);
        Physics.bouncedWall(a, wall);
        expected = southBound - a.getRadius();
        yVelocity = -Math.abs(yVelocity);
        assertEquals(expected, a.getYCoordinate(), 0.0f);
        assertEquals(yVelocity, a.getYVelocity(), 0.0f);
        float westBound = wall.getXCoordinate() + wall.getXSize();
        a.setCoordinates(westBound, a.getYCoordinate());
        Physics.bouncedWall(a, wall);
        expected = westBound - a.getRadius();
        xVelocity = -Math.abs(xVelocity);
        assertEquals(expected, a.getXCoordinate(), 0.0f);
        assertEquals(xVelocity, a.getXVelocity(), 0.0f);
    }

    @Test
    public void collisionShouldHappen() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);
        BallData b = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);

        assertEquals(true, Physics.checkCollision(a, b));
        b.setCoordinates(0, 0);
        assertEquals(false, Physics.checkCollision(a, b));
    }

    @Test
    public void updateAttributesWhenCollisionHappened() {
        BallData a = new BallData("bob", false, 91.1f, 52.3f,
                ObjectState.MOVING, 12.1f, 3.0f);
        PlayerData b = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.IDLE, 12.1f, 3.0f);
        Physics.collided(a, b);
        b.setCoordinates(a.getXCoordinate(), a.getYCoordinate());
        Physics.collided(a, b);
    }

    @Test
    public void playerShouldIncreaseshisSpeedWhenChargeApplies() {
        PlayerData steve = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.IDLE, 0f, 3.0f);
        steve.setChargeLevel(3);
        Physics.charge(steve);
        float expectedSpeed = GameSettings.getDefaultPlayerSpeed() * (1 + 3 * 1.5f);
        assertEquals(expectedSpeed, steve.getSpeed(), 0.0f);
        assertEquals(3, steve.getChargeLevel(), 0);
    }

    @Test
    public void movingCoordinationOfPlayersToSafeCoordinates() {
        BallData a = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);


        PlayerData steve = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.IDLE, 0f, 3.0f);

        PlayerData steven = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.DASHING, 0f, 3.0f);

        BallData b = new BallData(null, false, 91.1f, 52.3f, null, 12.1f, 3.0f);

        // ball VS ball.
        ArrayList<Matrix> expected = Physics.calculateCollision(a, b);
        ArrayList<Matrix> originalVelo = new ArrayList<Matrix>();
        assertEquals(true,Physics.checkCollision(a, b));
        Physics.collided(a, b);
        assertEquals(expected.get(0).getX(), a.getXCoordinate(), 0.0f);
        assertEquals(expected.get(0).getY(), a.getYCoordinate(), 0.0f);
        assertEquals(expected.get(1).getX(), b.getXCoordinate(), 0.0f);
        assertEquals(expected.get(1).getY(), b.getYCoordinate(), 0.0f);
        
        // ball VS player.
        steve.setCoordinates(a.getXCoordinate(), a.getYCoordinate());
        expected = Physics.calculateCollision(a, steve);
        assertEquals(true,Physics.checkCollision(a, steve));
        Physics.collided(a, steve);
        assertEquals(expected.get(0).getX(), a.getXCoordinate(), 0.0f);
        assertEquals(expected.get(0).getY(), a.getYCoordinate(), 0.0f);
        assertEquals(expected.get(1).getX(), steve.getXCoordinate(), 0.0f);
        assertEquals(expected.get(1).getY(), steve.getYCoordinate(), 0.0f);
        // player VS ball.
        steve.setCoordinates(a.getXCoordinate(), a.getYCoordinate());
        expected = Physics.calculateCollision(steve, a);
        assertEquals(true,Physics.checkCollision(steve, a));
        Physics.collided(steve, a);
        assertEquals(expected.get(0).getX(), steve.getXCoordinate(), 0.0f);
        assertEquals(expected.get(0).getY(), steve.getYCoordinate(), 0.0f);
        assertEquals(expected.get(1).getX(), a.getXCoordinate(), 0.0f);
        assertEquals(expected.get(1).getY(), a.getYCoordinate(), 0.0f);
        // ball VS dashing player

        PlayerData c = new PlayerData("Steve", 90, 91.1f, 52.3f,
                ObjectState.DASHING, 0f, 3.0f);

        steven.setCoordinates(a.getXCoordinate(), a.getYCoordinate());
        expected = Physics.calculateCollision(a, steven);
        assertEquals(true,Physics.checkCollision(a, steven));
        Physics.collided(a, steven);
        assertEquals(expected.get(0).getX(), a.getXCoordinate(), 0.0f);
        assertEquals(expected.get(0).getY(), a.getYCoordinate(), 0.0f);
        assertEquals(expected.get(1).getX(), steven.getXCoordinate(), 0.0f);
        assertEquals(expected.get(1).getY(), steven.getYCoordinate(), 0.0f);
        // Dashing player VS ball
        steven.setCoordinates(a.getXCoordinate(), a.getYCoordinate());
        expected = Physics.calculateCollision(steven, a);
        assertEquals(true,Physics.checkCollision(steven, a));
        Physics.collided(steven, a);
        assertEquals(expected.get(0).getX(), steven.getXCoordinate(), 0.0f);
        assertEquals(expected.get(0).getY(), steven.getYCoordinate(), 0.0f);
        assertEquals(expected.get(1).getX(), a.getXCoordinate(), 0.0f);
        assertEquals(expected.get(1).getY(), a.getYCoordinate(), 0.0f);

    }
}