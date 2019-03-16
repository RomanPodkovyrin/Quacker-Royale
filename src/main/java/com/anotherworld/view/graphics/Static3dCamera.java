package com.anotherworld.view.graphics;

import com.anotherworld.view.data.Points2d;

public class Static3dCamera extends Static2dCamera {
    
    public Static3dCamera(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    @Override
    public float getY() {
        return super.getY() * 2;
    }

    @Override
    public float getZ() {
        return -super.getY();
    }

    @Override
    public float getDepth() { //Don't ask
        return 1.414213562f;
    }

    @Override
    public Points2d getViewDirection() {
        Points2d tempUp = new Points2d(3, 1);
        tempUp.setValue(1, 0, -1f);
        tempUp.setValue(2, 0, 1f);
        return tempUp;
    }

}
