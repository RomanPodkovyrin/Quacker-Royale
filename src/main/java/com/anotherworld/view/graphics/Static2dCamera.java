package com.anotherworld.view.graphics;

public class Static2dCamera implements Camera {

    private float x;
    private float y;
    private float width;
    private float height;
    
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

}
