package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_FAN;

import com.anotherworld.view.Programme;

/** 
 * Stores and manages the data needed to display a rectangle.
 * @author Jake Stewart
 *
 */
public class RectangleDisplayObject extends DisplayObject {

    private final RectangleDisplayData displayData;
    
    private final float maxWidth;
    
    private final float maxHeight;
    
    private final Programme programme;
    
    /**
     * Creates a display object to display a rectangle.
     * @param displayData The rectangle to display
     */
    public RectangleDisplayObject(Programme programme, RectangleDisplayData displayData) {
        super(programme, Points2d.genRectangle(displayData.getWidth(), displayData.getHeight()), GL_TRIANGLE_FAN, false, 0.6f, 0.4f, 0f);
        this.displayData = displayData;
        this.maxWidth = displayData.getWidth();
        this.maxHeight = displayData.getHeight();
        this.programme = programme;
    }
    
    @Override
    public void transform() {
        super.transform();
        
        programme.scalef(displayData.getWidth() / maxWidth, displayData.getHeight() / maxHeight, 1f);
        
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
