package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

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
     * Creates a rectangle to display.
     * @param spriteSheet The rectangle sprite sheet
     * @param programme The programme to use for rendering
     * @param displayData The display data
     */
    public RectangleDisplayObject(SpriteSheet spriteSheet, Programme programme, RectangleDisplayData displayData) {
        super(spriteSheet, programme, Points2d.genRectangle(displayData.getWidth(), displayData.getHeight()), DrawType.TRIANGLE_FAN, 1f, 1f, 1f);
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
