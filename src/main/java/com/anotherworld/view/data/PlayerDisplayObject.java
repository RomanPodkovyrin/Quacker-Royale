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
    private float lastXLocation;
    private float lastYLocation;
    private static final float MAX_X_SCALE = 1f;
    private static final float MAX_X_DIFF = 5f;
    private float maxR;
    private float maxG;
    private float maxB;
    
    private Optional<Long> startedFalling;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(PlayerDisplayData displayData) {
        super(Points2d.genCircle(displayData.getRadius()), GL_TRIANGLE_FAN, false);
        this.displayData = displayData;
        this.lastXLocation = displayData.getXCoordinate();
        this.lastYLocation = displayData.getYCoordinate();
        this.setColours();
        this.startedFalling = Optional.empty();
    }
    
    private void setColours() {
        maxR = (float)(0.5f + Math.random() * 0.5f);
        maxG = (float)(0.5f + Math.random() * 0.5f);
        maxB = (float)(0.5f + Math.random() * 0.5f);
        
        float randomCo = (float)(0.5f + Math.random() * 0.5f);
        
        float max = randomCo * Math.max(Math.max(maxR, maxG), maxB);

        //maxR = maxR / max;
        //maxG = maxG / max;
        //maxB = maxB / max;
        
        this.setColour(maxR, maxG, maxB);
    }
    
    private float diff(float x, float y, float x1, float y1) {
        return (float)Math.sqrt(Math.pow(y1 - y, 2) + Math.pow(x1 - x, 2));
    }
    
    @Override
    public void transform(Programme programme) {
        super.transform(programme);
        
        //float healthCo = (float)displayData.getHealth() / (float)displayData.getMaxHealth();
        
        //this.setColour(healthCo * maxR, healthCo * maxG, healthCo * maxB);
        
        if (displayData.getState() == ObjectState.DEAD) {
            if (!startedFalling.isPresent()) {
                startedFalling = Optional.of(System.currentTimeMillis());
            }
            float timeFalling = System.currentTimeMillis() - startedFalling.get();
            timeFalling = timeFalling / 1000;
            float fallingRatio = 1 / (1 + timeFalling * timeFalling * 4.8f);
            programme.scalef(fallingRatio, fallingRatio, 1);
        }
        
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
        return true;
        //return displayData.getState() != ObjectState.DEAD;
    }
    
    @Override
    public boolean shouldCameraFollow() {
        return displayData.getState() != ObjectState.DEAD;
    }
    
}
