package com.anotherworld.model.physics;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
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
    private static final String FILE = "physics.properties";
    private static final String FALLING = "FALLINGSPEED";
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
        logger.debug("Object's location updated successfully");
    }

    /**
     * To check collision of the objects.
     *
     * @param a
     *            the first object to check
     * @param b
     *            the second object to check
     */
    public static boolean checkCollision(AbstractMovable a, AbstractMovable b) {
        float xDistance = a.getXCoordinate() - b.getXCoordinate();
        float yDistance = a.getYCoordinate() - b.getYCoordinate();

        float sumOfRadii = a.getRadius() + b.getRadius();
        float distanceSquared = xDistance * xDistance + yDistance * yDistance;

        boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
        logger.debug("Checked if the two objects are colliding");
        return isOverlapping;
    }

    /**
     * Check if the ball is colliding on the wall: If Y of the ball is colliding
     * Y of the wall (check if the value of north of the ball is lesser than
     * value of north of the wall, else check if the value of south of the ball
     * is greater than value of south of the wall)
     * 
     * If X of the ball is colliding X of the wall.
     * 
     * @param a
     *            the ball to check for collisions
     * @param wall
     *            the wall to check for collisions
     */
    public static void bouncedWall(Ball a, Wall wall) {
        float circleR = a.getRadius();
        float circleX = a.getXCoordinate();
        float circleY = a.getYCoordinate();
        float xSize = wall.getXSize();
        float ySize = wall.getYSize();
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
            a.setYVelocity(-a.getYVelocity());
            logger.debug("The ball is bouncing on the North of Wall");
        } else if (southWest.getY() > southWestWall.getY()) {
            a.setCoordinates(circleX, southWestWall.getY() - circleR);
            a.setYVelocity(-a.getYVelocity());
            logger.debug("The ball is bouncing on the South of Wall");
        }
        if (northEast.getX() > northEastWall.getX()) {
            a.setCoordinates(northEast.getX() - circleR, circleY);
            a.setXVelocity(-a.getXVelocity());
            logger.debug("The ball is bouncing on the East of Wall");
        } else if (southWest.getX() < southWestWall.getX()) {
            a.setCoordinates(southWest.getX() + circleR, circleY);
            a.setXVelocity(-a.getXVelocity());
            logger.debug("The ball is bouncing on the West of Wall");
        }
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
            if (objectB instanceof Player) {
               
            }
            Matrix angleFinding = coordA.sub(coordB);
            float angle = MatrixMath.vectorAngle(angleFinding);
            objectA.setVelocity((float) (objectA.getSpeed() * Math.sin(angle)),
                    (float) (objectA.getSpeed() * Math.cos(angle)));

            if (objectB instanceof Ball) {
                angleFinding = coordB.sub(coordA);
                angle = MatrixMath.vectorAngle(angleFinding);
                objectB.setVelocity(
                        (float) (objectB.getSpeed() * Math.sin(angle)),
                        (float) (objectB.getSpeed() * Math.cos(angle)));
            }
        }
        if (xDifference < (distance) && xDifference < 0) {
            objectB.setCoordinates(coordB.getX() + objectA.getRadius() / 10,
                    coordB.getY());
        } else if (Math.abs(xDifference) < (distance)) {
            objectB.setCoordinates(coordB.getX() - objectA.getRadius() / 10,
                    coordB.getY());
        }
        if (yDifference < (distance) && yDifference < 0) {
            objectB.setCoordinates(coordB.getX(),
                    coordB.getY() + objectA.getRadius() / 10);
        } else if (Math.abs(yDifference) < (distance)) {
            objectB.setCoordinates(coordB.getX(),
                    coordB.getY() - objectA.getRadius() / 10);
        }
        logger.debug((objectA instanceof Ball?"Ball":"Player")+" collided with"+(objectB instanceof Ball?"Ball":"Player"));
    }

    public static void falling(Player player) {
        float angle = player.getAngle();
        player.setVelocity((float) (fallingSpeed * Math.sin(angle)),
                (float) (fallingSpeed * Math.cos(angle)));
    }

    /**
     * Check every items in the game if they have collision First: Ball: check
     * with the very first ball and: Check if it collided with a wall check if
     * it collided with one of the player check if it collided with anotherball
     * (if they collided, assign id of the ball into the value From second ball
     * onward, check if it matches the index of the ball. Then Player: Check if
     * the player is collided Check if the player is collided with another
     * player check if the player is collided with pitfall
     *
     * @param listOfBalls
     * @param listOfPlayers
     * @param wall
     */
    public static void onCollision(List<Ball> listOfBalls,
            List<Player> listOfPlayers, Wall wall) {
        List<Integer> collided = new ArrayList<>();

        int collidedBall = -1;
        for (int i = 0; i < listOfBalls.size(); i++) {
            if (collidedBall == i) {
                continue;
            }
            Ball ball = listOfBalls.get(i);
            bouncedWall(ball, wall);
            for (int j = 0; j < listOfPlayers.size(); j++) {
                if (collided.contains(j)) {
                    continue;
                }
                Player victim = listOfPlayers.get(i);
                if (checkCollision(ball, victim)) {
                    logger.debug("ball collided with player");
                    collided(ball, victim);
                    collided.add(j);
                }
            }
            for (int k = i + 1; k < listOfBalls.size(); k++) {
                if (checkCollision(ball, listOfBalls.get(k))) {
                    collided(ball, listOfBalls.get(k));
                    collidedBall = k;
                }
            }
        }

        for (int i = 0; i < listOfPlayers.size(); i++) {

            if (collided.contains(i)) {
                continue;
            }
            Player player = listOfPlayers.get(i);

            for (int j = i + 1; j < listOfPlayers.size(); j++) {
                if (collided.contains(j)) {
                    continue;
                }
                Player player2 = listOfPlayers.get(j);
                if (checkCollision(player, player2)) {
                    collided(player, player2);
                }

            }
        }
    }

    /**
     * Function that checks and applies all the collisions within the game.
     * First checks each ball for a collisions with: (i) a wall. (ii) a player.
     * (iii) another ball. Then checks a player for collisions with: (i) another
     * player. (ii) outside of the platform.
     * 
     * @param listOfBalls
     * @param listOfPlayers
     * @param wall
     */
    public static void onCollision2ElectricBoogaloo(List<Ball> listOfBalls,
            List<Player> listOfPlayers, Wall wall) {

        for (Ball ball : listOfBalls) {

            // Check if a ball has collided with the wall.
            bouncedWall(ball, wall);

            // Check if a ball has collided with a player.
            for (Player player : listOfPlayers) {
                if (checkCollision(ball, player)) {
                    collided(ball, player);
                }
            }

            // Check if a ball has collided with another ball.
            for (Ball ballB : listOfBalls) {
                if (!ball.equals(ballB) && checkCollision(ball, ballB)) {
                    collided(ball, ballB);
                }
            }
        }

        // Check if a player has collided with another player.
        for (Player playerA : listOfPlayers) {
            for (Player playerB : listOfPlayers) {
                if (!playerA.equals(playerB)
                        && checkCollision(playerA, playerB)) {
                    collided(playerA, playerB);
                }
            }
        }
    }
}
