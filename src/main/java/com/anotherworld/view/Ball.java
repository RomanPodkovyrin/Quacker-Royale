package com.anotherworld.view;

public class Ball extends DisplayObject {

	private static final float BALL_R = 4;
	
	private static final Matrix2d BALL_MODEL = GEN_BALL_MODEL();
	
	public Ball(float x, float y, float theta) {
		super(BALL_MODEL, x, y, theta);
	}
	
	private static final Matrix2d GEN_BALL_MODEL() {
		Matrix2d result = new Matrix2d(3, 120);
		for (int j = 0; j < 120; j += 1) {
			result.setValue(0, j, BALL_R * (float)Math.cos(((float)j / 60) * Math.PI));
			result.setValue(1, j, BALL_R * (float)Math.sin(((float)j / 60) * Math.PI));
			result.setValue(2, j, 1);
		}
		result.draw("Circle");
		return result;
	}
	
}
