package com.anotherworld.view;

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
	
	public float[] getRow(int i) {
		return value[i];
	}

	public float[] getColumn(int j) {
		float[] r = new float[m];
		for (int i = 0; i < m; i++) {
			r[i] = value[i][j];
		}
		return r;
	}
	
	public void setValue(int i, int j, float v) {
		value[i][j] = v;
	}
	
	public Matrix2d add(Matrix2d b) {
		assert(b.getM() == this.getM());
		assert(b.getN() == this.getN());
		Matrix2d result = new Matrix2d(m, n);
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				result.value[i][j] = this.value[i][j] + b.value[i][j];
			}
		}
		return result;
	}
	
	public Matrix2d sub(Matrix2d b) {
		assert(b.getM() == this.getM());
		assert(b.getN() == this.getN());
		Matrix2d result = new Matrix2d(m, n);
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; i < n; j++) {
				result.value[i][j] = this.value[i][j] - b.value[i][j];
			}
		}
		return result;
	}
	
	public Matrix2d mult(Matrix2d a) { // Multiplies a * this
		assert(this.getM() == a.getN());
		Matrix2d result = new Matrix2d(a.getM(), this.getN());
		for (int i = 0; i < a.getM(); i++) {
			for (int j = 0; j < this.getN(); j++) {
				float v = 0f;
				for (int k = 0; k < this.getM(); k++) {
					v += a.value[i][k] * a.value[k][j];
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
	
	public static final Matrix2d GEN_IDENTITY(int l) {
		Matrix2d result = new Matrix2d(l, l);
		for (int k = 0; k < l; k++) {
			result.value[k][k] = 1f;
		}
		return result;
	}
	
}
