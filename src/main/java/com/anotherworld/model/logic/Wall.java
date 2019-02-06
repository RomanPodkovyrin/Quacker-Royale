package com.anotherworld.model.logic;

/**
 * Represents the wall object.
 *
 * @ Roman
 */
public class Wall {
    private int stage = 1;
    private static int MAXSTAGE = 4;
    private float xShrink = 10;
    private float yShrink = 20;
    private float xSize = 140;
    private float ySize = 70;

    private float xCoordinate;
    private float yCoordinate;

    public Wall(float x, float y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * Called when the wall needs to shrink.
     */
    public void nextStage(){
        if (stage < MAXSTAGE ){
            stage ++;
            this.xSize =-xShrink;
            this.ySize =-yShrink;

        }
    }

}
