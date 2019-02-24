package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

import java.io.Serializable;

public class WallData implements RectangleDisplayData, Serializable {

    private int stage = 3;
    private static final int MAXSTAGE = 4;
    private static final float xShrink = 10;
    private static final float yShrink = 10;

    private float xSize = 70;
    private float ySize = 35;
    private float width = xSize * 2;
    private float height = ySize * 2;

    private float xCoordinate = 80;
    private float yCoordinate = 45;

    public WallData(float x, float y) {
        this.xCoordinate = x;
        this.yCoordinate = y;

    }

    public void copyObject(WallData data) {
        this.stage = data.getStage();
        this.xSize = data.getxSize();
        this.ySize = data.getySize();
        this.width = data.getWidth();
        this.height = data.getHeight();
        this.xCoordinate = data.getXCoordinate();
        this.yCoordinate = data.getYCoordinate();
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

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getYCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
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
