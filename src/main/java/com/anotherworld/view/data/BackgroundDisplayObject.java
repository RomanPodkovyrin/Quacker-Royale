package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.programme.Programme;

public class BackgroundDisplayObject extends DisplayObject {
    
    private BackgroundDisplayData displayData;

    public BackgroundDisplayObject(Programme programme, BackgroundDisplayData displayData) {
        super(displayData.getSpriteSheet(), programme, Points2d.genRectangle(displayData.getWidth(), displayData.getHeight()), DrawType.TRIANGLE_FAN, 1, 1, 1);
        this.displayData = displayData;
    }

    @Override
    public float getTheta() {
        return displayData.getAngle();
    }

    @Override
    public float getX() {
        return displayData.getXCoordinate();
    }

    @Override
    public float getY() {
        return displayData.getYCoordinate();
    }

    @Override
    public float getZ() {
        return 0;
    }

    @Override
    public boolean shouldDraw() {
        return true;
    }

    @Override
    public boolean shouldCameraFollow() {
        return false;
    }

}
