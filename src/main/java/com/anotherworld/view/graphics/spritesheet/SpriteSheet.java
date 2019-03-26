package com.anotherworld.view.graphics.spritesheet;

public class SpriteSheet {
    
    private static final long startTime = System.currentTimeMillis();
    
    public static final long getCurrentTime() {
        return System.currentTimeMillis() - startTime;
    }
    
    public boolean isTextured() {
        return false;
    }

    public int getTextureId() {
        return 0;
    }
    
    public SpriteLocation getTextureBuffer() {
        return SpriteLocation.NONE;
    }

    public boolean shouldBeTransformed() {
        return true;
    }
    
}
