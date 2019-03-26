package com.anotherworld.model.logic;

import com.anotherworld.tools.datapool.WallData;

/**
 * Represents the wall object.
 *
 * @author  Roman
 */
public class Wall {

    private static final float SIZE_DECREASE = 0.08f;
    private WallData wallData;

    /**
     * Class constructor to represent the logic of a wall.
     * @param wallData the data of the wall.
     */
    public Wall(WallData wallData) {
        this.wallData = wallData;
    }

    /**
     * Gets the x coordinate of the wall.
     * @return the x coordinate of the wall.
     */
    public float getXCoordinate() {
        return wallData.getXCoordinate();
    }

    /**
     * Gets the y coordinate of the wall.
     * @return the y coordinate of the wall.
     */
    public float getYCoordinate() {
        return wallData.getYCoordinate();
    }

    /**
     * Gets the size of the wall along the x axis
     * from the center to the edge.
     * @return the size of the wall along the x axis.
     */
    public float getXSize() {
        return wallData.getxSize();
    }

    /**
     * Sets the size of the wall along the x axis
     * from the center to the edge.
     * @param xSize the size of the wall along the x axis to set.
     */
    public void setXSize(float xSize) {
        wallData.setxSize(xSize);
    }

    /**
     * Gets the size of the wall along the y axis
     * from the center to the edge.
     * @return the size of the wall along the y axis.
     */
    public float getYSize() {
        return wallData.getySize();
    }

    /**
     * Sets the size of the wall along the y axis
     * from the center to the edge.
     * @param ySize the size of the wall along the y axis to set.
     */
    public void setYSize(float ySize) {
        wallData.setySize(ySize);
    }

    /**
     * Gets the size of the wall that the wall should shrink to on the x axis.
     * @return target size of the wall on the x axis.
     */
    public float getXTargetSize() {
        return wallData.getxTargetSize();
    }

    /**
     * Gets the size of the wall that the wall should shrink to on the y axis.
     * @return target size of the wall on the y axis.
     */
    public float getYTargetSize() {
        return wallData.getyTargetSize();
    }

    /**
     * Gets the amount that the wall should shrink in the x axis.
     * @return amount the wall should shrink on the x axis.
     */
    public float getXShrink() {
        return wallData.getxShrink();
    }

    /**
     * Gets the amount that the wall should shrink in the y axis.
     * @return amount the platfrom should shrink on the y axis.
     */
    public float getYShrink() {
        return wallData.getyShrink();
    }

    /**
     * Gets the current stage of the wall.
     * @return the current stage of the wall.
     */
    public int getStage() {
        return wallData.getStage();
    }

    /**
     * When the wall needs to shrink, the stage and target sizes are updated.
     */
    public void nextStage() {
        int stage = getStage();
        if (stage > 0) {
            wallData.setStage(stage - 1);
            wallData.setxTargetSize(getXSize() - getXShrink());
            wallData.setyTargetSize(getYSize() - getYShrink());
        }
    }

    /**
     * Smoothly shrinks the size of the wall.
     */
    public void shrink() {
        if (getXSize() > getXTargetSize() && getYSize() > getYTargetSize()) {
            setXSize(getXSize() - SIZE_DECREASE);
            setYSize(getYSize() - SIZE_DECREASE);
        }
    }

}
