package com.anotherworld.model.movable;

public class Ball extends AbstractMovable {

    private int timesUp;
    private boolean canDamage;

    public Ball(float xCoordinate, float yCoordinate, ObjectState state){
        super(xCoordinate,yCoordinate, state);
        canDamage = false;
    }

    public boolean canDamage() { return canDamage; }
    public void setDamage(boolean canDamage) { this.canDamage = canDamage; }

    public String toString() {
        return "Location: x "+ getxCoordinate() + " y " + getyCoordinate();
    }
}
