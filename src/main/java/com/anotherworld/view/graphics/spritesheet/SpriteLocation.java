package com.anotherworld.view.graphics.spritesheet;

public enum SpriteLocation {
    NONE, TEXT, PLAYER, POWER_UP, POLICE_HAT, PIRATE_HAT, ROBBER_HAT, SANTA_HAT, SAFE_BALL, DANGEROUS_BALL, BACKGROUND,
    INSTRUCTIONS, TILE, ARROW;
    
    public static final int NUMBER_OF_LOCATIONS = 14;
    
    /**
     * Returns a unique int for each sprite location.
     * @return returns the unique int location
     */
    public int getInt() {
        switch (this) {
            case NONE:
                return 0;
            case TEXT:
                return 1;
            case PLAYER:
                return 2;
            case SAFE_BALL:
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
            case DANGEROUS_BALL:
                return 9;
            case BACKGROUND:
                return 10;
            case INSTRUCTIONS:
                return 11;
            case TILE:
                return 12;
            case ARROW:
                return 13;
            default:
                return 0;
        }
    }
}
