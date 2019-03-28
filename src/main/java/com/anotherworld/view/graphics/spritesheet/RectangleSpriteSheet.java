package com.anotherworld.view.graphics.spritesheet;

public class RectangleSpriteSheet extends SpriteSheet {

    private SpriteLocation location;
    private int numberOfSprites;
    private float spriteTime;
    
    /**
     * Creates a sprite sheet for a simple rectangle with the parsed options.
     * @param location The sprite location
     * @param numberOfSprites The number of sprites on the sheet
     * @param spriteTime How long each frame should be displayed
     */
    public RectangleSpriteSheet(SpriteLocation location, int numberOfSprites, float spriteTime) {
        this.location = location;
        this.numberOfSprites = numberOfSprites;
        this.spriteTime = spriteTime;
    }
    
    @Override
    public SpriteLocation getTextureBuffer() {
        return location;
    }
    
    @Override
    public boolean isTextured() {
        return true;
    }
    
    @Override
    public int getTextureId() {
        return (int)(SpriteSheet.getCurrentTime() / spriteTime) % numberOfSprites;
    }
    
}
