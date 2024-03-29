package com.anotherworld.view.graphics;

import com.anotherworld.view.data.primatives.Points2d;

/**
 * Creates a static camera to view the game.
 * @author Jake Stewart
 *
 */
public class Static2dCamera extends Camera {

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
        return 1.414213562f;
    }

    @Override
    public float getZ() {
        return -22.5f;
    }
    
    @Override
    public Points2d getViewDirection() {
        Points2d tempUp = new Points2d(3, 1);
        tempUp.setValue(2, 0, 1f);
        return tempUp;
    }

}
