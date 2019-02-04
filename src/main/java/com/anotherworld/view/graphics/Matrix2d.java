package com.anotherworld.view.graphics;

public class Matrix2d {

    private float[][] value;
    private int m;
    private int n;

    public Matrix2d(int m, int n) {
        if (m < 0 || n < 0) {
            throw new IndexOutOfBoundsException();
        }
        value = new float[m][n];
        this.m = m;
        this.n = n;
    }

    public void setValue(int i, int j, float v) {
        value[i][j] = v;
    }

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

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

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
