package com.anotherworld.tools.datapool;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.tools.enums.PowerUpType;

import java.io.Serializable;

public class PowerUpData implements Serializable {

    private float radius = 2; //TODO: Magic Number

    private Matrix coordinates;
    private PowerUpType powerUpType;
    private long spawnTime;

    public PowerUpData(Matrix coordinates, PowerUpType powerUpType, long spawnTime) {
        this.coordinates = coordinates;
        this.powerUpType = powerUpType;
        this.spawnTime   = spawnTime;
    }

    public Matrix getCoordinates() { return this.coordinates; }
    public float  getXCoordinate() { return this.coordinates.getX(); }
    public float  getYCoordinate() { return this.coordinates.getY(); }

    public PowerUpType getPowerUpType() { return this.powerUpType; }

    public long getSpawnTime() { return this.spawnTime; }
}
