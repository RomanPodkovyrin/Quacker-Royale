package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL45.*;

import com.anotherworld.model.movable.ObjectState;

public class PlayerDisplayObject extends DisplayObject {

    private final PlayerDisplayData displayData;
    private float lastXLocation;
    private float lastYLocation;
    private static final float MAX_X_SCALE = 1f;
    private static final float MAX_X_DIFF = 5f;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(PlayerDisplayData displayData) {
        super(DisplayObject.genCircle(displayData.getRadius()), GL_TRIANGLE_FAN);
        this.displayData = displayData;
        this.lastXLocation = displayData.getXCoordinate();
        this.lastYLocation = displayData.getYCoordinate();
    }
    
    private float diff(float x, float y, float x1, float y1) {
        return (float)Math.sqrt(Math.pow(y1 - y, 2) + Math.pow(x1 - x, 2));
    }
    
    @Override
    public void transform() {
        super.transform();
        
        this.setColour(((float)displayData.getHealth() / 70f), (float)displayData.getHealth() / 70f, 0);
        
        /*if (diff(this.getX(), this.getY(), this.lastXLocation, this.lastYLocation) > MAX_X_DIFF) {
            
        }
        
        float yScale = diff(this.getX(), this.getY(), this.lastXLocation, this.lastYLocation) / displayData.getRadius();
        
        glScalef(1, yScale, 1);*/
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
        return displayData.getState() != ObjectState.DEAD;
    }
    
    @Override
    public boolean shouldCameraFollow() {
        return displayData.getState() != ObjectState.DEAD;
    }
    
}
