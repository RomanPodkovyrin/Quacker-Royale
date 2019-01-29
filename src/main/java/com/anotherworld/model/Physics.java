package com.anotherworld.model;

public class Physics {
	public float[] calculateVelocityXY(float speed, float angle) {
		float[] velocityXY = new float[2];
		velocityXY[0] = (float) (speed * Math.sin(angle));
		velocityXY[1] = (float) (speed * Math.cos(angle));
		return velocityXY;
	}
	
	public float[] calculateSpeedAngle(float velocityX, float velocityY) {
		float[] speedAngle = new float[2];
		speedAngle[0] = Math.abs(velocityY*velocityY+velocityX*velocityX);
		speedAngle[1] = (float) Math.tanh(velocityX/velocityY);
		return speedAngle;
	}
}
