package com.anotherworld.model.physics;

import java.util.ArrayList;
import java.util.List;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

public class Physics {

    static float friction;
    static float rate;
    static float minimumSpeed = 0.4f;
    static float maximumSpeed;

    public Physics(float rate, float friction, float maximumSpeed) {
        Physics.friction = friction;
        Physics.rate = rate;
    }

    /**
     * To make the object move
     * 
     * @param AbstractMovable
     *            object
     */
    public static void move(AbstractMovable object) {
        float newXCoordinate = object.getXCoordinate() + object.getXVelocity();
        float newYCoordinate = object.getYCoordinate() + object.getYVelocity();
        object.setCoordinates(newXCoordinate, newYCoordinate);
    }

    /**
     * To check collision of the objects.
     *
     * @param AbstractMovable
     *            a, b
     */
    public static boolean checkCollision(AbstractMovable a, AbstractMovable b) {
        float xDistance = a.getXCoordinate() - b.getXCoordinate();
        float yDistance = a.getYCoordinate() - b.getYCoordinate();

        float sumOfRadii = a.getRadius() + b.getRadius();
        float distanceSquared = xDistance * xDistance + yDistance * yDistance;

        boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
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
     * @param wall
     */
    public static void bouncedWall(Ball a, Wall wall) {
        float circleR = a.getRadius();
        float circleX = a.getXCoordinate();
        float circleY = a.getYCoordinate();
        Matrix wallCoord = wall.getCoordinate();
        float xSize = wall.getXSize();
        float ySize = wall.getYSize();
        // North and East of the ball.
        Matrix northEast = new Matrix(circleY - circleR, circleX + circleR);
        // South and West of the ball.
        Matrix southWest = new Matrix(circleY + circleR, circleX - circleR);
        // North and East of the Wall.
        Matrix northEastWall = new Matrix((wallCoord.getX() + xSize),
                (wallCoord.getY() - ySize));
        // South and West of the Wall.
        Matrix southWestWall = new Matrix((wallCoord.getX() - xSize),
                (wallCoord.getY() + ySize));

        if (northEast.getY() < (northEastWall.getY())) {
            a.setCoordinates(circleX, northEastWall.getY() + circleR);
            a.setYVelocity(-a.getYVelocity());
        } else if (southWest.getY() > southWestWall.getY()) {
            a.setCoordinates(circleX, southWestWall.getY() - circleR);
            a.setYVelocity(-a.getYVelocity());
        }
        if (northEast.getX() > northEastWall.getX()) {
            a.setCoordinates(northEast.getX() - circleR, circleY);
            a.setXVelocity(-a.getXVelocity());
        } else if (southWest.getX() < southWestWall.getX()) {
            a.setCoordinates(southWest.getX() + circleR, circleY);
            a.setXVelocity(-a.getXVelocity());
        }
    }

    //

    /**
     * To make the object move
     *
     * @param AbstractMovable
     *            object
     */
    public static void applyFriction(AbstractMovable a) {
        float speed = a.getSpeed() * friction;
        if (speed < 0.5f) {
            speed = 0.0f;
        }
        a.setSpeed(speed);
    }

    /**
     * To make the object accelerate
     *
     * @param AbstractMovable
     *            object
     */
    public static void accelerate(AbstractMovable a) {
        float speed = a.getSpeed() + rate;
        if (speed > 3.0f) {
            speed = 3.0f;
        }
        a.setSpeed(speed);
    }

    /**
     * To apply force to the object (reduce out strength or increase force)
     * 
     * @param AbstractMovable
     *            object
     */
    public static void forceApplying(AbstractMovable a, Matrix velocity) {
        float xVelocity = a.getXVelocity() + velocity.getY();
        float yVelocity = a.getYVelocity() + velocity.getX();
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
    }

    /**
     * When two objects collided with each other
     * 
     * @param Player
     * @param Martix
     */
    public static void collided(AbstractMovable player, AbstractMovable player2) {
        player.setXVelocity(player2.getXVelocity());
        player.setYVelocity(player2.getYVelocity());
        player2.setXVelocity(player.getXVelocity());
        player2.setYVelocity(player.getYVelocity());
    }

    /**
     * Apply collision on both ball and player Check if the ball can damage
     * people then decreases player health by 30 else then toggle the ball to
     * harmful state.
     * 
     * @param player
     * @param ball
     */
    public static void collidedByBall(Player player, Ball ball) {
        player.setXVelocity(ball.getXVelocity());
        player.setYVelocity(ball.getYVelocity());
        if (ball.isDangerous()) {
            int health = player.getHealth();
            player.setHealth(health - 30);
        } else {
            ball.setDangerous(true);
        }
        float xDifference = ball.getXCoordinate() - player.getXCoordinate();
        float yDifference = ball.getYCoordinate() - player.getYCoordinate();
        if (xDifference > (ball.getRadius() + player.getRadius())) {
            ball.setXVelocity(-ball.getXVelocity());
        }
        if (yDifference > (ball.getRadius() + player.getRadius())) {
            ball.setYVelocity(-ball.getYVelocity());
        }
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
            List<Player> listOfPlayers, Wall wall, Platform platform) {
        List<Integer> collided = new ArrayList<Integer>();

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
                    collidedByBall(victim, ball);
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

            if (!platform.isOnPlatform(player)) {

            }
            for (int j = i + 1; i < listOfPlayers.size(); j++) {
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
}
