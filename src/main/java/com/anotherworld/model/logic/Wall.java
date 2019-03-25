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

    public Wall(WallData wallData) {
        this.wallData = wallData;
    }

    public float getXCoordinate() {
        return wallData.getXCoordinate();
    }

    public float getYCoordinate() {
        return wallData.getYCoordinate();
    }

    public float getXSize() {
        return wallData.getxSize();
    }

    public void setXSize(float xSize) {
        wallData.setxSize(xSize);
    }

    public float getYSize() {
        return wallData.getySize();
    }

    public void setYSize(float ySize) {
        wallData.setySize(ySize);
    }

    public float getXTargetSize() {
        return wallData.getxTargetSize();
    }

    public float getYTargetSize() {
        return wallData.getyTargetSize();
    }

    public float getXShrink() {
        return wallData.getxShrink();
    }

    public float getYShrink() {
        return wallData.getyShrink();
    }

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
