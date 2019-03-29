package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.BallDisplayData;

import java.io.Serializable;

/**
 * Class that represents the positions, states, and timers of a ball.
 *
 * @author Alfi S.
 */
public class BallData extends MovableData implements BallDisplayData, Serializable {

    private boolean dangerous;
    private int timer;
    private int damage = 2;

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

    /**
     * Gets the amount of damage the ball deals upon impact
     * when it is in the dangerous state.
     * @return the amount of damage the dall can deal.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Checks whether the ball can damage a player.
     * @return true if the ball is dangerous, false otherwise.
     */
    public boolean isDangerous() {
        return dangerous;
    }

    /**
     * Sets whether the ball can damage a player.
     * @param dangerous boolean determining whether or not the ball is dangerous.
     */
    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }

    /**
     * Gets the timer of the ball that determines how long the ball
     * remains in its dangerous state.
     * @return the timer of the ball.
     */
    public int getTimer() {
        return timer;
    }

    /**
     * Sets the timer of the ball that determines how long that ball
     * remains in its dangerous state.
     * @param time the amount of time the ball would be in the dangerous state.
     */
    public void setTimer(int time) {
        this.timer = time;
    }
}
