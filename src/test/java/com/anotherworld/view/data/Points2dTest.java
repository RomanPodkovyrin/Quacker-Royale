package com.anotherworld.view.data;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

public class Points2dTest {

    private static final float TEST_ERROR = 0.0001f;
    
    @Test
    public void constructor_ZeroValues_IsCreated() {
        new Points2d(0, 0);
    }
    
    @Test
    public void constructor_CorrectValues_IsCreated() {
        new Points2d(1000, 20);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void constructor_NegativeM_Exception() {
        new Points2d(-25, 40);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void constructor_NegativeN_Exception() {
        new Points2d(32, -1);
    }

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
    public void getValue_NegativeJ_Exception() {
        Points2d a = new Points2d(4, 5);
        a.getValue(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getValue_NegativeI_Exception() {
        Points2d a = new Points2d(4, 5);
        a.getValue(-20, 2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getValue_TooLargeI_Exception() {
        Points2d a = new Points2d(7, 2);
        a.getValue(6, 8);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getValue_TooLargeJ_Exception() {
        Points2d a = new Points2d(4, 22);
        a.getValue(20, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValue_NegativeJ_Exception() {
        Points2d a = new Points2d(4, 5);
        a.setValue(0, -1, 0f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValue_NegativeI_Exception() {
        Points2d a = new Points2d(4, 5);
        a.setValue(-20, 2, 0f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValue_TooLargeI_Exception() {
        Points2d a = new Points2d(7, 2);
        a.setValue(6, 8, 0f);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setValue_TooLargeJ_Exception() {
        Points2d a = new Points2d(4, 22);
        a.setValue(20, 1, 0f);
    }
    
    @Test
    public void genCircle_ValidR_CircleIsReturned() {
        Points2d circle = Points2d.genCircle(1);
        assertThat(circle.getValue(0, 0), is(0f));
        assertThat(circle.getValue(1, 0), is(0f));
        assertThat(circle.getValue(2, 0), is(0f));
        for (int j = 1; j < circle.getN(); j++) {
            double radius = Math.pow(circle.getValue(0, j) / circle.getValue(3, j), 2);
            radius += Math.pow(circle.getValue(1, j) / circle.getValue(3, j), 2);
            radius += Math.pow(circle.getValue(2, j) / circle.getValue(3, j), 2);
            radius = Math.sqrt(radius);
            assertThat(radius, is(closeTo(1f, TEST_ERROR)));
        }
    }
    
    @Test
    public void getPoints_RandomValues_ReturnsThePoints() {
        Points2d a = new Points2d(12, 30);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, (float)Math.random());
            }
        }
        float[] values = a.getPoints();
        assertThat(values.length, is(a.getM() * a.getN()));
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                assertThat(values[i + a.getM() * j], is(equalTo(a.getValue(i, j))));
            }
        }
    }
    
    @Test
    public void toString_EmptyPoints_ReturnsThePoints() {
        Points2d a = new Points2d(0, 0);
        assertThat(a.toString(), is(equalTo("")));
    }
    
    @Test
    public void toString_RandomValues_ReturnsThePoints() {
        Points2d a = new Points2d(20, 12);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, (float)Math.random());
            }
        }
        String value = a.toString();
        int index = 0;
        int lastNumberStart = 0;
        int counter = 0;
        int line = 0;
        while (index < value.length()) {
            if (value.substring(index, index + 1).equals(",")) {
                assertThat(Double.parseDouble(value.substring(lastNumberStart, index)), is(closeTo(a.getValue(line, counter), TEST_ERROR)));
                lastNumberStart = index + 1;
                counter += 1;
            } else if (value.substring(index, index + 1).equals("\n")) {
                assertThat(Double.parseDouble(value.substring(lastNumberStart, index)), is(closeTo(a.getValue(line, counter), TEST_ERROR)));
                lastNumberStart = index + 1;
                assertThat(counter + 1, is(equalTo(a.getN())));
                counter = 0;
                line += 1;
            }
            index += 1;
        }
        assertThat(line, is(equalTo(a.getM())));
    }
    
    @Test
    public void genRectangle_NormalValues_ReturnsRectangle() {
        Points2d a = Points2d.genRectangle(100, 50);
        assertThat(a.getN(), is(greaterThan(0)));
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        for (int j = 0; j < a.getN(); j++) {
            assertThat((double)a.getValue(0, j), is(anyOf(closeTo(50f, TEST_ERROR), closeTo(-50f, TEST_ERROR))));
            assertThat((double)a.getValue(1, j), is(anyOf(closeTo(25f, TEST_ERROR), closeTo(-25f, TEST_ERROR))));
            x.add((double)a.getValue(0, j));
            y.add((double)a.getValue(1, j));
        }
        assertThat(x, hasItem(closeTo(-50d, TEST_ERROR)));
        assertThat(x, hasItem(closeTo(50d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(-25d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(25d, TEST_ERROR)));
    }
    
    @Test
    public void genWall_NormalValues_ReturnsWall() {
        Points2d a = Points2d.genWall(100, 50, 5);
        assertThat(a.getN(), is(greaterThan(0)));
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        for (int j = 0; j < a.getN(); j++) {
            assertThat((double)a.getValue(0, j), is(anyOf(closeTo(50f, TEST_ERROR), closeTo(-50f, TEST_ERROR), closeTo(55f, TEST_ERROR), closeTo(-55f, TEST_ERROR))));
            assertThat((double)a.getValue(1, j), is(anyOf(closeTo(25f, TEST_ERROR), closeTo(-25f, TEST_ERROR), closeTo(30f, TEST_ERROR), closeTo(-30f, TEST_ERROR))));
            x.add((double)a.getValue(0, j));
            y.add((double)a.getValue(1, j));
        }
        assertThat(x, hasItem(closeTo(-50d, TEST_ERROR)));
        assertThat(x, hasItem(closeTo(50d, TEST_ERROR)));
        assertThat(x, hasItem(closeTo(-55d, TEST_ERROR)));
        assertThat(x, hasItem(closeTo(55d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(-25d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(25d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(-30d, TEST_ERROR)));
        assertThat(y, hasItem(closeTo(30d, TEST_ERROR)));
    }
    
    @Test
    public void normalise_ZeroVector_ZeroVector() {
        Points2d b = new Points2d(40, 20);
        b = b.normalise();
        for (int i = 0; i < b.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                assertThat((double)b.getValue(i, j), is(closeTo(0f, TEST_ERROR)));
            }
        }
    }
    
    @Test
    public void normalise_OneOneVector_OneOneVector() {
        Points2d b = new Points2d(4, 1);
        b.setValue(2, 0, 1f);
        b = b.normalise();
        for (int i = 0; i < b.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                assertThat((double)b.getValue(i, j), is(closeTo((i == 2 && j == 0) ? 1f : 0f, TEST_ERROR)));
            }
        }
    }
    
    @Test
    public void normalise_RandomVector_NormalisedVector() {
        Points2d b = new Points2d(50, 20);
        for (int i = 0; i < b.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                b.setValue(i, j, (float)Math.random());
            }
        }
        b = b.normalise();
        float total = 0f;
        for (int i = 0; i < b.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                assertThat(b.getValue(i, j), is(allOf(greaterThanOrEqualTo(-1f), lessThanOrEqualTo(1f))));
                total += b.getValue(i, j) * b.getValue(i, j);
            }
        }
        assertThat((double)total, is(closeTo(1f, TEST_ERROR)));
    }
    
    @Test
    public void crossProduct_ZeroVector_ZeroVector() {
        Points2d a = new Points2d(3, 1);
        Points2d b = new Points2d(3, 1);
        Points2d c = a.crossProduct(b);
        for (int i = 0; i < 3; i++) {
            assertThat((double)c.getValue(i, 0), is(closeTo(0f, TEST_ERROR)));
        }
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void crossProduct_WrongVectoram_Error() {
        Points2d a = new Points2d(2, 1);
        Points2d b = new Points2d(3, 1);
        a.crossProduct(b);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void crossProduct_WrongVectoran_Error() {
        Points2d a = new Points2d(3, 2);
        Points2d b = new Points2d(3, 1);
        a.crossProduct(b);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void crossProduct_WrongVectorbm_Error() {
        Points2d a = new Points2d(3, 1);
        Points2d b = new Points2d(2, 1);
        a.crossProduct(b);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void crossProduct_WrongVectorbn_Error() {
        Points2d a = new Points2d(3, 1);
        Points2d b = new Points2d(3, 0);
        a.crossProduct(b);
    }

}
