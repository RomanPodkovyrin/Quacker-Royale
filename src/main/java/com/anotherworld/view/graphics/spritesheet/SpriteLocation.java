package com.anotherworld.view.graphics.spritesheet;

public enum SpriteLocation {
    NONE, TEXT, PLAYER, BALL, POWER_UP;
    
    public static final int NUMBER_OF_LOCATIONS = 5;
    
    /**
     * Returns a unique int for each sprite location
     * @return
     */
    public int getInt() {
        switch (this) {
            case NONE:
                return 0;
            case TEXT:
                return 1;
            case PLAYER:
                return 2;
            case BALL:
                return 3;
            case POWER_UP:
                return 4;
            default:
                return 0;
        }
    }
}
