package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL46.glScalef;

import com.anotherworld.tools.datapool.WallData;

public class WallDisplayObject extends DisplayObject {

    private final RectangleDisplayData displayData;
    
    private final float maxWidth;
    
    private final float maxHeight;

    /**
     * Creates a display object to display the wall.
     * @param displayData The wall to display
     */
    public WallDisplayObject(WallData displayData) {
        super(Points2d.genWall(displayData.getWidth(), displayData.getHeight(), 1), GL_TRIANGLE_STRIP, 0f, 0.6f, 1f);
        this.displayData = displayData;
        this.maxWidth = displayData.getWidth();
        this.maxHeight = displayData.getHeight();
    }
    
    @Override
    public void transform() {
        super.transform();
        
        glScalef(displayData.getWidth() / maxWidth, displayData.getHeight() / maxHeight, 1f);
        
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
