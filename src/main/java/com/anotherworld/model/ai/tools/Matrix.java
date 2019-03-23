package com.anotherworld.model.ai.tools;

import com.anotherworld.tools.maths.Maths;

import java.io.Serializable;

/**
 * This class hold x and y values to represent a point or vector.*
 * [p1] <- x
 * [p2] <- y
 *
 *
 * @author Roman P
 */
public class Matrix implements Serializable {
    private float x;
    private float y;

    /**
     *Initialises the class.
     *
     * @param x the x value of the Matrix
     * @param y the y value of the Matrix
     */
    public Matrix(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Multiplies the Matrix by the scalar value.
     *
     * @param n  scalar value to multiply the Matrix
     * @return Matrix the result of scalar multiplication
     */
    public Matrix mult(float n) {

        return new Matrix(x * n, y * n);
    }

    /**
     * Adds two matrices together.
     *
     * @param n the matrix to add to the current matrix
     * @return Matrix the result of the addition
     */
    public Matrix add(Matrix n) {

        return new Matrix(n.getX() + x, n.getY() + y);
    }

    /**
     * Subtract two matrices.
     *
     * @param n the matrix to be subtracted from the matrix
     * @return Matrix the result of the subtraction
     */
    public Matrix sub(Matrix n) {
        return new Matrix(x - n.getX(), y - n.getY());
    }

    /**
     * Normalizes the vector returns a new instance without modifying the current one.
     *
     * @return returns normalized vector
     */
    public Matrix normalize() {

        float magnitude = this.magnitude();
        return new Matrix(Maths.floatDivision(x, magnitude),Maths.floatDivision(y, magnitude));
    }

    /**
     * Normalizes the vector by modifying the current one.
     */
    public void normalizeThis() {

        float magnitude = this.magnitude();
        x = Maths.floatDivision(x, magnitude);
        y = Maths.floatDivision(y, magnitude);
    }
    
    /**
     * Returns the magnitude of the vector.
     * @return the magnitude
     */
    public float magnitude() {
        return MatrixMath.magnitude(this);
    }

    /**
     * Divide Matrix by a scalar.
     *
     * @param n scalar value to divide the Matrix
     * @return Matrix the result of scalar division
     */
    public Matrix div(float n) {
        return new Matrix(Maths.floatDivision(x, n), Maths.floatDivision(y, n));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        return "x: " + x + ", y: " + y;
    }

}
