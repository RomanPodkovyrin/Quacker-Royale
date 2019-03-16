package com.anotherworld.view;

import com.anotherworld.view.data.BallDisplayData;

public class BallSpriteSheet extends SpriteSheet {
    
    private final BallDisplayData data;
    
    public BallSpriteSheet(BallDisplayData data) {
        this.data = data;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }
    
    @Override
    public int getTextureId() {
        return 0;
    }

    @Override
    public int getTextureBuffer() {
        return TextureMap.BALL_TEXTURE_BUFFER;
    }

}
