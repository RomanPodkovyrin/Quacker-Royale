package com.anotherworld.view.input;

public class MouseState {

    private final float x;
    private final float y;
    private final boolean mouseDown;
    
    /**
     * Creates a mouse state with the mouse's current information.
     * @param x The x position of the mouse cursor
     * @param y The y position of the mouse cursor
     * @param mouseDown If the mouse button is clicked
     */
    public MouseState(float x, float y, boolean mouseDown) {
        this.x = x;
        this.y = y;
        this.mouseDown = mouseDown;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public boolean isMouseDown() {
        return mouseDown;
    }
    
}
