package com.anotherworld.view.graphics;

/**
 * Creates a static camera to view the game.
 * @author Jake Stewart
 *
 */
public class Static2dCamera implements Camera {

    private final float x;
    private final float y;
    private final float width;
    private final float height;
    
    /**
     * Creates a camera with fixed location and dimensions.
     * @param x The x position
     * @param y The y position
     * @param width The camera width
     * @param height The camera height
     */
    public Static2dCamera(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
    
    @Override
    public float getDepth() {
        return 10;
    }

    @Override
    public float getZ() {
        return 0;
    }

}
