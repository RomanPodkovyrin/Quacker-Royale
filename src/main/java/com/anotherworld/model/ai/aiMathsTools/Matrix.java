package com.anotherworld.model.ai.aiMathsTools;

public class Matrix {
    private float x;
    private float y;

    public Matrix(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public String toString(){
        return "x: "+ x + ", y: " + y;
    }

    /**
     * [p1] <- x
     * [p2] <- y
     */
}
