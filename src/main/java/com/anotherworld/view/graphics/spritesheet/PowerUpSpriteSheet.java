package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.view.data.PowerUpDisplayData;

public class PowerUpSpriteSheet extends SpriteSheet {
    
    private static final long FRAME_TIME = 75;
    
    private static final int NUMBER_OF_FRAMES = 4;

    private SpriteLocation powerUpType;
    
    public PowerUpSpriteSheet(PowerUpDisplayData displayData) {
        switch (displayData.getPowerUpType()) {
            case HEAL:
                powerUpType = SpriteLocation.HEAL_POWERUP;
                break;
            case TIME_STOP:
                powerUpType = SpriteLocation.TIME_STOP_POWERUP;
                break;
            case SHIELD:
                powerUpType = SpriteLocation.SHIELD_POWERUP;
                break;
            default:
                powerUpType = SpriteLocation.NONE;
        }
    }
    
    public SpriteLocation getTextureBufferId() {
        return powerUpType;
    }

    @Override
    public int getTextureId() {
        return ((int)((SpriteSheet.getCurrentTime() / FRAME_TIME) % NUMBER_OF_FRAMES));
    }
    
}
