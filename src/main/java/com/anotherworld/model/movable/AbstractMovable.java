package com.anotherworld.model.movable;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import com.anotherworld.tools.datapool.MovableData;

/**
 * Class that models a moving object in the game.
 * @author Alfi S
 */
public abstract class AbstractMovable {
    private MovableData movableData;

    public AbstractMovable(MovableData data) {
        this.movableData = data;

    }

    // COORDINATES //
    public Matrix getCoordinates() {
        return movableData.getCoordinates();
    }

    public void setCoordinates(float xCoordinate, float yCoordinate) {
        movableData.setCoordinates(xCoordinate, yCoordinate);
    }

    public float getXCoordinate() {
        return movableData.getCoordinates().getX();
    }

    public float getYCoordinate() {
        return movableData.getCoordinates().getY();
    }

    // VELOCITY //
    public Matrix getVelocity() {
        return movableData.getVelocity();
    }

    public void setVelocity(float xVelocity, float yVelocity) {
        movableData.setVelocity(xVelocity, yVelocity);
        movableData.setAngle(MatrixMath.vectorAngle(movableData.getVelocity()));
    }

    public float getXVelocity() {
        return movableData.getXVelocity();
    }

    public void setXVelocity(float xVelocity) {
        movableData.setXVelocity(xVelocity);
    }

    public float getYVelocity() {
        return movableData.getYVelocity();
    }

    public void setYVelocity(float yVelocity) {
        movableData.setYVelocity(yVelocity);
    }

    // OBJECT STATE //
    public ObjectState getState() {
        return movableData.getState();
    }

    public void setState(ObjectState state) {
        movableData.setState(state);
    }

    public float getAngle() {
        return movableData.getAngle();
    }

    public void setAngle(float angle) {
        movableData.setAngle(angle);
    }

    public float getSpeed() {
        return movableData.getSpeed();
    }

    public void setSpeed(float speed) {
        movableData.setSpeed(speed);
    }

    public float getRadius() {
        return movableData.getRadius();
    }

    public void setRadius(float radius) {
        movableData.setRadius(radius);
    }

}
