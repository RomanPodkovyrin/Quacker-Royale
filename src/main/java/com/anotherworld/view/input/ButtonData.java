package com.anotherworld.view.input;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

public class ButtonData implements RectangleDisplayData {

    private float x;
    private float y;
    private float angle;
    private ObjectState objectState;
    private float width;
    private float height;
    
    public ButtonData(float x, float y, float angle, float width, float height) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.width = width;
        this.height = height;
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
        return angle;
    }

    @Override
    public ObjectState getState() {
        return objectState;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

}
