package com.anotherworld.model.movable;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.tools.datapool.AbstractMovableData;
import com.anotherworld.tools.datapool.PlayerData;

/**
 * Class that models a moving object in the game.
 * @author Alfi S
 */
public abstract class AbstractMovable {
    private Matrix coordinates;
    private Matrix velocity;
    private ObjectState state;
    private int points;
    private float angle;
    private float speed;
    private AbstractMovableData data;
    private float radius;

    public AbstractMovable(float xCoordinate, float yCoordinate, ObjectState state){
        this.coordinates = new Matrix(xCoordinate, yCoordinate);
        this.state = state;

    }


    // COORDINATES //
    public Matrix getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float xCoordinate, float yCoordinate) {
        coordinates = new Matrix(xCoordinate, yCoordinate);
    }

    public float getXCoordinate() {
        return coordinates.getX();
    }

    public float getYCoordinate() {
        return coordinates.getY();
    }

    // VELOCITY //
    public Matrix getVelocity() {
        return velocity;
    }

    public void setVelocity(float xVelocity, float yVelocity) {
        velocity = new Matrix(xVelocity, yVelocity);
        this.angle = MatrixMath.vectorAngle(velocity);
    }

    public float getXVelocity() {
        return velocity.getX();
    }

    public void setXVelocity(float xVelocity) {
        velocity = new Matrix(xVelocity, velocity.getY());
    }

    public float getYVelocity() {
        return velocity.getY();
    }

    public void setYVelocity(float yVelocity) {
        velocity = new Matrix(velocity.getX(), yVelocity);
    }

    // OBJECT STATE //
    public ObjectState getState() {
        return state;
    }

    public void setState(ObjectState state) {
        this.state = state;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
