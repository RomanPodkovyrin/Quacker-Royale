package com.anotherworld.view.data;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.PlayerSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.util.LinkedList;
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
    
    private Programme programme;
    private Optional<Long> timeStartedFalling;
    private HealthBarDisplayObject healthBar;
    
    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public PlayerDisplayObject(Programme programme, PlayerDisplayData displayData) {
        super(new PlayerSpriteSheet(displayData), programme, programme.supportsTextures() ? Points2d.genRectangle(displayData.getRadius() * 2, displayData.getRadius() * 2) : Points2d.genCircle(displayData.getRadius()), DrawType.TRIANGLE_FAN);
        this.displayData = displayData;
        this.setColours();
        this.timeStartedFalling = Optional.empty();
        this.programme = programme;
        this.healthBar = new HealthBarDisplayObject(programme, displayData);
    }
    
    private void setColours() {
        maxR = (float)(0.5f + Math.random() * 0.5f);
        maxG = (float)(0.5f + Math.random() * 0.5f);
        maxB = (float)(0.5f + Math.random() * 0.5f);
        
        
        this.setColour(maxR, maxG, maxB);
    }
    
    @Override
    public LinkedList<DisplayObject> draw() {
        LinkedList<DisplayObject> result = super.draw();
        result.addAll(healthBar.draw());
        return result;
    }
    
    @Override
    public void transform() {
        super.transform();
        if (displayData.getState() == ObjectState.DEAD && displayData.isDeadByFalling()) {
            if (!timeStartedFalling.isPresent()) {
                timeStartedFalling = Optional.of(System.currentTimeMillis());
            }
            float timeFalling = System.currentTimeMillis() - timeStartedFalling.get();
            timeFalling = timeFalling / 1000;
            programme.scalef(1 / (1 + timeFalling * timeFalling * 4.8f), 1 / (1 + timeFalling * timeFalling * 4.8f), 0);
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
    public boolean shouldDraw() {
        return true;
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
        return fallingRatio - 0;//displayData.getRadius();
    }
    
}
