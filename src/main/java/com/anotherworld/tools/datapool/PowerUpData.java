package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.enums.PowerUpType;
import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.view.data.PowerUpDisplayData;

import java.io.Serializable;

/**
 * Class that represents information about a power up within the game.
 *
 * @author Alfi S.
 */
public class PowerUpData implements Serializable, PowerUpDisplayData {

    private float radius = 3; //TODO: Magic Number

    private Matrix coordinates;
    private PowerUpType powerUpType;
    private long spawnTime;

    private ObjectState state;

    /**
     * Class constructor for power ups.
     * @param coordinates the coordinates of the power up.
     * @param powerUpType the type of the power up.
     * @param spawnTime the time the power up should spawn.
     */
    public PowerUpData(Matrix coordinates, PowerUpType powerUpType, long spawnTime) {
        this.coordinates = coordinates;
        this.powerUpType = powerUpType;
        this.spawnTime   = spawnTime;
        this.state = ObjectState.INACTIVE;
    }

    /**
     * Used to copy the given object into the current one.
     * @param data data to be copied.
     */
    public void copyObject(PowerUpData data) {
        this.coordinates = data.coordinates;
        this.powerUpType = data.powerUpType;
        this.spawnTime   = data.spawnTime;
        this.state       = data.state;
    }

    /**
     * Gets the coordinates of the power up.
     * @return the coordinates of the power up as a matrix.
     */
    public Matrix getCoordinates() {
        return this.coordinates;
    }

    /**
     * Gets the x coordinate of the power up.
     * @return the x coordinate of the power up.
     */
    public float  getXCoordinate() {
        return this.coordinates.getX();
    }

    /**
     * Gets the y coordinate of the power up.
     * @return the y coordinate of the power up.
     */
    public float  getYCoordinate() {
        return this.coordinates.getY();
    }

    /**
     * Gets the radius of the power up.
     * @return the radius of the power up.
     */
    public float  getRadius() {
        return this.radius;
    }

    /**
     * Gets the type of the power up.
     * @return the type of the power up.
     */
    public PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

    /**
     * Gets the time the power up is meant to be spawned.
     * @return the spawn time of the power up.
     */
    public long getSpawnTime() {
        return this.spawnTime;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return this.state;
    }

    public void setState(ObjectState state) {
        this.state = state;
    }

    @Override
    public Matrix getVelocity() {
        return new Matrix(0, 0);
    }

    @Override
    public String toString() {
        return "("
                + this.powerUpType.toString() + ", "
                + "x:" + this.coordinates.getX() + ", "
                + "y:" + this.coordinates.getY() + ", "
                + "state: " + this.getState().toString() + ")";
    }
}
