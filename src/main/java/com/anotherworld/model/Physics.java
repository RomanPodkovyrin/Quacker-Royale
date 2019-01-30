package com.anotherworld.model;

import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.model.movable.Ball;

public class Physics {
	public float[] calculateXYVelocity(float speed, float angle) {
		float[] xyVelocity = new float[2];
		xyVelocity[0] = (float) (speed * Math.sin(angle));
		xyVelocity[1] = (float) (speed * Math.cos(angle));
		return xyVelocity;
	}
	
	public float[] calculateSpeedAngle(float xVelocity, float yVelocity) {
		float[] speedAngle = new float[2];
		speedAngle[0] = Math.abs(yVelocity*yVelocity+xVelocity*xVelocity);
		speedAngle[1] = (float) Math.tanh(xVelocity/yVelocity);
		return speedAngle;
	}
	
	public float[] move(float xVelocity, float yVelocity, float xCoordinate, float yCoordinate) {
		float[] coordinates = {xCoordinate+xVelocity, yCoordinate+yVelocity};
		return coordinates;
	}
	
	public boolean checkCollision(AbstractMovable a, AbstractMovable b) {
		float xDistance = a.getxCoordinate() - b.getxCoordinate();
	    float yDistance = a.getyCoordinate() - b.getyCoordinate();

	    float sumOfRadii = a.getRadious() + b.getRadious();
	    float distanceSquared = xDistance * xDistance + yDistance * yDistance;

	    boolean isOverlapping = distanceSquared  < sumOfRadii * sumOfRadii;
	    return isOverlapping;
	}
	//0:lesser Y, 1:greater X, 2:greater Y, 3: lesser X
	public boolean bouncedWall(Ball a, float[] wallCoordinate){
		float circleR = a.getRadious();
		float circleX = a.getxCoordinate();
		float circleY = a.getyCoordinate();
		float deltaX = circleX - Math.max(wallCoordinate[3], Math.min(circleX, wallCoordinate[1]));
		float deltaY = circleY - Math.max(wallCoordinate[0], Math.min(circleY, wallCoordinate[2]));
		return !((deltaX * deltaX + deltaY * deltaY) < (circleR * circleR));
	}
}
