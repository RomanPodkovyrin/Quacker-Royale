package com.anotherworld.view.input;

public class MouseState {

    private final float x;
    private final float y;
    
    /**
     * Creates a mouse state with the mouse's current information.
     * @param x The x position of the mouse cursor
     * @param y The y position of the mouse cursor
     */
    public MouseState(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
}
