package com.anotherworld.model.logic;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.tools.datapool.MovableData;
import com.anotherworld.tools.datapool.PlatformData;

/**
 * Represents Platform Object.
 *
 * @author Roman P
 * @author Alfi S
 */
public class Platform {

    private static final float SIZE_DECREASE = 0.1f;
    private PlatformData platformData;

    /**
     * Class constructor to represent the logic of a platform.
     * @param platformData the data of the platform.
     */
    public Platform(PlatformData platformData) {
        this.platformData = platformData;
    }

    /**
     * Gets the x coordinate of the platform.
     * @return the x coordinate of the platform.
     */
    public float getXCoordinate() {
        return platformData.getXCoordinate();
    }

    /**
     * Gets the y coordinate of the platform.
     * @return the y coordinate of the platform.
     */
    public float getYCoordinate() {
        return platformData.getYCoordinate();
    }

    /**
     * Gets the size of the platform along the x axis
     * from the center to the edge.
     * @return the size of the platform along the x axis.
     */
    public float getXSize() {
        return platformData.getxSize();
    }

    /**
     * Sets the size of the platform along the x axis
     * from the center to the edge.
     * @param xSize the size of the platform along the x axis to set.
     */
    private void setXSize(float xSize) {
        platformData.setxSize(xSize);
    }

    /**
     * Gets the size of the platform along the y axis
     * from the center to the edge.
     * @return the size of the platform along the y axis.
     */
    public float getYSize() {
        return platformData.getySize();
    }

    /**
     * Sets the size of the platform along the y axis
     * from the center to the edge.
     * @param ySize the size of the platform along the y axis to set.
     */
    private void setYSize(float ySize) {
        platformData.setySize(ySize);
    }

    /**
     * Gets the size of the platform that the platform should shrink to on the x axis.
     * @return target size of the platform on the x axis.
     */
    private float getXTargetSize() {
        return platformData.getxTargetSize();
    }

    /**
     * Gets the size of the platform that the platform should shrink to on the y axis.
     * @return target size of the platform on the y axis.
     */
    private float getYTargetSize() {
        return platformData.getyTargetSize();
    }

    /**
     * Gets the amount that the platform should shrink in the x axis.
     * @return amount the platform should shrink on the x axis.
     */
    private float getXShrink() {
        return PlatformData.getxShrink();
    }

    /**
     * Gets the amount that the platform should shrink in the y axis.
     * @return amount the platfrom should shrink on the y axis.
     */
    private float getYShrink() {
        return PlatformData.getyShrink();
    }

    /**
     * Gets the current stage of the platform.
     * @return the current stage of the platform.
     */
    int getStage() {
        return platformData.getStage();
    }

    /**
     * When the platform needs to shrink, the stage and target sizes are updated.
     */
    void nextStage() {
        int stage = getStage();
        if (stage > 0) {
            platformData.setStage(stage - 1);
            platformData.setxTargetSize(getXSize() - getXShrink());
            platformData.setyTargetSize(getYSize() - getYShrink());
        }
    }

    /**
     * Smoothly shrinks the size of the platform.
     */
    void shrink() {
        if (getXSize() > getXTargetSize()
                && getYSize() > getYTargetSize()) {
            setXSize(getXSize() - SIZE_DECREASE);
            setYSize(getYSize() - SIZE_DECREASE);
        }
    }

    /**
     * checks whether the the given coordinates are on the platform.
     * @param x the x coordinate
     * @param y  the y coordinate
     * @return true if coordinates are on the platform, false if not
     */
    private boolean isOnPlatform(float x, float y) {
        float leftX  = getXCoordinate() - getXSize();
        float upperY = getYCoordinate() - getYSize();

        float rightX  = getXCoordinate() + getXSize();
        float bottomY = getYCoordinate() + getYSize();

        return (x <= rightX & x >= leftX) & (y <= bottomY & y >= upperY);
    }

    /**
     *checks whether the the given coordinates are on the platform.
     * @param matrix the x and y matrix of a point
     * @return true if coordinates are on the platform, false if not
     */
    public boolean isOnPlatform(Matrix matrix) {
        return isOnPlatform(matrix.getX(), matrix.getY());
    }

    /**
     * tells whether the object is on the platform.
     * @param object the object to check if it is on the platform.
     * @return true if coordinates are on the platform, false if not
     */
    boolean isOnPlatform(MovableData object) {
        return isOnPlatform(object.getXCoordinate(),object.getYCoordinate());
    }


}
