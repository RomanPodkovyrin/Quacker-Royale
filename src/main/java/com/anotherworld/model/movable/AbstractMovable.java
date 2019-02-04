package com.anotherworld.model.movable;


import com.anotherworld.tools.datapool.PlayerData;

public abstract class AbstractMovable {
    private float xCoordinate;
    private float yCoordinate;
    private ObjectState state;
    private int points;
    private float angle;
    private float xVelocity;
    private float yVelocity;
    private float speed;
    private PlayerData data;

    private float radius;

    public AbstractMovable(float xCoordinate, float yCoordinate, ObjectState state){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.state = state;

    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setCoordinates(float xCoordinate, float yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

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

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
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
    
    private void OnCollision() {
    	
    }

}
