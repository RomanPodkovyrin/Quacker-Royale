package com.anotherworld.model.physics;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.PropertyReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Physics {
    private static Logger logger = LogManager.getLogger(Physics.class);
    private static final String FRICTION = "FRICTION";
    private static final String RATE = "ACCELERATE";
    private static final String FALLING = "FALLINGSPEED";
    private static final String FILE = "physics.properties";
    static float friction;
    static float rate;
    static float fallingSpeed;

    /**
     * This method is to setup the attributes of the Physics class such as
     * friction and rate
     */
    public static void setUp() {
        PropertyReader property;
        try {
            property = new PropertyReader(FILE);
            Physics.friction = Float.parseFloat(property.getValue(FRICTION));
            Physics.rate = Float.parseFloat(property.getValue(RATE));
            Physics.fallingSpeed = Float.parseFloat(property.getValue(FALLING));
        } catch (IOException exception) {
            logger.fatal("Cannot set up the properties of physics: "
                    + exception.getStackTrace());
        }
    }

    /**
     * To make the object move
     * 
     * @param object
     *            the object to move
     */
    public static void move(AbstractMovable object) {
        float newXCoordinate = object.getXCoordinate() + object.getXVelocity();
        float newYCoordinate = object.getYCoordinate() + object.getYVelocity();
        object.setCoordinates(newXCoordinate, newYCoordinate);
        logger.debug((object instanceof Player ? "Player "
                + ((Player) object).getCharacterID() : "Ball ")
                + "location updated successfully");
    }

    /**
     * To check collision of the objects.
     *
     * @param a
     *            the first object to check
     * @param b
     *            the second object to check
     */
    public static boolean checkCollision(AbstractMovable objectA,
            AbstractMovable objectB) {
        float xDistance = objectA.getXCoordinate() - objectB.getXCoordinate()
                - objectB.getXVelocity() + objectA.getYVelocity();
        float yDistance = objectA.getYCoordinate() - objectB.getYCoordinate()
                - objectB.getYVelocity() + objectA.getYVelocity();

        float sumOfRadii = objectA.getRadius() + objectB.getRadius();
        float distanceSquared = xDistance * xDistance + yDistance * yDistance;

        boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
        logger.debug((objectA instanceof Ball ? "Ball" : "Player "
                + ((Player) objectA).getCharacterID())
                + " and "
                + (objectB instanceof Ball ? "Ball" : "Player "
                        + ((Player) objectB).getCharacterID())
                + " are "
                + ((!isOverlapping) ? "not" : "") + " colliding");
        return isOverlapping;
    }

    /**
     * Check if the ball is colliding on the wall: If Y of the ball is colliding
     * Y of the wall (check if the value of north of the ball is lesser than
     * value of north of the wall, else check if the value of south of the ball
     * is greater than value of south of the wall) If X of the ball is colliding
     * X of the wall.
     * 
     * @param a
     *            the ball to check for collisions
     * @param wall
     *            the wall to check for collisions
     */
    public static boolean bouncedWall(Ball a, Wall wall) {
        float circleR = a.getRadius();
        float circleX = a.getXCoordinate();
        float circleY = a.getYCoordinate();
        float xSize = wall.getXSize();
        float ySize = wall.getYSize();
        boolean bounced = false;
        // North and East of the ball.
        Matrix northEast = new Matrix(circleX + circleR, circleY - circleR);
        // South and West of the ball.
        Matrix southWest = new Matrix(circleX - circleR, circleY + circleR);
        // North and East of the Wall.
        Matrix northEastWall = new Matrix((wall.getXCoordinate() + xSize),
                (wall.getYCoordinate() - ySize));
        // South and West of the Wall.
        Matrix southWestWall = new Matrix((wall.getXCoordinate() - xSize),
                (wall.getYCoordinate() + ySize));
        if (northEast.getY() < (northEastWall.getY())) {
            a.setCoordinates(circleX, northEastWall.getY() + circleR);
            a.setYVelocity(Math.abs(a.getYVelocity()));
            logger.debug("The ball is bouncing on the North of Wall");
            bounced = true;
        } else if (southWest.getY() > southWestWall.getY()) {
            a.setCoordinates(circleX, southWestWall.getY() - circleR);
            a.setYVelocity(-Math.abs(a.getYVelocity()));
            logger.debug("The ball is bouncing on the South of Wall");
            bounced = true;
        }
        if (northEast.getX() > northEastWall.getX()) {
            a.setCoordinates(northEast.getX() - circleR, circleY);
            a.setXVelocity(-Math.abs(a.getXVelocity()));
            logger.debug("The ball is bouncing on the East of Wall");
            bounced = true;
        } else if (southWest.getX() < southWestWall.getX()) {
            a.setCoordinates(southWest.getX() + circleR, circleY);
            a.setXVelocity(Math.abs(a.getXVelocity()));
            logger.debug("The ball is bouncing on the West of Wall");
            bounced = true;
        }

        return bounced;
    }

    //

    /**
     * To make the object move
     *
     * @param a
     *            the object to apply friction to
     */
    public static void applyFriction(AbstractMovable a) {
        float speed = a.getSpeed() * friction;
        if (speed < 0.5f) {
            speed = 0.0f;
            logger.debug("The object reached the minimum possible speed: Stopping now");
        }
        a.setSpeed(speed);
        logger.debug("The object has reduced its speed");
    }

    /**
     * To make the object accelerate
     *
     * @param a
     *            the object to apply acceleration to.
     */
    public static void accelerate(AbstractMovable a) {
        float speed = a.getSpeed() + rate;
        if (speed > 3.0f) {
            speed = 3.0f;
            logger.debug("The object has reached its maximum speed.");
        }
        a.setSpeed(speed);
        logger.debug("Object's speed is modified");
    }

    /**
     * To apply force to the object (reduce out strength or increase force)
     * 
     * @param a
     *            the object to which the force is applied
     * @param velocity
     *            the force matrix
     */
    public static void forceApplying(AbstractMovable a, Matrix velocity) {
        float xVelocity = a.getXVelocity() + velocity.getY();
        float yVelocity = a.getYVelocity() + velocity.getX();
        if (Math.abs(xVelocity) > 2.0) {
            xVelocity = 2.0f;
        }
        if (Math.abs(yVelocity) > 2.0) {
            yVelocity = 2.0f;
        }
        float angle = (float) Math.toDegrees(Math.atan2(xVelocity, yVelocity));
        if (angle < 0) {
            angle += 360;
        }
        float speed = (float) Math.sqrt(xVelocity * xVelocity + yVelocity
                * yVelocity);
        a.setAngle(angle);
        a.setSpeed(speed);
        a.setXVelocity(xVelocity);
        a.setYVelocity(yVelocity);
        logger.debug("Applied forced to the original velocity of the object.");
    }

    /**
     * Apply collision on an abstractMovables, and check for their instance.
     * 
     * @param objectA
     *            the first object in the collision
     * @param objectB
     *            the second object in the collision
     */
    public static void collided(AbstractMovable objectA, AbstractMovable objectB) {

        Matrix coordA = objectA.getCoordinates();
        Matrix coordB = objectB.getCoordinates();

        float xDifference = objectA.getXCoordinate() - objectB.getXCoordinate();
        float yDifference = objectA.getYCoordinate() - objectB.getYCoordinate();
        float distance = objectA.getRadius() + objectB.getRadius();
        if (objectA instanceof Ball) {
            Matrix angleFinding = coordA.sub(coordB);
            float angle = MatrixMath.vectorAngle(angleFinding);

            // objectA.setCoordinates(
            // objectA.getXCoordinate() - objectA.getXVelocity()
            // - objectB.getXVelocity(), objectA.getYCoordinate()
            // - objectA.getYVelocity() - objectB.getXVelocity());
            // objectB.setCoordinates(
            // objectB.getXCoordinate() + objectA.getXVelocity()
            // + objectB.getXVelocity(), objectB.getYCoordinate()
            // + objectA.getYVelocity() + objectB.getYVelocity());

            objectA.setVelocity((float) (objectA.getSpeed() * Math.sin(angle)),
                    (float) (objectA.getSpeed() * Math.cos(angle)));
            objectA.setAngle(angle);

            // if (objectB instanceof Ball) {
            // angleFinding = coordB.sub(coordA);
            // angle = MatrixMath.vectorAngle(angleFinding);
            // objectA.setVelocity((float) (objectA.getSpeed() *
            // Math.sin(angle)),
            // (float) (objectA.getSpeed() * Math.cos(angle)));
            // objectA.setAngle(angle);
            // }
        }
        if (xDifference < (distance) && xDifference < 0) {
            objectB.setCoordinates(coordB.getX() + objectB.getRadius() / 5,
                    coordB.getY());
        } else if (Math.abs(xDifference) < (distance)) {
            objectB.setCoordinates(coordB.getX() - objectB.getRadius() / 5,
                    coordB.getY());
        }
        if (yDifference < (distance) && yDifference < 0) {
            objectB.setCoordinates(coordB.getX(),
                    coordB.getY() + objectB.getRadius() / 5);
        } else if (Math.abs(yDifference) < (distance)) {
            objectB.setCoordinates(coordB.getX(),
                    coordB.getY() - objectB.getRadius() / 5);
        }

        logger.debug("Completed collision event between "
                + (objectA instanceof Ball ? "Ball" : "Player "
                        + ((Player) objectA).getCharacterID())
                + " and "
                + (objectB instanceof Ball ? "Ball" : "Player "
                        + ((Player) objectB).getCharacterID()));
    }

    /**
     * This method allows player to have a slow speed to the current direction
     * which looks like it is falling off the edge.
     * 
     * @param player
     */
    public static void falling(Player player) {
        float angle = player.getAngle();
        player.setVelocity((float) (fallingSpeed * Math.sin(angle)),
                (float) (fallingSpeed * Math.cos(angle)));
    }

    // public static void charge(Player player) {
    // int charge = player.getChargeLevel();
    // float speedIncreases = 1 + (1 / 5 * charge);
    // float speed = player.getSpeed() * speedIncreases;
    // float angle = player.getAngle();
    // player.setVelocity((float)(speed*Math.sin(angle)),(float)(speed*Math.cos(angle)));
    // player.setChargeLevel(charge>0?charge-1:charge);
    // }
}
