package com.anotherworld.view.graphics.layout;

import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Static2dCamera;

import java.util.ArrayList;

public abstract class Layout {
    
    private float x;
    private float y;
    private float height;
    private float width;
    
    private ArrayList<Layout> subLayouts;
    
    public Layout() {
        this(0f, 0f, 2f, 2f);
    }
    
    /**
     * Creates a layout to display a menu.
     * @param x the x position of the layout
     * @param y the y position of the layout
     * @param height the height of the layout
     * @param width the width of the layout
     */
    public Layout(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        subLayouts = new ArrayList<>();
    }
    
    /**
     * Takes a display and adds all the items currently in the layout to it.
     * @param display the display that will use the layout
     */
    public void enactLayout(GraphicsDisplay display) {
        display.changeCamera(new Static2dCamera(0, 0, 2f * 16f / 9f, 2));
        for (Layout l : subLayouts) {
            l.enactLayout(display);
        }
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWidth() {
        return width;
    }
    
}
