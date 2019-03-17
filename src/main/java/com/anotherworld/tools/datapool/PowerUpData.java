package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.enums.PowerUpType;
import com.anotherworld.view.data.PowerUpDisplayData;

import java.io.Serializable;

public class PowerUpData implements Serializable, PowerUpDisplayData {

    private float radius = 2; //TODO: Magic Number

    private Matrix coordinates;
    private PowerUpType powerUpType;
    private long spawnTime;

    private ObjectState state;

    public PowerUpData(Matrix coordinates, PowerUpType powerUpType, long spawnTime) {
        this.coordinates = coordinates;
        this.powerUpType = powerUpType;
        this.spawnTime   = spawnTime;
    }

    public Matrix getCoordinates() { return this.coordinates; }
    public float  getXCoordinate() { return this.coordinates.getX(); }
    public float  getYCoordinate() { return this.coordinates.getY(); }

    public float  getRadius() { return this.radius; }

    public PowerUpType getPowerUpType() { return this.powerUpType; }

    public long getSpawnTime() { return this.spawnTime; }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return this.state;
    }

    public void setState(ObjectState state) { this.state = state; }

    @Override
    public Matrix getVelocity() {
        return new Matrix(0, 0);
    }

}
