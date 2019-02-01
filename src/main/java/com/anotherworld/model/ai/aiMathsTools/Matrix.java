package com.anotherworld.model.ai.aiMathsTools;

public class Matrix {
    private float x;
    private float y;

    public Matrix(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Matrix mult(float n){

        return new Matrix(x * n, y * n);
    }

    public Matrix add(Matrix n){

        return new Matrix(n.getX() + x, n.getY() + y);
    }

    public Matrix sub(Matrix n){
        return new Matrix(x - n.getX(), y - n.getY());
    }

    public Matrix div(float n){
        return new Matrix(x / n, y / n);
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
