package com.anotherworld.model.movable;

import com.anotherworld.tools.datapool.BallData;

public class Ball extends AbstractMovable {

    private BallData ballData;

    public Ball(BallData ballData){
        super(ballData);
        this.ballData = ballData;
    }

    public int getDamage() { return ballData.getDamage(); }

    public boolean isDangerous() { return ballData.isDangerous(); }
    public void setDangerous(boolean dangerous) { ballData.setDangerous(dangerous); }

    public int getTimer() { return ballData.getTimer(); }
    public void setTimer(int time) { ballData.setTimer(time); }
    public void decrementTimer() { ballData.decrementTimer(); }

    public String toString() {
        return "Location: x "+ getXCoordinate() + " y " + getYCoordinate();
    }
}
