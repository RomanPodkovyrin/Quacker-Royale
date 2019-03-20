package com.anotherworld.view.data;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PowerUpData;
import com.anotherworld.view.Programme;
import org.lwjgl.opengl.GL46;

public class PowerUpDisplayObject extends DisplayObject{

    private final GameSessionData gameData;
    private final PowerUpData displayData;

    public PowerUpDisplayObject(Programme programme, GameSessionData gameData, int i) {
        super(programme, Points2d.genCircle(gameData.getPowerUpSchedule().get(i).getRadius()), GL46.GL_TRIANGLE_FAN);
        this.gameData= gameData;
        this.displayData = gameData.getPowerUpSchedule().get(i);
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

        return this.displayData.getState() != ObjectState.INACTIVE &&
                this.displayData.getSpawnTime() > this.gameData.getTimeLeft()+1;

    }

    @Override
    public boolean shouldCameraFollow() {
        return false;
    }
}
