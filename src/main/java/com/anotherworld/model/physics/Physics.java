package com.anotherworld.model.physics;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.PropertyReader;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Physics {
    private static Logger logger = LogManager.getLogger(Physics.class);
    private static final String FRICTION = "FRICTION";
    private static final String RATE = "ACCELERATE";
    private static final String FALLING = "FALLINGSPEED";
    private static final String FILE = "physics.properties";
    private static final float CHARGE_RATE = 1.5f;
    private static final int FIRST = 0, SECOND = 1, TWO = 2;
    static float friction;
    static float rate;
    static float fallingSpeed;

    /**
     * This method is to setup the attributes of the Physics class such as
     * friction and rate.
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
     * To make the object move. And the way object move is by adding up the product of 
     * speed and velocity of the object on its original coordination.
     * 
     * @param object
     *            the object to move
     */
    public static void move(AbstractMovable object) {
        float newXCoordinate = object.getXCoordinate() + object.getXVelocity()
                * object.getSpeed();
        float newYCoordinate = object.getYCoordinate() + object.getYVelocity()
                * object.getSpeed();
        object.setCoordinates(newXCoordinate, newYCoordinate);
        logger.debug((object instanceof Player ? "Player "
                + ((Player) object).getCharacterID() : "Ball ")
                + "location updated successfully");
    }

    /** 
     * To check for collision between a player and coordinate of an item as well as its radius.
     * 
     * @param object
     *          The player who is used in checking for collision
     *          
     * @param coordinates
     *          The coordinate of the item
     *          
     * @param radius
     *          The radius of the item.
     *         
     * @return boolean
     *          Indicates if the object is collided with the item. 
     */
    
    public static boolean checkCollision(Player object, Matrix coordinates, float radius) {
        float xDistance = (object.getXCoordinate() - coordinates.getX());
        float yDistance = (object.getYCoordinate() - coordinates.getY());

        float sumOfRadii = object.getRadius() + radius;
        float distanceSquared =xDistance * xDistance + yDistance * yDistance;

        boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
        
        return isOverlapping;
    }
    /**
     * To check collision of the objects.
     *
     * @param objectA
     *            the first object to check
     *            
     * @param objectB
     *            the second object to check
     */
    
    public static boolean checkCollision(AbstractMovable objectA, AbstractMovable objectB) {
        float xDistance = (objectA.getXCoordinate() - objectB.getXCoordinate());
        float yDistance = (objectA.getYCoordinate() - objectB.getYCoordinate());

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
     * @param ball
     *            the ball to check for collisions
     *            
     * @param wall
     *            the wall to check for collisions
     */
    public static boolean bouncedWall(Ball ball, Wall wall) {
        float circleR = ball.getRadius();
        float circleX = ball.getXCoordinate();
        float circleY = ball.getYCoordinate();
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
            ball.setCoordinates(circleX, northEastWall.getY() + circleR);
            ball.setYVelocity(Math.abs(ball.getYVelocity()));
            logger.debug("The ball is bouncing on the North of Wall");
            bounced = true;
        } else if (southWest.getY() > southWestWall.getY()) {
            ball.setCoordinates(circleX, southWestWall.getY() - circleR);
            ball.setYVelocity(-Math.abs(ball.getYVelocity()));
            logger.debug("The ball is bouncing on the South of Wall");
            bounced = true;
        }
        if (northEast.getX() > northEastWall.getX()) {
            ball.setCoordinates(northEastWall.getX() - circleR, circleY);
            ball.setXVelocity(-Math.abs(ball.getXVelocity()));
            logger.debug("The ball is bouncing on the East of Wall");
            bounced = true;
        } else if (southWest.getX() < southWestWall.getX()) {
            ball.setCoordinates(southWestWall.getX() + circleR, circleY);
            ball.setXVelocity(Math.abs(ball.getXVelocity()));
            logger.debug("The ball is bouncing on the West of Wall");
            bounced = true;
        }

        return bounced;
    }

    /**
     * To calculate safe coordinates between objectA and objectB to prevent
     * overlapping after collision happened.
     * 
     * @param objectA
     *            An object which is collided.
     *            
     * @param objectB
     *            An object which is collided.
     */
    public static ArrayList<Matrix> calculateCollision(AbstractMovable objectA,
            AbstractMovable objectB) {
        Matrix pointA = new Matrix(objectA.getXCoordinate()
                + (objectA.getXVelocity() * objectA.getSpeed()),
                objectA.getYCoordinate()
                        + (objectA.getYVelocity() * objectA.getSpeed()));
        Matrix pointB = new Matrix(objectB.getXCoordinate()
                + (objectB.getXVelocity() * objectB.getSpeed()),
                objectB.getYCoordinate()
                        + (objectB.getYVelocity() * objectB.getSpeed()));
        double angleBetweenCircles = Math.atan2(pointB.getY() - pointA.getY(),
                pointB.getX() - pointA.getX());

        float radiusA = objectA.getRadius();
        float radiusB = objectB.getRadius();

        Matrix midpointBetweenCircles = new Matrix(
                (pointA.getX() + pointB.getX()) / TWO, 
                (pointA.getY() + pointB.getY()) / TWO);

        Matrix objectAOffSet = new Matrix((float) (radiusA * Math.cos(Math.PI
                + angleBetweenCircles)), (float) (radiusA * Math.sin(Math.PI
                + angleBetweenCircles)));
        Matrix objectBOffSet = new Matrix(
                (float) (radiusB * Math.cos(angleBetweenCircles)),
                (float) (radiusB * Math.sin(angleBetweenCircles)));

        ArrayList<Matrix> safeCoordinateAb = new ArrayList<>();
        safeCoordinateAb.add(new Matrix(midpointBetweenCircles.getX()
                + objectAOffSet.getX(), midpointBetweenCircles.getY()
                + objectAOffSet.getY()));
        safeCoordinateAb.add(new Matrix(midpointBetweenCircles.getX()
                + objectBOffSet.getX(), midpointBetweenCircles.getY()
                + objectBOffSet.getY()));

        return safeCoordinateAb;
    }

    /**
     * Apply collision on an abstractMovables, and check for their instance. If
     * objectA is ball, change the velocity of it, if objectB is ball, change
     * the velocity of it. If one of the object is player and he is dashing,
     * repel the opponent Object and setVelocity on it.
     * 
     * @param objectA
     *            the first object in the collision
     *            
     * @param objectB
     *            the second object in the collision
     */
    public static void collided(AbstractMovable objectA, AbstractMovable objectB) {

        Matrix coordA = objectA.getCoordinates();
        Matrix coordB = objectB.getCoordinates();
        Matrix n = coordA.sub(coordB);
        n.normalizeThis();

        float newVeloA = MatrixMath.innerProduct(objectA.getVelocity(), n);
        float newVeloB = MatrixMath.innerProduct(objectB.getVelocity(), n);
        float optimisedP = Math.min((newVeloA - newVeloB), 0);
        Matrix veloA = new Matrix(objectA.getXVelocity()
                - (optimisedP * n.getX()), objectA.getYVelocity()
                - (optimisedP * n.getY()));
        Matrix veloB = new Matrix(objectB.getXVelocity()
                + (optimisedP * n.getX()), objectB.getYVelocity()
                + (optimisedP * n.getY()));

        veloA.normalizeThis();
        veloB.normalizeThis();
        if (objectA instanceof Ball) {

            objectA.setVelocity(veloA.getX(), veloA.getY());
            objectA.setAngle((float) MatrixMath.vectorAngle(veloA));

            if (objectB instanceof Ball) {
                objectB.setVelocity(veloB.getX(), veloB.getY());
                objectB.setAngle((float) MatrixMath.vectorAngle(veloB));
            }
        }
        ArrayList<Matrix> newCoordinate = calculateCollision(objectA, objectB);
        Matrix safe;
        boolean specialCase = false;
        if (objectA instanceof Player) {
            if (((Player) objectA).getState().equals(ObjectState.DASHING)) {
                {
                    objectB.setVelocity(veloB.getX(), veloB.getY());
                    objectB.setAngle((float) MatrixMath.vectorAngle(veloB));
                    safe = newCoordinate.get(SECOND);
                    objectB.setCoordinates(safe.getX(), safe.getY());
                    if (!(objectB instanceof Ball)) {
                        specialCase = true;
                    }
                }
            }
        } else if (objectB instanceof Player) {
            if (((Player) objectB).getState().equals(ObjectState.DASHING)) {
                {
                    objectA.setVelocity(veloA.getX(), veloA.getY());
                    objectA.setAngle((float) MatrixMath.vectorAngle(veloA));
                    safe = newCoordinate.get(FIRST);
                    objectA.setCoordinates(safe.getX(), safe.getY());
                    if (!(objectA instanceof Ball)) {
                        specialCase = true;
                    }
                }
            }
        }
        if (!specialCase) {
            safe = newCoordinate.get(FIRST);
            objectA.setCoordinates(safe.getX(), safe.getY());
            safe = newCoordinate.get(SECOND);
            objectB.setCoordinates(safe.getX(), safe.getY());
        }
        logger.debug("Completed collision event between "
                + (objectA instanceof Ball ? "Ball" : "Player "
                        + ((Player) objectA).getCharacterID())
                + " and "
                + (objectB instanceof Ball ? "Ball" : "Player "
                        + ((Player) objectB).getCharacterID()));
    }

    /**
     * This method allows player to increase his speed depends on the internal
     * value of himself (charge level) And it add into 1 (1 is to make sure the
     * player still have the speed without any charge) and multiply the
     * defaultPlayerSpeed by the amplifying value. Finally, set speed of the
     * player by the result of the multiplication.
     * 
     * @param player
     *            The player who is about to dash.
     */
    public static void charge(Player player) {
        int charge = player.getChargeLevel();
        float speedIncreases = 1 + (CHARGE_RATE * charge);

        float speed = GameSettings.getDefaultPlayerSpeed() * speedIncreases;

        player.setSpeed(speed);
    }
}
