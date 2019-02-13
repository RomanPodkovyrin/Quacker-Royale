package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.DisplayData;

import java.io.Serializable;

public abstract class MovableData implements DisplayData, Serializable {

    private Matrix coordinates;
    private Matrix velocity;
    private ObjectState state;
    private float angle;
    private float speed;
    private float radius;

    public MovableData(float xCoordinate, float yCoordinate, ObjectState state,
                       float speed, float radius) {
        this.setCoordinates(xCoordinate, yCoordinate);
        this.setVelocity(0,0);
        this.angle = 0;
        this.speed = speed;
        this.radius = radius;
        this.state = state;
    }

    /**
     * Gets the coordinates of the player
     * @return Matrix object with player coordinates
     */
    public Matrix getCoordinates() { return coordinates; }

    /**
     * Sets the new coordinates of the player
     * @param x the new x-coordinate to set
     * @param y the new y-cooridnate to set
     */
    public void setCoordinates(float x, float y) {

        coordinates = new Matrix(x, y);
        this.angle = MatrixMath.vectorAngle(coordinates);
    }

    /**
     * Gets the current x-coordinate of the player
     * @return player's x-coordinate as a float
     */
    public float getXCoordinate() {
        return coordinates.getX();
    }

    /**
     * Sets the x-coordinate of the player
     * @param newXCoordinate the new x-coordinate to set.
     */
    public void setXCoordinate(float newXCoordinate) {
        setCoordinates(newXCoordinate,coordinates.getY());
    }

    /**
     * Gets the current y-coordinate of the player
     * @return player's y-coordinate as a float
     */
    public float getYCoordinate() {
        return coordinates.getY();
    }

    /**
     * Sets the y-coordinate of the player
     * @param newYCoordinate the new y-coordinate to set.
     */
    public void setYCoordinate(float newYCoordinate) {
        setCoordinates(coordinates.getX(),newYCoordinate);
    }

    /**
     * Sets the new velocity of the player and calculates a new angle
     * based on the velocity
     * @param x the new x-velocity to set
     * @param y the new y-velocity to set
     */
    public void setVelocity(float x, float y) {
        velocity = new Matrix(x, y);
    }

    public Matrix getVelocity() {
        return velocity;
    }

    public float getXVelocity() {
        return velocity.getX();
    }

    public void setXVelocity(float newXVelocity) {
        velocity = new Matrix(newXVelocity, velocity.getY());
    }

    public float getYVelocity() {
        return velocity.getY();
    }

    public void setYVelocity(float newYVelocity) {
        velocity = new Matrix(velocity.getX(), newYVelocity);
    }

    public float getAngle() { return angle; }

    public void setAngle(float angle) { this.angle = angle; }

    public float getSpeed() { return speed; }

    public void setSpeed(float speed) { this.speed = speed; }

    public ObjectState getState() { return state; }

    public void setState(ObjectState state) { this.state = state; }

    public float getRadius() { return radius; }

    public void setRadius(float radius) { this.radius = radius; }

}
