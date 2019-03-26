package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.RectangleDisplayData;

import java.io.Serializable;

/**
 * Class that represents the information about the wall within a game session.
 *
 * @author Alfi S.
 */
public class WallData implements RectangleDisplayData, Serializable {

    private int stage = 3;

    private static final float xShrink = 10;
    private static final float yShrink = 10;

    private float xSize = 70;
    private float ySize = 35;

    private float xTargetSize;
    private float yTargetSize;

    private float xCoordinate;
    private float yCoordinate;

    /**
     * Class constructor for a wall.
     * @param x the x coordinate of the center of the wall.
     * @param y the y coordinate of the center of the wall.
     */
    public WallData(float x, float y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.xTargetSize = xSize;
        this.yTargetSize = ySize;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied.
     */
    public void copyObject(WallData data) {
        this.stage = data.getStage();
        this.xSize = data.getxSize();
        this.ySize = data.getySize();
        this.xCoordinate = data.getXCoordinate();
        this.yCoordinate = data.getYCoordinate();
    }

    /**
     * Gets the current stage of the wall.
     * @return the current stage of the wall.
     */
    public int getStage() {
        return stage;
    }

    /**
     * Sets the current stage of the wall.
     * @param stage the stage to set.
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    /**
     * Gets the amount that the wall should shrink in the x axis.
     * @return amount the wall should shrink on the x axis.
     */
    public static float getxShrink() {
        return xShrink;
    }

    /**
     * Gets the amount that the wall should shrink in the y axis.
     * @return amount the platfrom should shrink on the y axis.
     */
    public static float getyShrink() {
        return yShrink;
    }

    /**
     * Gets the size of the wall on the x axis from the center to the edge.
     * @return the size of the wall on the x axis.
     */
    public float getxTargetSize() {
        return xTargetSize;
    }

    /**
     * Sets the size of the wall on the x axis from the center to the edge.
     * @param xTargetSize the size of the wall on the x axis to set.
     */
    public void setxTargetSize(float xTargetSize) {
        this.xTargetSize = xTargetSize;
    }

    /**
     * Gets the size of the wall on the y axis from the center to the edge.
     * @return the size of the wall on the y axis.
     */
    public float getyTargetSize() {
        return yTargetSize;
    }

    /**
     * Sets the size of the wall that the wall should shrink to on the y axis.
     * @param yTargetSize target size of the wall on the y axis to set.
     */
    public void setyTargetSize(float yTargetSize) {
        this.yTargetSize = yTargetSize;
    }

    /**
     * Gets the size of the wall on the x axis from the center to the edge.
     * @return the size of the wall on the x axis.
     */
    public float getxSize() {
        return xSize;
    }

    /**
     * Sets the size of the wall on the x axis from the center to the edge.
     * @param xSize the size of the wall on the x axis to set.
     */
    public void setxSize(float xSize) {
        this.xSize = xSize;
    }

    /**
     * Gets the size of the wall on the y axis from the center to the edge.
     * @return the size of the wall on the y axis.
     */
    public float getySize() {
        return ySize;
    }

    /**
     * Sets the size of the wall on the y axis from the center to the edge.
     * @param ySize the size of the wall on the y axis to set.
     */
    public void setySize(float ySize) {
        this.ySize = ySize;
    }

    /**
     * Gets the width of the wall.
     * @return width of the wall.
     */
    public float getWidth() {
        return xSize * 2;
    }

    /**
     * Sets the width of the wall.
     * @param width the width to set.
     */
    public void setWidth(float width) {
        this.xSize = width / 2;
    }

    /**
     * Gets the height of the wall.
     * @return the height of the wall.
     */
    public float getHeight() {
        return ySize * 2;
    }

    /**
     * Sets the height of the wall
     * @param height the height of the wall.
     */
    public void setHeight(float height) {
        this.ySize = height / 2;
    }

    /**
     * Gets the x coordinate of the center of the wall.
     * @return x coordinate of the wall.
     */
    public float getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Gets the y coordinate of the center of the wall.
     * @return y coordinate of the wall.
     */
    public float getYCoordinate() {
        return yCoordinate;
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
