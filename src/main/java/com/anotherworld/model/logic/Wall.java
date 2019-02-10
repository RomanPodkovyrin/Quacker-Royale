package com.anotherworld.model.logic;

import com.anotherworld.tools.datapool.WallData;

/**
 * Represents the wall object.
 *
 * @author  Roman
 */
public class Wall {

    private WallData wallData;

    public Wall(WallData wallData) {
        this.wallData = wallData;
    }

    public float getXCoordinate() { return wallData.getXCoordinate(); }
    public float getYCoordinate() { return wallData.getYCoordinate(); }

    public float getXSize() { return wallData.getxSize(); }
    public void setXSize(float xSize) { wallData.setxSize(xSize); }

    public float getYSize() { return wallData.getySize(); }
    public void setYSize(float ySize) { wallData.setySize(ySize); }

    public float getXShrink() { return wallData.getxShrink(); }

    public float getYShrink() { return wallData.getyShrink(); }

    public int getStage() { return wallData.getStage(); }

    /**
     * Called when the wall needs to shrink.
     */
    public void nextStage(){
        int stage = getStage();
        int maxStage = wallData.getMaxStage();
        if (stage < maxStage) {
            wallData.setStage(stage + 1);
            this.setXSize(getXSize() - getXShrink());
            this.setYSize(getYSize() - getYShrink());
        }
    }

}
