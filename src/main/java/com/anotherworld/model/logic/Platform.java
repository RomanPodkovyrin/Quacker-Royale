package com.anotherworld.model.logic;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.AbstractMovable;
import com.anotherworld.tools.datapool.MovableData;
import com.anotherworld.tools.datapool.PlatformData;

/**
 *Represents Platform Object.
 *
 * @author Roman P
 * @author Alfi S
 */
public class Platform {

    private static final float SIZE_DECREASE = 0.1f;
    private PlatformData platformData;

    public Platform(PlatformData platformData){
        this.platformData = platformData;
    }

    public float getXCoordinate() { return platformData.getXCoordinate(); }
    public float getYCoordinate() { return platformData.getYCoordinate(); }

    public float getXSize() { return platformData.getxSize(); }
    private void setXSize(float xSize) { platformData.setxSize(xSize); }

    public float getYSize() { return platformData.getySize(); }
    private void setYSize(float ySize) { platformData.setySize(ySize); }

    private float getXTargetSize() { return platformData.getxTargetSize(); }
    private float getYTargetSize() { return platformData.getyTargetSize(); }

    private float getXShrink() { return PlatformData.getxShrink(); }
    private float getYShrink() { return PlatformData.getyShrink(); }

    int getStage() { return platformData.getStage(); }

    /**
     * Called when the platform needs to shrink.
     */
    void nextStage(){
        int stage = getStage();
        if (stage > 0) {
            platformData.setStage(stage - 1);
            platformData.setxTargetSize(getXSize() - getXShrink());
            platformData.setyTargetSize(getYSize() - getYShrink());
        }
    }

    void shrink() {
        if (getXSize() > getXTargetSize() &&
            getYSize() > getYTargetSize()) {
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
     * @param object
     * @return true if coordinates are on the platform, false if not
     */
    boolean isOnPlatform(MovableData object) {
        return isOnPlatform(object.getXCoordinate(),object.getYCoordinate());
    }


}
