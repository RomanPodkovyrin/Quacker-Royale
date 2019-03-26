package com.anotherworld.view.graphics.spritesheet;

public class RectangleSpriteSheet extends SpriteSheet {

    private SpriteLocation location;
    
    public RectangleSpriteSheet(SpriteLocation location) {
        this.location = location;
    }
    
    @Override
    public SpriteLocation getTextureBuffer() {
        return location;
    }
    
}
