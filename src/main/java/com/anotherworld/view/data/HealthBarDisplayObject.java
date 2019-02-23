package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_TRIANGLE_FAN;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.Programme;

/**
 * Manages and stores the data to display a health bar.
 * @author Jake Stewart
 *
 */
public class HealthBarDisplayObject extends DisplayObject {

    private final PlayerDisplayData displayData;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public HealthBarDisplayObject(PlayerDisplayData displayData) {
        super(Points2d.genRectangle(displayData.getRadius() * 2, 0.75f), GL_TRIANGLE_FAN, 1f, 0f, 0f);
        this.displayData = displayData;
    }
    
    @Override
    public void transform(Programme programme) {
        super.transform(programme);
        
        float healthCo = (float)displayData.getHealth() / (float)displayData.getMaxHealth();
        
        programme.scalef(healthCo, 1, 1);
    }

    @Override
    public float getTheta() {
        return 0;
    }

    @Override
    public float getX() {
        return displayData.getXCoordinate();
    }

    @Override
    public float getY() {
        return displayData.getYCoordinate() - displayData.getRadius() - 1f;
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
