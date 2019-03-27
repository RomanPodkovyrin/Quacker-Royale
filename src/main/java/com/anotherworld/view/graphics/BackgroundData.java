package com.anotherworld.view.graphics;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.BackgroundDisplayData;
import com.anotherworld.view.graphics.spritesheet.RectangleSpriteSheet;
import com.anotherworld.view.graphics.spritesheet.SpriteLocation;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;

public class BackgroundData implements BackgroundDisplayData {

    private float x;
    private float y;
    private float h;
    private float w;
    private SpriteSheet spriteSheet;
    
    public BackgroundData(float x, float y, float h, float w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        spriteSheet = new RectangleSpriteSheet(SpriteLocation.BACKGROUND, 10, 1000f / 12f);
    }
    
    @Override
    public float getWidth() {
        return w;
    }

    @Override
    public float getHeight() {
        return h;
    }

    @Override
    public float getXCoordinate() {
        return x;
    }

    @Override
    public float getYCoordinate() {
        return y;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return ObjectState.IDLE;
    }

    @Override
    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

}
