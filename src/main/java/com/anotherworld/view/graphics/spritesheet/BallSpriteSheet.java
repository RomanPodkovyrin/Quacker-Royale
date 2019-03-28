package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.view.data.BallDisplayData;

public class BallSpriteSheet extends SpriteSheet {
    
    private final BallDisplayData data;
    
    private static final float ANIMATION_TIME = 180;
    
    public BallSpriteSheet(BallDisplayData data) {
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }
    
    @Override
    public int getTextureId() {
        int id = (int)(SpriteSheet.getCurrentTime() / ANIMATION_TIME);
        id %= data.isDangerous() ? 4 : 3;
        return id;
    }

    @Override
    public SpriteLocation getTextureBuffer() {
        if (data.isDangerous()) {
            return SpriteLocation.DANGEROUS_BALL;
        } else {
            return SpriteLocation.SAFE_BALL;
        }
    }

}
