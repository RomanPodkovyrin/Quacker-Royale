package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_STRIP;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.programme.Programme;

/**
 * Manages and stores the data needed to display a wall object.
 * @author Jake Stewart
 *
 */
public class WallDisplayObject extends DisplayObject {

    private final RectangleDisplayData displayData;
    
    private final float maxWidth;
    
    private final float maxHeight;
    
    private final Programme programme;

    /**
     * Creates a display object to display the wall.
     * @param displayData The wall to display
     */
    public WallDisplayObject(Programme programme, WallData displayData) {
        super(programme, Points2d.genWall(displayData.getWidth(), displayData.getHeight(), 1), GL_TRIANGLE_STRIP, 0f, 0.6f, 1f);
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
    public float getZ() {
        return 0f;
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
