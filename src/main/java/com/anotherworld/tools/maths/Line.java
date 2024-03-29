package com.anotherworld.tools.maths;

/**
 * Represents a line in the form of
 * a*x1 + b*x2 = d.
 *
 * @author Roman P
 */
public class Line {
    private Matrix point;
    private float s;
    private Matrix vector;


    private float a;
    private float x1;
    private float b;
    private float x2;
    private float d;

    public Matrix getPoint() {
        return point;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getD() {
        return d;
    }

    /**
     * Takes P + s V where p is a point
     * s is a scalar and V is a vector.
     *
     * @param point point of origin
     * @param s scale of the line
     * @param vector vector coming from the point
     */
    public Line(Matrix point, float s, Matrix vector) {
        this.point = point;
        this.s = s;
        this.vector = vector;

        this.a = - this.vector.getY();
        this.b = this.vector.getX();
        this.d = (this.a * this.point.getX()) + (this.b * this.point.getY());

    }

    /**
     * Takes P + V where p is a point and V is a vector.
     *
     * @param point point of origin
     * @param vector vector coming from the point
     */
    public Line(Matrix point,Matrix vector) {
        this.point = point;
        this.vector = vector;

        this.a = - this.vector.getY();
        this.b = this.vector.getX();
        this.d = (this.a * this.point.getX()) + (this.b * this.point.getY());

    }

    /**
     * Returns the normal vector to the line.
     *
     * @return normal vector
     */
    public Matrix getOrthogonalVector() {

        return new Matrix(this.a, this.b);
    }

    public String toString() {
        return  a + "*x1 + " + b + "*x2 = " + d;
    }
}
