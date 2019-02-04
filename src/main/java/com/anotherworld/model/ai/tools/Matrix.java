package com.anotherworld.model.ai.tools;

/**
 *
 * @author Roman P
 */
public class Matrix {
    private float x;
    private float y;

    /**
     *
     * @param x the x value of the Matrix
     * @param y the y value of the Matrix
     */
    public Matrix(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Multiplies the Matrix by the scalar value
     * @param n  scalar value to multiply the Matrix
     * @return Matrix the result of scalar multiplication
     */
    public Matrix mult(float n){

        return new Matrix(x * n, y * n);
    }

    /**
     * Adds two matrices together
     * @param n the matrix to add to the current matrix
     * @return Matrix the result of the addition
     */
    public Matrix add(Matrix n){

        return new Matrix(n.getX() + x, n.getY() + y);
    }

    /**
     * subtract two matrices
     * @param n the matrix to be subtracted from the matric
     * @return Matrix the result of the subtraction
     */
    public Matrix sub(Matrix n){
        return new Matrix(x - n.getX(), y - n.getY());
    }

    /**
     * Divide Matrix by a scalar
     * @param n scalar value to divide the Matrix
     * @return Matrix the result of scalar division
     */
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
