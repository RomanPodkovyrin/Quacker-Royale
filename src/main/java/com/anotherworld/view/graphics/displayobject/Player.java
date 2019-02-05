package com.anotherworld.view.graphics.displayobject;

import com.anotherworld.view.graphics.Matrix2d;

public class Player extends DisplayObject {

    private static final float PLAYER_SIZE = 2;

    private static final Matrix2d PLAYER_MODEL = Matrix2d.testSquare(PLAYER_SIZE);

    public Player(float x, float y, float theta) {
        super(PLAYER_MODEL, x, y, theta);
    }

}
