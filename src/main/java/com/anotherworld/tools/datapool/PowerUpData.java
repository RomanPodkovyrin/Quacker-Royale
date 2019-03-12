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

    private Matrix getCoordinates() { return this.coordinates; }
    private float getXCoordinate()  { return this.coordinates.getX(); }
    private float getYCoordinate()  { return this.coordinates.getY(); }

    private PowerUpType getPowerUpType() { return this.powerUpType; }

    private long spawnTime() { return this.spawnTime; }
}
