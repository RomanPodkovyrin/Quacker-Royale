package com.anotherworld.model.physics;

import java.util.ArrayList;
import java.util.List;

import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

public class Physics {
    public float[] calculateXYVelocity(float speed, float angle) {
        float[] xyVelocity = new float[2];
        xyVelocity[0] = (float) (speed * Math.sin(angle));
        xyVelocity[1] = (float) (speed * Math.cos(angle));
        return xyVelocity;
    }

    public float[] calculateSpeedAngle(float xVelocity, float yVelocity) {
        float[] speedAngle = new float[2];
        speedAngle[0] = Math.abs(yVelocity * yVelocity + xVelocity * xVelocity);
        speedAngle[1] = (float) Math.tanh(xVelocity / yVelocity);
        return speedAngle;
    }

    public float[] move(float xVelocity, float yVelocity, float xCoordinate,
            float yCoordinate) {
        float[] coordinates = { xCoordinate + xVelocity, yCoordinate + yVelocity };
        return coordinates;
    }

    public boolean checkCollision(AbstractMovable a, AbstractMovable b) {
        float xDistance = a.getXCoordinate() - b.getXCoordinate();
        float yDistance = a.getYCoordinate() - b.getYCoordinate();

        float sumOfRadii = a.getRadius() + b.getRadius();
        float distanceSquared = xDistance * xDistance + yDistance * yDistance;

        boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
        return isOverlapping;
    }

    public boolean bouncedWall(Ball a, float[] wallCoordinate) {
        float circleR = a.getRadius();
        float circleX = a.getXCoordinate();
        float circleY = a.getYCoordinate();
        float deltaX = circleX
                - Math.max(wallCoordinate[3],
                        Math.min(circleX, wallCoordinate[1]));
        float deltaY = circleY
                - Math.max(wallCoordinate[0],
                        Math.min(circleY, wallCoordinate[2]));
        return !((deltaX * deltaX + deltaY * deltaY) < (circleR * circleR));
    }

    public void applyFriction(AbstractMovable a) {
        float friction = 0.90f;
        float speed = a.getSpeed() * friction;
        if (speed < 0.5f) {
            speed = 0.0f;
        }
        a.setSpeed(speed);
    }

    public void accelerate(AbstractMovable a) {
        float rate = 0.5f;
        float speed = a.getSpeed() + rate;
        if (speed > 3.0f) {
            speed = 3.0f;
        }
        a.setSpeed(speed);
    }

    public void forceCancelling(AbstractMovable a, float newSpeed,
            float newAngle) {
        float[] xyVelocity = calculateXYVelocity(newSpeed, newAngle);
        float xVelocity = a.getXVelocity();
        float yVelocity = a.getYVelocity();
        a.setXVelocity(xVelocity + xyVelocity[0]);
        a.setYVelocity(yVelocity + xyVelocity[1]);
    }

    public void collided(Ball ball) {
        float velocityX = ball.getXVelocity();
        ball.setYVelocity(velocityX * -1);
    }

    public void collided(Player player, float[] outsideVelocity) {
        player.setXVelocity(outsideVelocity[0]);
        player.setYVelocity(outsideVelocity[1]);
    }

    public void collidedByBall(Player player, Ball ball) {
        player.setXVelocity(ball.getXVelocity());
        player.setYVelocity(ball.getYVelocity());
        if (ball.isDangerous()) {
            int health = player.getHealth();
            player.setHealth(health - 30);
        }
    }

    public void onCollision(List<Ball> listOfBalls, List<Player> listOfPlayers, float[] wallDimensions) {
        // check for ball collided, then player collided.
        // The list of integer collided is for storing the index of player
        // To make sure the event has already occurred on them during this
        // method is called.
        List<Integer> collided = new ArrayList<Integer>();
        for (int i = 0; i < listOfBalls.size(); i++) {
            Ball ball = listOfBalls.get(i);
            if (bouncedWall(ball, wallDimensions)) {
                collided(ball);
                continue;
            }
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
        }
        // Check for player collided,
        // if the player was collided with another player, the force will be
        // applied by each other
        // but no damage will be taken.
        for (int i = 0; i < listOfPlayers.size(); i++) {
            if (collided.contains(i)) {
                continue;
            }
            Player player = listOfPlayers.get(i);
            for (int j = i + 1; i < listOfPlayers.size(); j++) {
                if (collided.contains(j)) {
                    continue;
                }
                Player player2 = listOfPlayers.get(j);
                if (checkCollision(player, player2)) {
                    float[] veloToPlayer = { player2.getXVelocity(),
                            player2.getYVelocity() };
                    float[] veloToPlayer2 = { player.getXVelocity(),
                            player.getYVelocity() };
                    collided(player, veloToPlayer);
                    collided(player2, veloToPlayer2);
                    collided.add(i);
                    collided.add(j);
                }
            }
        }
    }
}
