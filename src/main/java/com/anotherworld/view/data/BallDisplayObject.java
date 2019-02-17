package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class BallDisplayObject extends DisplayObject {

    private final BallDisplayData displayData;
    
    /**
     * Creates a display object to display a ball.
     * @param displayData The ball to display
     */
    public BallDisplayObject(BallDisplayData displayData) {
        super(DisplayObject.genCircle(displayData.getRadius()), GL_TRIANGLE_FAN);
        this.displayData = displayData;
    }

    @Override
    public float getTheta() {
        return displayData.getAngle();
    }

    @Override
    public float getX() {
        return displayData.getXCoordinate();
    }

    @Override
    public float getY() {
        return displayData.getYCoordinate();
    }
    
    @Override
    public boolean shouldCameraFollow() {
        return false;
    }
    
}
