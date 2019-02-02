package com.anotherworld.view;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class Matrix2dTest {

    @Test
    public void value_InitValue_IsZero() {
        Matrix2d v = new Matrix2d(4, 6);
        for (int i = 0; i < v.getM(); i++) {
            for (int j = 0; j < v.getN(); j++) {
                assertThat(v.getValue(i, j), is(equalTo(0f)));
            }
        }
    }

    @Test
    public void value_IndentityValue_IsCorrect() {
        Matrix2d v = Matrix2d.GEN_IDENTITY(10);
        for (int i = 0; i < v.getM(); i++) {
            for (int j = 0; j < v.getN(); j++) {
                assertThat(v.getValue(i, j), is(equalTo((i == j) ? 1f : 0f)));
            }
        }
    }

    @Test
    public void value_MatrixSize_IsCorrect() {
        Matrix2d v = new Matrix2d(10, 100);
        assertThat(v.getM(), is(equalTo(10)));
        assertThat(v.getN(), is(equalTo(100)));
    }

    @Test
    public void value_SetValue_IsCorrect() {
        Matrix2d v = new Matrix2d(8, 5);
        for (int i = 0; i < v.getM(); i++) {
            for (int j = 0; j < v.getN(); j++) {
                ;
                v.setValue(i, j, i * (j + 5));
                assertThat(v.getValue(i, j), is(equalTo((float) i * (j + 5))));
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void value_NegativeM_Exception() {
        new Matrix2d(-1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void value_NegativeJ_Exception() {
        Matrix2d a = new Matrix2d(4, 5);
        a.getValue(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void value_TooLargeI_Exception() {
        Matrix2d a = new Matrix2d(7, 2);
        a.getValue(6, 8);
    }

    @Test
    public void sum_AddToSelf_IsCalculated() {
        Matrix2d a = new Matrix2d(14, 3);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, i + j);
            }
        }
        Matrix2d r = a.add(a);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                assertThat(r.getValue(i, j), is(equalTo(a.getValue(i, j) * 2)));
            }
        }

    }

    @Test
    public void sum_NormalValues_IsCalculated() {
        Matrix2d a = new Matrix2d(6, 15);
        Matrix2d b = new Matrix2d(6, 15);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, i * j);
                b.setValue(i, j, Math.max(i, j));
            }
        }
        Matrix2d c = a.add(b);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                assertThat(c.getValue(i, j), is(equalTo(a.getValue(i, j) + b.getValue(i, j))));
            }
        }
    }

    @Test
    public void mult_IdById_IsId() {
        Matrix2d id = Matrix2d.GEN_IDENTITY(7);
        Matrix2d result = id.mult(id);
        assertThat(result.getN(), is(equalTo(7)));
        assertThat(result.getM(), is(equalTo(7)));
        for (int i = 0; i < result.getM(); i++) {
            for (int j = 0; j < result.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo((i == j) ? 1f : 0f)));
            }
        }
    }

}
