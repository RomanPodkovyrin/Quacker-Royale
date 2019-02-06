package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.BallData;

public class Ball extends AbstractMovable {

    private BallData ballData;
    private boolean canDamage;

    public Ball(BallData ballData){
        super(ballData);
        this.ballData = ballData;
        canDamage = false;
    }

    public boolean canDamage() { return canDamage; }
    public void setDamage(boolean canDamage) { this.canDamage = canDamage; }

    public String toString() {
        return "Location: x "+ getXCoordinate() + " y " + getYCoordinate();
    }
}
