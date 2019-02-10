package com.anotherworld.tools.datapool;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.BallDisplayData;

public class BallData extends MovableData implements BallDisplayData {

    boolean dangerous;

    public BallData(boolean dangerous,
                    float xCoordinate, float yCoordinate,
                    ObjectState state, float speed, float radius) {
        super(xCoordinate, yCoordinate, state, speed, radius);
        this.dangerous = dangerous;
    }

    public boolean isDangerous() { return dangerous; }
    public void setDangerous(boolean dangerous) { this.dangerous = dangerous; }
}
