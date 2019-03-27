package com.anotherworld.view.graphics.spritesheet;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class PlatformSpriteSheet extends RectangleSpriteSheet {

    private static final float TILE_SIZE = 10f;
    
    public PlatformSpriteSheet() {
        super(SpriteLocation.TILE, 1, 1000);
    }
    
    /**
     * Returns a buffer with the tile applied to the object.
     * @param w the object width
     * @param h the object height
     * @return a texture float buffer
     */
    public static FloatBuffer getBuffer(float w, float h) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(8);
        float maxX = w / TILE_SIZE;
        float maxY = h / TILE_SIZE;
        buffer.put(0);
        buffer.put(0);
        buffer.put(maxX);
        buffer.put(0);
        buffer.put(maxX);
        buffer.put(maxY);
        buffer.put(0);
        buffer.put(maxY);
        buffer.flip();
        return buffer;
    }

}
