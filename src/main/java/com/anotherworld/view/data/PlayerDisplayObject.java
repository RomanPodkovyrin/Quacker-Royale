package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_FAN;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.Programme;

import java.util.Optional;

/**
 * Stores and manages the data needed to display a player.
 * @author Jake Stewart
 *
 */
public class PlayerDisplayObject extends DisplayObject {

    private final PlayerDisplayData displayData;
    private float maxR;
    private float maxG;
    private float maxB;
    
    private Optional<Long> timeStartedFalling;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(Programme programme, PlayerDisplayData displayData) {
        super(programme, Points2d.gen3DCircle(displayData.getRadius()), GL_TRIANGLE_FAN, false);
        this.displayData = displayData;
        this.setColours();
        this.timeStartedFalling = Optional.empty();
    }
    
    private void setColours() {
        maxR = (float)(0.5f + Math.random() * 0.5f);
        maxG = (float)(0.5f + Math.random() * 0.5f);
        maxB = (float)(0.5f + Math.random() * 0.5f);
        
        
        this.setColour(maxR, maxG, maxB);
    }
    
    @Override
    public void transform() {
        super.transform();
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
        //return displayData.getState() != ObjectState.DEAD;
    }
    
    @Override
    public boolean shouldCameraFollow() {
        return displayData.getState() != ObjectState.DEAD;
    }

    @Override
    public float getZ() {
        float fallingRatio = 0;
        if (displayData.getState() == ObjectState.DEAD) {
            if (!timeStartedFalling.isPresent()) {
                timeStartedFalling = Optional.of(System.currentTimeMillis());
            }
            float timeFalling = System.currentTimeMillis() - timeStartedFalling.get();
            timeFalling = timeFalling / 1000;
            fallingRatio = (timeFalling * timeFalling * 4.8f);
        }
        return fallingRatio;
    }
    
}
