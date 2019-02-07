package com.anotherworld.model.logic;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.tools.datapool.PlatformData;

/**
 *Represents Platform Object.
 *
 * @ Roman
 */
public class Platform {

    private PlatformData platformData;

    private int stage = 1;
    private static int MAXSTAGE = 4;
    private float xShrink = 10;
    private float yShrink = 20;
    private float xSize = 140;
    private float ySize = 70;

    private float xCoordinate;
    private float yCoordinate;

    public Platform(float x, float y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public Platform(float x, float y, float xSide, float ySide){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.xSize = xSide;
        this.ySize = ySide;
    }

    public float getDistanceX() {
        return xSize;
    }

    public float getDistanceY() {
        return ySize;
    }

    /**
     * Called when the platform needs to shrink.
     */
    public void nextStage(){
        if (stage < MAXSTAGE ){
            stage ++;
            this.xSize -= xShrink;
            this.ySize -= yShrink;
        }
    }

    /**
     * checks whether the the given coordinates are on the platform.
     * @param x the x coordinate
     * @param y  the y coordinate
     * @return true if coordinates are on the platform, false if not
     */
    public boolean isOnPlatform(float x, float y) {
        float leftX = xCoordinate - xSize/2;
        float upperY = yCoordinate - ySize/2;

        float rightX = xCoordinate + xSize/2;
        float bottomY = yCoordinate + ySize/2;

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
     * @param object
     * @return true if coordinates are on the platform, false if not
     */
    public boolean isOnPlatform(AbstractMovable object) {
        return isOnPlatform(object.getXCoordinate(),object.getYCoordinate());
    }


}
