package com.anotherworld.model.logic;

import com.anotherworld.tools.datapool.WallData;

/**
 * Represents the wall object.
 *
 * @author  Roman
 */
public class Wall {

    private WallData wallData;
    private int stage = 1;
    private static int MAXSTAGE = 4;
    private float xShrink = 10;
    private float yShrink = 20;
    private float xSize = 140;
    private float ySize = 70;

    private float xCoordinate;
    private float yCoordinate;

    public Wall(WallData wallData, float x, float y) {

        this.wallData = wallData;

        this.xCoordinate = x;
        this.yCoordinate = y;
    }
    public float getXCoordinate() { return xCoordinate; }
    public float getYCoordinate() { return yCoordinate; }
    public float getXSize() {
        return xSize;
    }

    public float getYSize() {
        return ySize;
    }

    /**
     * Called when the wall needs to shrink.
     */
    public void nextStage(){
        if (stage < MAXSTAGE ){
            stage++;
            this.xSize -= xShrink;
            this.ySize -= yShrink;

        }
    }

}
