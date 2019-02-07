package com.anotherworld.view.input;

import com.anotherworld.view.graphics.Matrix2d;
import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.RectangleDisplayData;

import java.util.ArrayList;
import java.util.List;

public class Button extends DisplayObject implements Clickable {
    
    private boolean pressed;
    private boolean hover;
    
    private List<ButtonListener> listeners;
    
    public Button(ButtonData buttonData) {
        super((RectangleDisplayData)buttonData);
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
    public float getHeight() {
        return ((ButtonData)this.displayData).getHeight();
    }
    
    @Override
    public float getWidth() {
        return ((ButtonData)this.displayData).getWidth();
    }
    
}
