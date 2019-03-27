package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.PlayerDisplayData;

public class HatSpriteSheet extends PlayerSpriteSheet {

    private final PlayerDisplayData data;
    
    //RIGHT DOWN LEFT UP
    private static final int DIRECTION_OFFSET = 5;
    
    private static final float MIN_ANIMATION_SPEED = 0.1f;
    
    private static final int EFFECT_OFFSET = DIRECTION_OFFSET * 4;
    
    private int directionOffset = 0;
    
    public HatSpriteSheet(PlayerDisplayData data) {
        super(data);
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }

    @Override
    public int getTextureId() {
        int id = super.getTextureId();
        if (data.getState() == ObjectState.DEAD) {
            if (data.isDeadByFalling()) {
                return 0;
            }
        }
        if (data.getVelocity().magnitude() >= MIN_ANIMATION_SPEED) {
            id -= super.getEffectOffset();
        }
        id += directionOffset;
        return id;
    }

    @Override
    public SpriteLocation getTextureBuffer() {
        switch (data.getObjectID().toLowerCase()) { //TODO switch this to real method
            case "santa":
                return SpriteLocation.SANTA_HAT;
            case "priate":
                return SpriteLocation.PIRATE_HAT;
            case "robber":
                return SpriteLocation.ROBBER_HAT;
            case "police":
                return SpriteLocation.POLICE_HAT;
            default:
                return SpriteLocation.SANTA_HAT;
        }
    }
    
}
