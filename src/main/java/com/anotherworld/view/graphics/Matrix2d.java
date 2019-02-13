package com.anotherworld.view.graphics;

/**
 * Creates and manipulates a 2d matrix.
 * @author Jake Stewart
 *
 */
public class Matrix2d {

    private float[] value;
    private int m;
    private int n;

    /**
     * Creates a 2d matrix with a height of m and a width of n.
     * @param m The height of the matrix must not be negative
     * @param n The width of the matrix must not be negative
     * @throws MatrixSizeException When m or n are less than 0
     */
    public Matrix2d(int m, int n) {
        if (m < 0 || n < 0) {
            throw new MatrixSizeException("Size must be non negative");
        }
        value = new float[m * n];
        this.m = m;
        this.n = n;
    }

    /**
     * Sets the value of a matrix cell.
     * @param i The "y" of the cell from 0 to m - 1
     * @param j The "x" of the cell from 0 to n - 1
     * @param v The new value for the cell location
     * @throws MatrixSizeException If i or j do not fall in the matrix
     */
    public void setValue(int i, int j, float v) {
        if (i < 0 || j < 0 || i >= this.getM() || j >= this.getN()) {
            throw new MatrixSizeException("Cell not in matrix");
        }
        value[i + j * m] = v;
    }

    /**
     * Adds matrix b to matrix.
     * @param b The matrix to add to this one
     * @return This matrix (a) + b
     * @throws MatrixSizeException If the two matrices are of different size
     */
    @Deprecated
    public Matrix2d add(Matrix2d b) {
        if (b.getM() != this.getM() || b.getN() != this.getN()) {
            throw new MatrixSizeException("Matrix a and b are of different size");
        }
        Matrix2d result = new Matrix2d(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.value[i + j * m] = this.value[i + j * m] + b.value[i + j * m];
            }
        }
        return result;
    }

    /**
     * Subtracts matrix b from matrix.
     * @param b The matrix to subtract to this one
     * @return This matrix (a) - b
     * @throws MatrixSizeException If the two matrices are of different size
     */
    @Deprecated
    public Matrix2d sub(Matrix2d b) {
        if (b.getM() != this.getM() || b.getN() != this.getN()) {
            throw new MatrixSizeException("Matrix a and b are of different size");
        }
        Matrix2d result = new Matrix2d(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; i < n; j++) {
                result.value[i + j * m] = this.value[i + j * m] - b.value[i + j * m];
            }
        }
        return result;
    }

    /**
     * Multiplies matrix b with matrix in form this * b.
     * @param b The matrix to multiply with this one
     * @return This matrix (a) * b
     * @throws MatrixSizeException When it is impossible to multiply the two matrices together
     */
    public Matrix2d mult(Matrix2d b) { // Multiplies this * b
        Matrix2d a = this;
        if (a.getN() != b.getM()) {
            throw new MatrixSizeException("Matrix a and b are of incompatible sizes");
        }
        Matrix2d result = new Matrix2d(a.getM(), b.getN());
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                float v = 0f;
                for (int k = 0; k < a.getN(); k++) {
                    v += a.value[i + k * m] * b.value[k + j * m];
                }
                result.value[i + j * m] = v;
            }
        }
        return result;
    }

    /**
     * Returns the "height" of the matrix.
     * @return the "height" m
     */
    public int getM() {
        return m;
    }

    /**
     * Returns the "width" of the matrix.
     * @return the "width" n
     */
    public int getN() {
        return n;
    }

    /**
     * Returns the value stored in a cell.
     * @param i the "y" of the cell
     * @param j the "x" of the cell
     * @return the value of i, j
     * @throws MatrixSizeException if cell is not in matrix
     */
    public float getValue(int i, int j) {
        if (i < 0 || j < 0 || i >= this.getM() || j >= this.getN()) {
            throw new MatrixSizeException("Cell not in matrix");
        }
        return value[i + j * m];
    }

    /**
     * Creates an l by l identity matrix.
     * @param l the size of the matrix
     * @return An l by l identity matrix
     */
    public static final Matrix2d genIdentity(int l) {
        Matrix2d result = new Matrix2d(l, l);
        for (int k = 0; k < l; k++) {
            result.value[k + k * l] = 1f;
        }
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that rotates a point theta degrees clockwise around the origin.
     * @param theta The angle in degrees
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homRotation2d(float theta) {
        Matrix2d result = new Matrix2d(3, 3);
        theta = theta * (float) Math.PI / 180f;
        result.value[0] = (float) Math.cos(theta);
        result.value[1] = (float) Math.sin(theta);
        result.value[3] = -(float) Math.sin(theta);
        result.value[4] = (float) Math.cos(theta);
        result.value[8] = 1f;
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that translates a point by x and y.
     * @param x The translation in the x-axis
     * @param y The translation in the y-axis
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homTranslation2d(float x, float y) {
        Matrix2d result = Matrix2d.genIdentity(3);
        result.value[6] = x;
        result.value[7] = y;
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that scales a point by x and y from the origin.
     * @param x The scale in the x-axis
     * @param y The scale in the y-axis
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homScale2d(float x, float y) {
        Matrix2d result = new Matrix2d(3, 3);
        result.value[0] = x;
        result.value[4] = y;
        result.value[8] = 1;
        return result;
    }
    
    @Override
    public String toString() {
        String r = "";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                r = r + (value[i * m + j] + (j < n - 1 ? "," : "\n"));
            }
        }
        return r;
    }

}
