package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.BallDisplayData;

import java.io.Serializable;

public class BallData extends MovableData implements BallDisplayData, Serializable {

    private boolean dangerous;
    private int timer;
    private int damage = 2; // TODO: Create damage in the constructor.

    /**
     * Class contractor for the generic ball data.
     * @param objectID - id of the ball
     * @param dangerous - danger status of the ball
     * @param xCoordinate - x coordinate
     * @param yCoordinate - y coordinate
     * @param state - state of the ball
     * @param speed - speed of the ball
     * @param radius - radius of the ball
     */
    public BallData(String objectID, boolean dangerous, float xCoordinate, float yCoordinate,
                    ObjectState state, float speed, float radius) {
        super(objectID, xCoordinate, yCoordinate, state, speed, radius);
        this.dangerous = dangerous;
        this.timer = 0;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied
     */
    public void copyObject(BallData data) {
        this.dangerous = data.isDangerous();
        this.timer = data.getTimer();
        this.damage = data.getDamage();
        super.copyObject(data);
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isDangerous() {
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int time) {
        this.timer = time;
    }
}
