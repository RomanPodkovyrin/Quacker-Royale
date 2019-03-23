package com.anotherworld.view.graphics.layout;

import java.util.ArrayList;

import com.anotherworld.view.graphics.GraphicsDisplay;

public abstract class Layout {
    
    private float x;
    private float y;
    private float height;
    private float width;
    
    public static final float X_SCALE_ADJUSTMENT = 9f / 16f; //Times x values by this to adjust to aspect ratio
    
    private ArrayList<Layout> subLayouts;
    
    public Layout() {
        this(0f, 0f, 2f, 2f);
    }
    
    public Layout(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        subLayouts = new ArrayList<>();
    }
    
    public void enactLayout(GraphicsDisplay display) {
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
