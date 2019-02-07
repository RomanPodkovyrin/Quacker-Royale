package com.anotherworld.tools.datapool;

import com.anotherworld.view.data.BallDisplayData;

public class BallData extends MovableData implements BallDisplayData {
    boolean dangerous;

    public boolean isDangerous() { return dangerous; }
    public void setDangerous(boolean dangerous) { this.dangerous = dangerous; }
}
