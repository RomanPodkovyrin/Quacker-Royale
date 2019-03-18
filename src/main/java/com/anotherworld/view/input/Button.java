package com.anotherworld.view.input;

import com.anotherworld.view.Programme;
import com.anotherworld.view.data.RectangleDisplayObject;

import java.util.ArrayList;
import java.util.List;

public class Button extends RectangleDisplayObject implements Clickable {
    
    private boolean pressed;
    private boolean hover;
    
    private final ButtonData buttonData;
    
    private List<ButtonListener> listeners;
    
    public Button(Programme programme, ButtonData buttonData) {
        super(programme, buttonData);
        this.pressed = false;
        this.hover = true;
        this.listeners = new ArrayList<>();
        this.buttonData = buttonData;
    }

    @Override
    public void click() {
        if (!pressed) {
            pressed = true;
            for (ButtonListener l : listeners) {
                l.clicked();
            }
            buttonData.preformAction();
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
        return this.buttonData.getHeight();
    }
    
    @Override
    public float getWidth() {
        return this.buttonData.getWidth();
    }
    
}
