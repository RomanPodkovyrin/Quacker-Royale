package com.anotherworld.view;

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
    
    public int getTextureBuffer() {
        return 0;
    }
    
}
