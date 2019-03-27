package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.BallSpriteSheet;
import com.anotherworld.view.programme.Programme;

/**
 * Manages displaying a ball object.
 * @author Jake Stewart
 *
 */
public class BallDisplayObject extends DisplayObject {

    private final BallDisplayData displayData;
    
    private final Programme programme;
    
    /**
     * Creates a display object to display a ball.
     * @param displayData The ball to display
     */
    public BallDisplayObject(Programme programme, BallDisplayData displayData) {
        super(new BallSpriteSheet(displayData), programme, programme.supportsTextures() ? Points2d.genRectangle(displayData.getRadius() * 2, displayData.getRadius() * 2) : Points2d.genCircle(displayData.getRadius()), DrawType.TRIANGLE_FAN, 1, 1, 1);
        this.displayData = displayData;
        this.programme = programme;
    }
    
    @Override
    public void transform() {
        super.transform();
        programme.rotatef(this.getTheta() +  90, 0, 0, 1);
        if (!programme.supportsTextures()) {
            if (displayData.isDangerous()) {
                super.setColour(1, 0, 0);
            } else {
                super.setColour(0, 1, 0);
            }
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
    public float getZ() {
        return 0;
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
