package com.anotherworld.view.input;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;
import com.anotherworld.view.data.TextDisplayData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ButtonData implements RectangleDisplayData, TextDisplayData {

    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;
    private String text;
    private float backgroundR;
    private float backgroundG;
    private float backgroundB;
    private float textR;
    private float textG;
    private float textB;
    private EventHandler<ActionEvent> action;
    
    public ButtonData(String text) {
        this.textR = 1;
        this.textG = 1;
        this.textB = 1;
        this.backgroundR = 1;
        this.backgroundG = 1;
        this.backgroundB = 1;
        this.text = text;
    }
    
    public void preformAction() {
        action.handle(new ActionEvent());
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
