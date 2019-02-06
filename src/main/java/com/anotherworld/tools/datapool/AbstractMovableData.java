package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.ObjectState;

public class AbstractMovableData {

    private float xCoordinate;
    private float yCoordinate;
    private ObjectState state;
    private float angle;
    private float xVelocity;
    private float yVelocity;
    private float speed;

    /**
     * Gets the coordinates of the player
     * @return Matrix object with player coordinates
     */
    public Matrix getCoordinates() {
        return new Matrix(xCoordinate, yCoordinate);
    }

    /**
     * Sets the new coordinates of the player
     * @param x the new x-coordinate to set
     * @param y the new y-cooridnate to set
     */
    public void setCoordinates(float x, float y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * Gets the current x-coordinate of the player
     * @return player's x-coordinate as a float
     */
    public float getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the player
     * @param newXCoordinate the new x-coordinate to set.
     */
    public void setXCoordinate(float newXCoordinate) {
        this.xCoordinate = newXCoordinate;
    }

    /**
     * Gets the current y-coordinate of the player
     * @return player's y-coordinate as a float
     */
    public float getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the player
     * @param newYCoordinate the new y-coordinate to set.
     */
    public void setYCoordinate(float newYCoordinate) {
        this.yCoordinate = newYCoordinate;
    }

    /**
     * Sets the new velocity of the player and calculates a new angle
     * based on the velocity
     * @param x the new x-velocity to set
     * @param y the new y-velocity to set
     */
    public void setVelocity(float x, float y) {
        this.xVelocity = x;
        this.yVelocity = y;
    }

    public Matrix getVelocity() {
        return new Matrix(xVelocity, yVelocity);
    }

    public float getXVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }


}
