package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

import java.io.Serializable;

public class PlatformData implements RectangleDisplayData, Serializable {

    private int stage = 3;
    private static final int MAXSTAGE = 4;
    private static final float xShrink = 10;
    private static final float yShrink = 20;

    private float xSize = 60;
    private float ySize = 30;
    private float width = xSize * 2;
    private float height = ySize * 2;

    private float xCoordinate = 80;
    private float yCoordinate = 45;

    public PlatformData(float x, float y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }


    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public static int getMaxStage() {
        return MAXSTAGE;
    }

    public static float getxShrink() {
        return xShrink;
    }

    public static float getyShrink() {
        return yShrink;
    }

    public float getxSize() {
        return xSize;
    }

    public void setxSize(float xSize) {
        this.xSize = xSize;
    }

    public float getySize() {
        return ySize;
    }

    public void setySize(float ySize) {
        this.ySize = ySize;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return null;
    }
}
