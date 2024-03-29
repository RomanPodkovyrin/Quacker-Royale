package com.anotherworld.view.data.primatives;

/**
 * Stores an array of points that define an object.
 * @author Jake Stewart
 *
 */
public class Points2d {

    private float[] value;
    private int m;
    private int n;

    /**
     * Creates a 2d points object with a height of m and a width of n.
     * @param m The number of dimensions of the points must not be negative
     * @param n The number of points
     * @throws MatrixSizeException When m or n are less than 0
     */
    public Points2d(int m, int n) {
        if (m < 0 || n < 0) {
            throw new IndexOutOfBoundsException("Size must be non negative");
        }
        value = new float[m * n];
        this.m = m;
        this.n = n;
    }

    /**
     * Sets the value of a point cell.
     * @param i The "y" of the cell from 0 to m - 1
     * @param j The "x" of the cell from 0 to n - 1
     * @param v The new value for the cell location
     * @throws MatrixSizeException If i or j do not fall in the points
     */
    public void setValue(int i, int j, float v) {
        if (i < 0 || j < 0 || i >= this.getM() || j >= this.getN()) {
            throw new IndexOutOfBoundsException("Cell not in matrix");
        }
        value[i + j * m] = v;
    }

    /**
     * Returns the value stored in a cell.
     * @param i the "y" of the cell
     * @param j the "x" of the cell
     * @return the value of i, j
     * @throws MatrixSizeException if cell is not in points
     */
    public float getValue(int i, int j) {
        if (i < 0 || j < 0 || i >= this.getM() || j >= this.getN()) {
            throw new IndexOutOfBoundsException("Cell not in matrix");
        }
        return value[i + j * m];
    }
    
    public float[] getPoints() {
        return value;
    }

    /**
     * Returns the number of dimensions for each point.
     * @return the number of dimensions
     */
    public int getM() {
        return m;
    }

    /**
     * Returns the number of points.
     * @return the number of points
     */
    public int getN() {
        return n;
    }
    
    @Override
    public String toString() {
        String r = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r = r + (value[i + j * m ] + (j < n - 1 ? "," : "\n"));
            }
        }
        return r;
    }
    
    /**
     * Generates the points of a wall with thickness adding to the outside of the object.
     * @param w The width of the wall
     * @param h The height of the wall
     * @param t The thickness of the wall
     * @return The wall's points
     */
    public static final Points2d genWall(float w, float h, float t) {
        Points2d points = new Points2d(4, 10);
        points.setValue(0, 0, -w / 2 - t);
        points.setValue(1, 0, h / 2 + t);
        points.setValue(0, 1, -w / 2);
        points.setValue(1, 1, h / 2);
        points.setValue(0, 2, w / 2 + t);
        points.setValue(1, 2, h / 2 + t);
        points.setValue(0, 3, w / 2);
        points.setValue(1, 3, h / 2);
        points.setValue(0, 4, w / 2 + t);
        points.setValue(1, 4, -h / 2 - t);
        points.setValue(0, 5, w / 2);
        points.setValue(1, 5, -h / 2);
        points.setValue(0, 6, -w / 2 - t);
        points.setValue(1, 6, -h / 2 - t);
        points.setValue(0, 7, -w / 2);
        points.setValue(1, 7, -h / 2);
        points.setValue(0, 8, -w / 2 - t);
        points.setValue(1, 8, h / 2 + t);
        points.setValue(0, 9, -w / 2);
        points.setValue(1, 9, h / 2);
        for (int j = 0; j < 10; j++) {
            points.setValue(3, j, 1f);
        }
        return points;
    }
    
    /**
     * Generates the points of a rectangle.
     * @param w The width of the rectangle
     * @param h The height of the rectangle
     * @return The points of the rectangle
     */
    public static final Points2d genRectangle(float w, float h) {
        Points2d points = new Points2d(4, 4);
        points.setValue(0, 0, -w / 2);
        points.setValue(1, 0, h / 2);
        points.setValue(0, 1, w / 2);
        points.setValue(1, 1, h / 2);
        points.setValue(0, 2, w / 2);
        points.setValue(1, 2, -h / 2);
        points.setValue(0, 3, -w / 2);
        points.setValue(1, 3, -h / 2);
        
        for (int j = 0; j < 4; j++) {
            points.setValue(3, j, 1f);
        }
        return points;
    }
    
    /**
     * Generates the points of a rectangle.
     * @param w The width of the rectangle
     * @param h The height of the rectangle
     * @return The points of the rectangle
     */
    public static final Points2d gen3DRectangle(float w, float h) {
        Points2d points = new Points2d(4, 4);
        points.setValue(0, 0, -w / 2);
        points.setValue(2, 0, h / 2);
        points.setValue(0, 1, w / 2);
        points.setValue(2, 1, h / 2);
        points.setValue(0, 2, w / 2);
        points.setValue(2, 2, -h / 2);
        points.setValue(0, 3, -w / 2);
        points.setValue(2, 3, -h / 2);
        
        for (int j = 0; j < 4; j++) {
            points.setValue(3, j, 1f);
        }
        return points;
    }
    
    /**
     * Generates the points of a circle.
     * @param r The radius of the circle
     * @return The points of the circle
     */
    public static final Points2d gen3DCircle(float r) {
        Points2d points = new Points2d(4, 38);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(3, 0, 1f);
        for (int i = 0; i <= 36; i += 1) {
            points.setValue(0, i + 1, r * (float)(Math.sin(((double)i / 18) * Math.PI)));
            points.setValue(2, i + 1, r * (float)(Math.cos(((double)i / 18) * Math.PI)));
            points.setValue(3, i + 1, 1f);
        }
        return points;
    }
    
    /**
     * Generates the points of a circle.
     * @param r The radius of the circle
     * @return The points of the circle
     */
    public static final Points2d genCircle(float r) {
        Points2d points = new Points2d(4, 38);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(3, 0, 1f);
        for (int i = 0; i <= 36; i += 1) {
            points.setValue(0, i + 1, r * (float)(Math.sin(((double)i / 18) * Math.PI)));
            points.setValue(1, i + 1, r * (float)(Math.cos(((double)i / 18) * Math.PI)));
            points.setValue(3, i + 1, 1f);
        }
        return points;
    }

    /**
     * Computes the cross product of two vectors iff they have an m value of 3 and n value of 1.
     * @param b The vector to compute cross product with
     * @return A new vector containing the cross product
     */
    public Points2d crossProduct(Points2d b) {
        if (!(this.getM() == 3 && this.getN() == 1 && b.getM() == 3 && b.getN() == 1)) {
            throw new IndexOutOfBoundsException("Cannot compute cross product");
        }
        Points2d result = new Points2d(3, 1);
        result.setValue(0, 0, this.getValue(1, 0) * b.getValue(2, 0) - this.getValue(2, 0) * b.getValue(1, 0));
        result.setValue(1, 0, this.getValue(2, 0) * b.getValue(0, 0) - this.getValue(0, 0) * b.getValue(2, 0));
        result.setValue(2, 0, this.getValue(0, 0) * b.getValue(1, 0) - this.getValue(1, 0) * b.getValue(0, 0));
        return result;
    }
    
    /**
     * Normalises a Points2d so it's magnitude is 1.
     * @return the normalised points
     */
    public Points2d normalise() {
        float sum = 0f;
        for (int i = 0; i < this.value.length; i++) {
            sum += this.value[i] * this.value[i];
        }
        sum = (float)Math.sqrt(sum);
        Points2d result = new Points2d(this.getM(), this.getN());
        if (sum != 0) {
            for (int i = 0; i < result.value.length; i++) {
                result.value[i] = this.value[i] / sum;
            }
        }
        return result;
    }
    
}
