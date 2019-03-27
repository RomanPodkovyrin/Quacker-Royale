package com.anotherworld.view.graphics.spritesheet;

public enum SpriteLocation {
    NONE, TEXT, PLAYER, BALL, POWER_UP, POLICE_HAT, PIRATE_HAT, ROBBER_HAT, SANTA_HAT;
    
    public static final int NUMBER_OF_LOCATIONS = 9;
    
    /**
     * Returns a unique int for each sprite location.
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
            case POLICE_HAT:
                return 5;
            case PIRATE_HAT:
                return 6;
            case ROBBER_HAT:
                return 7;
            case SANTA_HAT:
                return 8;
            default:
                return 0;
        }
    }
}
