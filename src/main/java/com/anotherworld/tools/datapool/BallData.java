package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.BallDisplayData;

import java.io.Serializable;

public class BallData extends MovableData implements BallDisplayData, Serializable {

    /**
     * The amount of frames the ball will stay dangerous.
     */
    public static final int MAX_TIMER = 2000;

    private boolean dangerous;
    private int timer;
    private int damage = 10; // TODO: Create damage in the constructor.

    public BallData(boolean dangerous,
                    float xCoordinate, float yCoordinate,
                    ObjectState state, float speed, float radius) {
        super(xCoordinate, yCoordinate, state, speed, radius);
        this.dangerous = dangerous;
        this.timer = 0;
    }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public boolean isDangerous() { return dangerous; }
    public void setDangerous(boolean dangerous) { this.dangerous = dangerous; }

    public int getTimer() { return timer; }
    public void setTimer(int time) { this.timer = time; }
    public void decrementTimer() { this.timer--; }
}
