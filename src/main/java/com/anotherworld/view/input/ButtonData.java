package com.anotherworld.view.input;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;
import com.anotherworld.view.data.TextDisplayData;
import com.anotherworld.view.data.primatives.Supplier;

import java.util.Optional;

public class ButtonData implements RectangleDisplayData, TextDisplayData {

    private static Supplier<Float> ZERO = () -> 0f;
    
    private Supplier<Float> x = ZERO;
    private Supplier<Float> y = ZERO;
    private Supplier<Float> width = ZERO;
    private Supplier<Float> height = ZERO;
    private Supplier<String> text;
    private float backgroundR;
    private float backgroundG;
    private float backgroundB;
    private boolean transparentBackground;
    private float textR;
    private float textG;
    private float textB;
    private Optional<ButtonListener> action;
    
    public ButtonData(String text) {
        this(() -> {
            return text;
        }, false);
    }
    
    public ButtonData(Supplier<String> text, boolean transparentBackground){
        this.textR = 1;
        this.textG = 1;
        this.textB = 1;
        this.backgroundR = 1;
        this.backgroundG = 1;
        this.backgroundB = 1;
        this.transparentBackground = transparentBackground;
        this.text = text;
        action = Optional.empty();
    }
    
    public void preformAction() {
        if (action.isPresent()) {
            action.get().clicked();
        }
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
        this.width = () -> width;
    }
    
    public void setWidth(Supplier<Float> width) {
        this.width = width;
    }
    
    public void setHeight(float height) {
        this.height = () -> height;
    }
    
    public void setPosition(float x, float y) {
        this.x = () -> x;
        this.y = () -> y;
    }
    
    public void setText(String text) {
        this.text = () -> {
            return text;
        };
    }
    
    @Override
    public float getXCoordinate() {
        return x.get();
    }

    @Override
    public float getYCoordinate() {
        return y.get();
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
        return width.get();
    }

    @Override
    public float getHeight() {
        return height.get();
    }
    
    public String getText() {
        return text.get();
    }

    public void setOnAction(ButtonListener action) {
        this.action = Optional.of(action);
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

    public boolean hasTransparentBackground() {
        return transparentBackground;
    }

}
