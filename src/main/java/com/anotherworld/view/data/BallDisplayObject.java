package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_FAN;

import com.anotherworld.view.Programme;

/**
 * Manages displaying a ball object.
 * @author Jake Stewart
 *
 */
public class BallDisplayObject extends DisplayObject {

    private final BallDisplayData displayData;
    
    /**
     * Creates a display object to display a ball.
     * @param displayData The ball to display
     */
    public BallDisplayObject(BallDisplayData displayData) {
        super(Points2d.genCircle(displayData.getRadius()), GL_TRIANGLE_FAN, true);
        this.displayData = displayData;
    }
    
    @Override
    public void transform(Programme programme) {
        super.transform(programme);
        if (displayData.isDangerous()) {
            super.setColour(1, 0, 0);
        } else {
            super.setColour(0, 1, 0);
        }
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
    public boolean shouldDraw() {
        return true;
    }
    
    @Override
    public boolean shouldCameraFollow() {
        return false;
    }
    
}
