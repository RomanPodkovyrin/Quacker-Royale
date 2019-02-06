package com.anotherworld.tools.datapool;

public class PlatformData {

    private int stage = 1;
    private static final int MAXSTAGE = 4;
    private static final float xShrink = 10;
    private static final float yShrink = 20;

    private float xSize = 140;
    private float ySize = 70;
    private float width = xSize * 2;
    private float height = ySize * 2;

    private float xCoordinate;
    private float yCoordinate;

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

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
