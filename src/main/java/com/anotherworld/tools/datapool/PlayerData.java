package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;

/**
 * Represents the raw data of a player shared throughout the system.
 * @author Alfi S.
 */
public class PlayerData {

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
     * Sets the coordinates of the player
     * @param newCoordinates Matrix object of the new coordinates.
     */
    public void setCoordinates(Matrix newCoordinates) {
        this.xCoordinate = newCoordinates.getX();
        this.yCoordinate = newCoordinates.getY();

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

}
