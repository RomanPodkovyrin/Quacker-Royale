package com.anotherworld.model.logic;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.tools.datapool.PlatformData;

/**
 *Represents Platform Object.
 *
 * @author  Roman,Alfi
 */
public class Platform {

    private PlatformData platformData;

    public Platform(PlatformData platformData){
        this.platformData = platformData;
    }

    public float getXCoordinate() { return platformData.getXCoordinate(); }
    public float getYCoordinate() { return platformData.getYCoordinate(); }

    public float getXSize() { return platformData.getxSize(); }
    public void setXSize(float xSize) { platformData.setxSize(xSize); }

    public float getYSize() { return platformData.getySize(); }
    public void setYSize(float ySize) { platformData.setySize(ySize); }

    public float getXShrink() { return platformData.getxShrink(); }

    public float getYShrink() { return platformData.getyShrink(); }

    public int getStage() { return platformData.getStage(); }

    /**
     * Called when the platform needs to shrink.
     */
    public void nextStage(){
        int stage = getStage();
        int maxStage = platformData.getMaxStage();
        if (stage < maxStage) {
            platformData.setStage(stage + 1);
            this.setXSize(getXSize() - getXShrink());
            this.setYSize(getYSize() - getYShrink());
        }
    }

    /**
     * checks whether the the given coordinates are on the platform.
     * @param x the x coordinate
     * @param y  the y coordinate
     * @return true if coordinates are on the platform, false if not
     */
    public boolean isOnPlatform(float x, float y) {
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
     * @param object
     * @return true if coordinates are on the platform, false if not
     */
    public boolean isOnPlatform(AbstractMovable object) {
        return isOnPlatform(object.getXCoordinate(),object.getYCoordinate());
    }


}
