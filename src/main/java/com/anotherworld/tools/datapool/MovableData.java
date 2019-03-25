package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.DisplayData;

import java.io.Serializable;

/**
 * Class that represents the information about a game object that is able to move.
 *
 * @author Alfi S.
 */
public abstract class MovableData implements DisplayData, Serializable {

    private String objectID;
    private Matrix coordinates;
    private Matrix velocity;
    private ObjectState state;
    private float angle;
    private float speed;
    private float radius;

    /**
     * Class constructor for a movable object.
     * @param objectID The id of the object
     * @param xCoordinate The x coordinate of the object.
     * @param yCoordinate The y coordinate of the object.
     * @param state The state of the object.
     * @param speed The speed of the object.
     * @param radius The radius of the object.
     */
    public MovableData(String objectID, float xCoordinate, float yCoordinate, ObjectState state,
                       float speed, float radius) {
        this.objectID = objectID;
        this.setCoordinates(xCoordinate, yCoordinate);
        this.setVelocity(0,0);
        this.angle = 0;
        this.speed = speed;
        this.radius = radius;
        this.state = state;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied.
     */
    public void copyObject(MovableData data) {
        this.coordinates = data.getCoordinates();
        this.velocity = data.getVelocity();
        this.state = data.getState();
        this.angle = data.getAngle();
        this.speed = data.getSpeed();
        this.radius = data.getRadius();
    }

    /**
     * Gets the coordinates of the player.
     * @return Matrix object with player coordinates.
     */
    public Matrix getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the new coordinates of the player.
     * @param x the new x-coordinate to set.
     * @param y the new y-coordinate to set.
     */
    public void setCoordinates(float x, float y) {

        coordinates = new Matrix(x, y);
    }

    /**
     * Gets the current x-coordinate of the player.
     * @return player's x-coordinate as a float.
     */
    public float getXCoordinate() {
        return coordinates.getX();
    }

    /**
     * Sets the x-coordinate of the player.
     * @param newXCoordinate the new x-coordinate to set.
     */
    public void setXCoordinate(float newXCoordinate) {
        this.setCoordinates(newXCoordinate,coordinates.getY());
    }

    /**
     * Gets the current y-coordinate of the player.
     * @return player's y-coordinate as a float.
     */
    public float getYCoordinate() {
        return coordinates.getY();
    }

    /**
     * Sets the y-coordinate of the player.
     * @param newYCoordinate the new y-coordinate to set.
     */
    public void setYCoordinate(float newYCoordinate) {
        this.setCoordinates(coordinates.getX(),newYCoordinate);
    }

    /**
     * Sets the new velocity of the player and calculates a new angle
     * based on the velocity.
     * @param x the new x-velocity to set.
     * @param y the new y-velocity to set.
     */
    public void setVelocity(float x, float y) {
        velocity = new Matrix(x, y);
        this.angle = MatrixMath.vectorAngle(velocity);
    }

    /**
     * Gets the velocity matrix of the object.
     * @return matrix representing the velocity of the object.
     */
    public Matrix getVelocity() {
        return velocity;
    }

    /**
     * Gets the x velocity of the object.
     * @return the x velocity of the object.
     */
    public float getXVelocity() {
        return velocity.getX();
    }

    /**
     * Sets the x velocity of the object.
     * @param newXVelocity the x velocity to set.
     */
    public void setXVelocity(float newXVelocity) {
        this.setVelocity(newXVelocity, velocity.getY());
    }

    /**
     * Gets the y velocity of the object.
     * @return the y velocity of the object.
     */
    public float getYVelocity() {
        return velocity.getY();
    }

    /**
     * Sets the y velocity of the object.
     * @param newYVelocity the y velocity to set.
     */
    public void setYVelocity(float newYVelocity) {
        this.setVelocity(velocity.getX(), newYVelocity);
    }

    /**
     * Gets the angle the object is traveling relative to the y axis.
     * @return the angle of the object.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle the object is traveling relative to the y axis.
     * @param angle the angle to set.
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the speed the object can travel.
     * @return  the speed of the object.
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed the object can travel.
     * @param speed the speed to set.
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the state of the object.
     * @return the state of the object.
     */
    public ObjectState getState() {
        return state;
    }

    /**
     * Sets the state of the object.
     * @param state the state of the object.
     */
    public void setState(ObjectState state) {
        this.state = state;
    }

    /**
     * Gets the radius of the object.
     * @return the radius of the object.
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Gets the id of the object.
     * @return
     */
    public String getObjectID() {
        return objectID;
    }

}
