package com.anotherworld.view;

public class Player extends DisplayObject {

    private static final float PLAYER_SIZE = 2;

    private static final Matrix2d PLAYER_MODEL = Matrix2d.TEST_SQUARE(PLAYER_SIZE);

    public Player(float x, float y, float theta) {
        super(PLAYER_MODEL, x, y, theta);
    }

}
