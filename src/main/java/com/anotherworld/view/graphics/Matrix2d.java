package com.anotherworld.view.graphics;

/**
 * Creates and manipulates a 2d matrix
 * @author Jake Stewart
 *
 */
public class Matrix2d {

    private float[][] value;
    private int m;
    private int n;

    /**
     * Creates a 2d matrix with a height of m and a width of n.
     * @param m The height of the matrix must not be negative
     * @param n The width of the matrix must not be negative
     * @throws IndexOutOfBoundsException When m or n are less than 0
     */
    public Matrix2d(int m, int n) {
        if (m < 0 || n < 0) {
            throw new IndexOutOfBoundsException();
        }
        value = new float[m][n];
        this.m = m;
        this.n = n;
    }

    /**
     * Sets the value of a matrix cell.
     * @param i The "y" of the cell from 0 to m - 1
     * @param j The "x" of the cell from 0 to n - 1
     * @param v The new value for the cell location
     * @throws IndexOutOfBoundsException if i or j do not fall in the matrix
     */
    public void setValue(int i, int j, float v) {
        value[i][j] = v;
    }

    /**
     * Adds matrix b to matrix.
     * @param b The matrix to add to this one
     * @return This matrix (a) + b
     */
    @Deprecated
    public Matrix2d add(Matrix2d b) {
        assert (b.getM() == this.getM());
        assert (b.getN() == this.getN());
        Matrix2d result = new Matrix2d(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result.value[i][j] = this.value[i][j] + b.value[i][j];
            }
        }
        return result;
    }

    /**
     * Subtracts matrix b from matrix.
     * @param b The matrix to subtract to this one
     * @return This matrix (a) - b
     */
    @Deprecated
    public Matrix2d sub(Matrix2d b) {
        assert (b.getM() == this.getM());
        assert (b.getN() == this.getN());
        Matrix2d result = new Matrix2d(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; i < n; j++) {
                result.value[i][j] = this.value[i][j] - b.value[i][j];
            }
        }
        return result;
    }

    /**
     * Multiplies matrix b with matrix in form this * b.
     * @param b The matrix to multiply with this one
     * @return This matrix (a) * b
     */
    public Matrix2d mult(Matrix2d b) { // Multiplies this * b
        Matrix2d a = this;
        assert (a.getN() == b.getM());
        Matrix2d result = new Matrix2d(a.getM(), b.getN());
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                float v = 0f;
                for (int k = 0; k < a.getN(); k++) {
                    v += a.value[i][k] * b.value[k][j];
                }
                result.value[i][j] = v;
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
     * @throws IndexOutOfBoundsException if cell is not in matrix
     */
    public float getValue(int i, int j) {
        return value[i][j];
    }

    public static final Matrix2d genIdentity(int l) {
        Matrix2d result = new Matrix2d(l, l);
        for (int k = 0; k < l; k++) {
            result.value[k][k] = 1f;
        }
        return result;
    }

    public static final Matrix2d homRotation2d(float theta) {
        Matrix2d result = new Matrix2d(3, 3);
        theta = theta * (float) Math.PI / 180f;
        result.value[0][0] = (float) Math.cos(theta);
        result.value[1][0] = (float) Math.sin(theta);
        result.value[0][1] = -(float) Math.sin(theta);
        result.value[1][1] = (float) Math.cos(theta);
        result.value[2][2] = 1f;
        return result;
    }

    public static final Matrix2d homTranslation2d(float x, float y) {
        Matrix2d result = Matrix2d.genIdentity(3);
        result.value[0][2] = x;
        result.value[1][2] = y;
        return result;
    }

    public static final Matrix2d homScale2d(float x, float y) {
        Matrix2d result = new Matrix2d(3, 3);
        result.value[0][0] = x;
        result.value[1][1] = y;
        result.value[2][2] = 1;
        return result;
    }

    @Deprecated
    public static final Matrix2d testSquare() {
        return Matrix2d.testSquare(0.5f);
    }

    @Deprecated
    public static final Matrix2d testSquare(float s) {
        Matrix2d result = new Matrix2d(3, 4);
        result.value[0][0] = -s;
        result.value[1][0] = -s;
        result.value[0][1] = -s;
        result.value[1][1] = s;
        result.value[0][2] = s;
        result.value[1][2] = s;
        result.value[0][3] = s;
        result.value[1][3] = -s;
        for (int j = 0; j < result.getN(); j++) {
            result.value[2][j] = 1;
        }
        return result;
    }

    @Deprecated
    public void draw(String name) {
        System.out.println(name + ":");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(value[i][j] + (j < n - 1 ? "," : ""));
            }
            System.out.println();
        }
        System.out.println();
    }

}
