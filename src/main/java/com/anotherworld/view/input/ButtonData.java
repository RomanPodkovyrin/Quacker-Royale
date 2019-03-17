package com.anotherworld.view.input;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonData implements RectangleDisplayData {

    private float x;
    private float y;
    private float width;
    private float height;
    private String text;
    private float backgroundR;
    private float backgroundG;
    private float backgroundB;
    private float textR;
    private float textG;
    private float textB;
    private EventHandler<ActionEvent> action;
    
    public ButtonData(String text) {
        this.textR = 0;
        this.textG = 0;
        this.textB = 0;
        this.backgroundR = 1;
        this.backgroundG = 1;
        this.backgroundB = 1;
        this.text = text;
    }
    
    public void preformAction() {
        //TODO i have no idea what to do here
        action.notify();
    }
    
    public void setBackgroundColour(float r, float g, float b) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }
    
    public void setTextColour(float r, float g, float b) {
        textR = r;
        textG = g;
        textB = b;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    @Override
    public float getXCoordinate() {
        return x;
    }

    @Override
    public float getYCoordinate() {
        return y;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return null;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
    
    public String getText() {
        return text;
    }

    public void setOnAction(EventHandler<ActionEvent> action) {
        this.action = action;
    }
    
    public float getTextR() {
        return textR;
    }
    
    public float getTextG() {
        return textG;
    }
    
    public float getTextB() {
        return textB;
    }
    
    public float getBackgroundR() {
        return backgroundR;
    }
    
    public float getBackgroundG() {
        return backgroundG;
    }
    
    public float getBackgroundB() {
        return backgroundB;
    }

}
