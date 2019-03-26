package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.view.data.PowerUpDisplayData;

public class PowerUpSpriteSheet extends SpriteSheet {
    
    private static final long FRAME_TIME = 150;
    
    private static final int NUMBER_OF_FRAMES = 4;

    private int powerUpType;
    
    public PowerUpSpriteSheet(PowerUpDisplayData displayData) {
        switch (displayData.getPowerUpType()) {
            case HEAL:
                powerUpType = NUMBER_OF_FRAMES * 1;
                break;
            case TIME_STOP:
                powerUpType = NUMBER_OF_FRAMES * 2;
                break;
            case SHIELD:
                powerUpType = 0;
                break;
            default:
                powerUpType = -1;
        }
    }
    
    @Override
    public SpriteLocation getTextureBuffer() {
        return powerUpType == -1 ? SpriteLocation.NONE : SpriteLocation.POWER_UP;
    }

    @Override
    public int getTextureId() {
        return ((int)((SpriteSheet.getCurrentTime() / FRAME_TIME) % NUMBER_OF_FRAMES)) + powerUpType;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }
    
}
