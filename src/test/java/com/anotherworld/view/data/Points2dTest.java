package com.anotherworld.view.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Points2dTest {

    @Test
    public void value_InitValue_IsZero() {
        Points2d v = new Points2d(4, 6);
        for (int i = 0; i < v.getM(); i++) {
            for (int j = 0; j < v.getN(); j++) {
                assertThat(v.getValue(i, j), is(equalTo(0f)));
            }
        }
    }
    
    @Test
    public void value_pointsSize_IsCorrect() {
        Points2d v = new Points2d(10, 100);
        assertThat(v.getM(), is(equalTo(10)));
        assertThat(v.getN(), is(equalTo(100)));
    }

    @Test
    public void value_SetValue_IsCorrect() {
        Points2d v = new Points2d(8, 5);
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
        new Points2d(-1, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void value_NegativeJ_Exception() {
        Points2d a = new Points2d(4, 5);
        a.getValue(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void value_TooLargeI_Exception() {
        Points2d a = new Points2d(7, 2);
        a.getValue(6, 8);
    }

}
