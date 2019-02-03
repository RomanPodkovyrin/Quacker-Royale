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
		float[] coordinates = { xCoordinate + xVelocity,
				yCoordinate + yVelocity };
		return coordinates;
	}

	public boolean checkCollision(AbstractMovable a, AbstractMovable b) {
		float xDistance = a.getxCoordinate() - b.getxCoordinate();
		float yDistance = a.getyCoordinate() - b.getyCoordinate();

		float sumOfRadii = a.getRadious() + b.getRadious();
		float distanceSquared = xDistance * xDistance + yDistance * yDistance;

		boolean isOverlapping = distanceSquared < sumOfRadii * sumOfRadii;
		return isOverlapping;
	}

	public boolean bouncedWall(Ball a, float[] wallCoordinate) {
		float circleR = a.getRadious();
		float circleX = a.getxCoordinate();
		float circleY = a.getyCoordinate();
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
		if (speed < 0.5f)
			speed = 0.0f;
		a.setSpeed(speed);
	}

	public void accelerate(AbstractMovable a) {
		float rate = 0.5f;
		float speed = a.getSpeed() + rate;
		if (speed > 3.0f)
			speed = 3.0f;
		a.setSpeed(speed);
	}
	
	public void forceCancelling(AbstractMovable a, float newSpeed, float newAngle) {
		float[] xyVelocity = calculateXYVelocity(newSpeed, newAngle);
		float xVelocity = a.getxVelocity();
		float yVelocity = a.getyVelocity();
		a.setxVelocity(xVelocity+xyVelocity[0]);
		a.setyVelocity(yVelocity+xyVelocity[1]);
	}

	public void collided(List<AbstractMovable> twoObjects) {
		
	}
	
	public void onCollision(List<AbstractMovable> listOfObjects) {
		/*
		 * TODO:
		 * For general:
		 * Get the closest object to this AbstractMovable
		 * 1: Player
		 * Get the ID and size of the object
		 * then apply function depends on the ID of the abstractMovable:
		 * * There should be 2 IDs:
		 * 0: Ball:
		 * 	0: bounced off to another angle with constant speed
		 * 	1: bounced off to another angle and toggle harmful mode.
		 * 1: Player:
		 * 	0,1: knocked back with the force applied from the object collided.
		 *  0: upon the knocked back, the Player
		 */
		/* Approach 1: get a list of movable objects
		 * check on each movable objects to see if they collide
		 * Assume we have objects a-g
		 * check a with b,c,d,e,f,g to find out which is closest to a.
		 * if a is colliding with f, remove a and f from object list
		 * else if a is colliding on the wall or not colliding at all, remove a.
		 */
		while(listOfObjects)
	}
}
