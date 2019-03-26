package com.anotherworld.view.graphics.spritesheet;

public enum SpriteLocation {
    NONE, TEXT, PLAYER, BALL;
    
    public static final int NUMBER_OF_LOCATIONS = 4;
    
    public int getInt() {
        switch(this) {
            case NONE:
                return 0;
            case TEXT:
                return 1;
            case PLAYER:
                return 2;
            case BALL:
                return 3;
            default:
                return 0;
        }
    }
}
