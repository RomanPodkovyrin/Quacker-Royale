package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.PlayerDisplayData;

public class PlayerSpriteSheet extends SpriteSheet {

    private final PlayerDisplayData data;
    
    private static final int BASE_ID = 0;
    
    private static final int MAX_ID = 4;
    
    //RIGHT DOWN LEFT UP
    private static final int DIRECTION_OFFSET = 5;
    
    private static final long FRAME_TIME = 75;
    
    private static final float MIN_ANIMATION_SPEED = 0.1f;
    
    private static final int EFFECT_OFFSET = DIRECTION_OFFSET * 4;
    
    private int directionOffset = 0;
    
    public PlayerSpriteSheet(PlayerDisplayData data) {
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }

    @Override
    public int getTextureId() {
        int id = 0;
        if (data.getState() == ObjectState.DEAD) {
            if (data.isDeadByFalling()) {
                return 0;
            }
            return EFFECT_OFFSET * 2;
        }
        if (data.getVelocity().magnitude() >= MIN_ANIMATION_SPEED) {
            directionOffset = getDirectionOffset();
            directionOffset += getEffectOffset();
            id = ((int)((SpriteSheet.getCurrentTime() / FRAME_TIME) % (MAX_ID + 1))) + BASE_ID;
        }
        id += directionOffset;
        return id;
    }

    @Override
    public SpriteLocation getTextureBuffer() {
        return SpriteLocation.PLAYER;
    }
    
    private int getDirectionOffset() {
        float direction = data.getAngle();
        if (direction >= 45f && direction <= 135f) {
            //RIGHT
            return DIRECTION_OFFSET * 0;
        } else if (direction > 135f && direction < 225f) {
            //DOWN
            return DIRECTION_OFFSET * 1;
        } else if (direction >= 225f && direction <= 315f) {
            //LEFT
            return DIRECTION_OFFSET * 2;
        } else {
            //UP
            return DIRECTION_OFFSET * 3;
        }
    }
    
    protected int getEffectOffset() {
        if (data.isShielded()) {
            return EFFECT_OFFSET;
        }
        return 0;
    }
    
}
