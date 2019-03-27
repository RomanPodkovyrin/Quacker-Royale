package com.anotherworld.view.graphics.spritesheet;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class PlatformSpriteSheet extends RectangleSpriteSheet {

    private static final float TILE_SIZE = 20f;
    
    public PlatformSpriteSheet() {
        super(SpriteLocation.TILE, 1, 1000);
    }
    
    /**
     * Returns a buffer with the tile applied to the object.
     * @param w the object width
     * @param h the object height
     * @return a texture float buffer
     */
    public static FloatBuffer getBuffer(float x, float y, float w, float h) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(8);
        float maxX = w / TILE_SIZE;
        maxX /= 2;
        float maxY = h / TILE_SIZE;
        maxY /= 2;
        buffer.put(x - maxX);
        buffer.put(y - maxY);
        buffer.put(x + maxX);
        buffer.put(y - maxY);
        buffer.put(x + maxX);
        buffer.put(y + maxY);
        buffer.put(x - maxX);
        buffer.put(y + maxY);
        buffer.flip();
        return buffer;
    }

}
