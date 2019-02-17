package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

public class RectangleDisplayObject extends DisplayObject {

    private final RectangleDisplayData displayData;
    
    /**
     * Creates a display object to display a rectangle.
     * @param displayData The rectangle to display
     */
    public RectangleDisplayObject(RectangleDisplayData displayData) {
        super(DisplayObject.genRectangle(displayData.getWidth(), displayData.getHeight()), GL_TRIANGLE_FAN);
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
