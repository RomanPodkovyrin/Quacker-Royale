package com.anotherworld.view.data.primatives;

/**
 * Creates and manipulates a 2d matrix.
 * @author Jake Stewart
 *
 */
public class Matrix2d extends Points2d {

    /**
     * Creates a 2d matrix with a height of m and a width of n.
     * @param m The height of the matrix must not be negative
     * @param n The width of the matrix must not be negative
     * @throws MatrixSizeException When m or n are less than 0
     */
    public Matrix2d(int m, int n) {
        super(m, n);
    }

    /**
     * Adds matrix b to matrix.
     * @param b The matrix to add to this one
     * @return This matrix (a) + b
     * @throws MatrixSizeException If the two matrices are of different size
     */
    public Matrix2d add(Points2d b) {
        if (b.getM() != this.getM() || b.getN() != this.getN()) {
            throw new MatrixSizeException("Matrix a and b are of different size");
        }
        Matrix2d result = new Matrix2d(this.getM(), this.getN());

        for (int i = 0; i < this.getM(); i++) {
            for (int j = 0; j < this.getN(); j++) {
                result.setValue(i, j, this.getValue(i, j) + b.getValue(i, j));
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
    public Matrix2d sub(Points2d b) {
        if (b.getM() != this.getM() || b.getN() != this.getN()) {
            throw new MatrixSizeException("Matrix a and b are of different size");
        }
        Matrix2d result = new Matrix2d(this.getM(), this.getN());

        for (int i = 0; i < this.getM(); i++) {
            for (int j = 0; j < this.getN(); j++) {
                result.setValue(i, j, this.getValue(i, j) - b.getValue(i, j));
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
    public Matrix2d mult(Points2d b) { // Multiplies this * b
        Matrix2d a = this;
        if (a.getN() != b.getM()) {
            throw new MatrixSizeException("Matrix a and b are of incompatible sizes");
        }
        Matrix2d result = new Matrix2d(a.getM(), b.getN());
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                float v = 0f;
                for (int k = 0; k < a.getN(); k++) {
                    v += a.getValue(i, k) * b.getValue(k, j);
                }
                result.setValue(i, j, v);
            }
        }
        return result;
    }

    /**
     * Creates an l by l identity matrix.
     * @param l the size of the matrix
     * @return An l by l identity matrix
     */
    public static final Matrix2d genIdentity(int l) {
        Matrix2d result = new Matrix2d(l, l);
        for (int k = 0; k < l; k++) {
            result.setValue(k, k, 1f);
        }
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that rotates a point theta degrees clockwise around the origin.
     * @param theta The angle in degrees
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homRotate3d(float theta) {
        Matrix2d result = new Matrix2d(4, 4);
        theta = theta * (float) Math.PI / 180f;
        result.setValue(0, 0, (float) Math.cos(theta));
        result.setValue(1, 0, (float) Math.sin(theta));
        result.setValue(0, 1, -(float) Math.sin(theta));
        result.setValue(1, 1, (float) Math.cos(theta));
        result.setValue(2, 2, 1f);
        result.setValue(3, 3, 1f);
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that translates a point by x and y.
     * @param x The translation in the x-axis
     * @param y The translation in the y-axis
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homTranslate3d(float x, float y, float z) {
        Matrix2d result = Matrix2d.genIdentity(4);
        result.setValue(0, 3, x);
        result.setValue(1, 3, y);
        result.setValue(2, 3, z);
        return result;
    }

    /**
     * Creates a 3 by 3 homogeneous matrix that scales a point by x and y from the origin.
     * @param x The scale in the x-axis
     * @param y The scale in the y-axis
     * @return The 3 by 3 matrix
     */
    public static final Matrix2d homScale3d(float x, float y, float z) {
        Matrix2d result = new Matrix2d(4, 4);
        result.setValue(0, 0, x);
        result.setValue(1, 1, y);
        result.setValue(2, 2, z);
        result.setValue(3, 3, 1f);
        return result;
    }

}
