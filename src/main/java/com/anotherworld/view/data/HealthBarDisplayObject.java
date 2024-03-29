package com.anotherworld.view.data;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.programme.Programme;

/**
 * Manages and stores the data to display a health bar.
 * @author Jake Stewart
 *
 */
public class HealthBarDisplayObject extends DisplayObject {

    private final PlayerDisplayData displayData;
    
    private final Programme programme;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public HealthBarDisplayObject(Programme programme, PlayerDisplayData displayData) {
        super(programme, Points2d.genRectangle(displayData.getRadius() * 2, 0.75f), DrawType.TRIANGLE_FAN, 1f, 0f, 0f);
        this.displayData = displayData;
        this.programme = programme;
    }
    
    @Override
    public void transform() {
        super.transform();
        
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
    public float getZ() {
        return 0;
        //return -2 * displayData.getRadius() - 1f;
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
