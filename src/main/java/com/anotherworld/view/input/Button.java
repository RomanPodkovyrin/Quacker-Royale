package com.anotherworld.view.input;

import com.anotherworld.view.graphics.Matrix2d;
import com.anotherworld.view.graphics.displayobject.DisplayObject;

import java.util.ArrayList;
import java.util.List;

public class Button extends DisplayObject implements Clickable {
    
    private float height;
    private float width;
    private boolean pressed;
    private boolean hover;
    
    private List<ButtonListener> listeners;
    
    public Button(Matrix2d points, float x, float y, float h, float w) {
        super(points, x, y, 0);
        this.height = h;
        this.width = w;
        this.pressed = false;
        this.hover = true;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void click() {
        if (!pressed) {
            pressed = true;
            for (ButtonListener l : listeners) {
                l.clicked();
            }
        }
    }
    
    @Override
    public void release() {
        if (pressed) {
            pressed = false;
        }
    }
    
    @Override
    public void hover() {
        if (!hover) {
            hover = true;
        }
    }
    
    @Override
    public void moveOff() {
        if (hover) {
            hover = true;
        }
    }
    
    public void addButtonListener(ButtonListener listener) {
        listeners.add(listener);
    }

    @Override
    public float getH() {
        return height;
    }

    @Override
    public float getW() {
        return width;
    }

}
