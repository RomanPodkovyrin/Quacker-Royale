package com.anotherworld.view.graphics.spritesheet;

public enum SpriteLocation {
    NONE, TEXT, PLAYER, BALL, HEAL_POWERUP, SHIELD_POWERUP, TIME_STOP_POWERUP;
    
    public static final int NUMBER_OF_LOCATIONS = 7;
    
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
            case HEAL_POWERUP:
                return 4;
            case SHIELD_POWERUP:
                return 5;
            case TIME_STOP_POWERUP:
                return 6;
            default:
                return 0;
        }
    }
}
