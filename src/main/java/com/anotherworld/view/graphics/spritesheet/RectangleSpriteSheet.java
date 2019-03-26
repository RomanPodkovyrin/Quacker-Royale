package com.anotherworld.view.graphics.spritesheet;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class RectangleSpriteSheet extends SpriteSheet {

    private SpriteLocation location;
    
    public RectangleSpriteSheet(SpriteLocation location) {
        this.location = location;
    }
    
    @Override
    public SpriteLocation getTextureBuffer() {
        return location;
    }
    
    public static FloatBuffer createRetangle() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(8);
        //TODO could be upside down?
        buffer.put(0);
        buffer.put(0);
        buffer.put(1);
        buffer.put(0);
        buffer.put(0);
        buffer.put(1);
        buffer.put(1);
        buffer.put(1);
        buffer.flip();
        return buffer;
    }
    
}
