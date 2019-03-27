package com.anotherworld.view.data;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.PowerUpSpriteSheet;
import com.anotherworld.view.programme.Programme;

public class PowerUpDisplayObject extends DisplayObject {

    private final PowerUpData displayData;

    /**
     * Creates a Display Object used to store a power up.
     * @param programme The programme used to render the powerup
     * @param displayData The data used to render the power up
     */
    public PowerUpDisplayObject(Programme programme, PowerUpData displayData) {
        super(new PowerUpSpriteSheet(displayData), programme, programme.supportsTextures() ?
                Points2d.genRectangle(displayData.getRadius() * 2, displayData.getRadius() * 2) : Points2d.genCircle(displayData.getRadius()),
                DrawType.TRIANGLE_FAN, 1, 1, 1);
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
        if(this.displayData.getState() == ObjectState.ACTIVE) {
            System.out.println("SHOULD DRAW " + displayData);
        }
        return this.displayData.getState() == ObjectState.ACTIVE;
    }

    @Override
    public boolean shouldCameraFollow() {
        return false;
    }
}
