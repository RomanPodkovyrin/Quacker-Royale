package com.anotherworld.view.data;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.anotherworld.view.graphics.MatrixSizeException;

import org.junit.Test;

public class Matrix2dTest {
    
    private static final float TEST_ERROR = 0.0001f;
    
    @Test
    public void value_IndentityValue_IsCorrect() {
        Matrix2d v = Matrix2d.genIdentity(10);
        for (int i = 0; i < v.getM(); i++) {
            for (int j = 0; j < v.getN(); j++) {
                assertThat(v.getValue(i, j), is(equalTo((i == j) ? 1f : 0f)));
            }
        }
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
                assertThat((double)r.getValue(i, j), is(closeTo(a.getValue(i, j) * 2, TEST_ERROR)));
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
                assertThat((double)c.getValue(i, j), is(closeTo(a.getValue(i, j) + b.getValue(i, j), TEST_ERROR)));
            }
        }
    }

    @Test(expected = MatrixSizeException.class)
    public void sum_MissMatch_Fails() {
        Matrix2d a = new Matrix2d(6, 15);
        Matrix2d b = new Matrix2d(7, 15);
        a.add(b);
    }

    @Test(expected = MatrixSizeException.class)
    public void sum_MissMatch2_Fails() {
        Matrix2d a = new Matrix2d(7, 16);
        Matrix2d b = new Matrix2d(7, 15);
        b.add(a);
    }

    @Test
    public void sub_SubFromSelf_IsCalculated() {
        Matrix2d a = new Matrix2d(14, 3);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, i + j);
            }
        }
        Matrix2d r = a.sub(a);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                assertThat((double)r.getValue(i, j), is(closeTo(0f, TEST_ERROR)));
            }
        }

    }

    @Test
    public void sub_NormalValues_IsCalculated() {
        Matrix2d a = new Matrix2d(8, 10);
        Matrix2d b = new Matrix2d(8, 10);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                a.setValue(i, j, i * j);
                b.setValue(i, j, Math.max(i, j));
            }
        }
        Matrix2d c = a.sub(b);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < a.getN(); j++) {
                assertThat((double)c.getValue(i, j), is(closeTo(a.getValue(i, j) - b.getValue(i, j), TEST_ERROR)));
            }
        }
    }

    @Test(expected = MatrixSizeException.class)
    public void sub_MissMatch_Fails() {
        Matrix2d a = new Matrix2d(20, 11);
        Matrix2d b = new Matrix2d(20, 10);
        a.sub(b);
    }

    @Test(expected = MatrixSizeException.class)
    public void sub_MissMatch_Fails2() {
        Matrix2d a = new Matrix2d(21, 10);
        Matrix2d b = new Matrix2d(20, 10);
        b.sub(a);
    }
    
    @Test
    public void mult_IdById_IsId() {
        Matrix2d id = Matrix2d.genIdentity(7);
        Matrix2d result = id.mult(id);
        assertThat(result.getN(), is(equalTo(7)));
        assertThat(result.getM(), is(equalTo(7)));
        for (int i = 0; i < result.getM(); i++) {
            for (int j = 0; j < result.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo((i == j) ? 1f : 0f)));
            }
        }
    }
    
    @Test
    public void mult_IdByA_IsA() {
        Matrix2d id = Matrix2d.genIdentity(4);
        Matrix2d b = new Matrix2d(4, 4);
        for (int i = 0; i < b.getM(); i++) {
            for (int j = 0; j < b.getN(); j++) {
                b.setValue(i, j, i * j + j);
            }
        }
        Matrix2d result = id.mult(b);
        for (int i = 0; i < result.getM(); i++) {
            for (int j = 0; j < result.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo(b.getValue(i,  j))));
            }
        }
    }
    
    @Test
    public void mult_RandomValuesTwoByTwo_IsCalculated() {
        Matrix2d a = new Matrix2d(2, 2);
        Matrix2d b = new Matrix2d(2, 2);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < b.getM(); j++) {
                a.setValue(i, j, (float)Math.random());
            }
        }
        Matrix2d result = a.mult(b);
        for (int i = 0; i < result.getM(); i++) {
            for (int j = 0; j < result.getN(); j++) {
                assertThat((double)result.getValue(i, j), is(closeTo(a.getValue(i, 0) * b.getValue(0, j) + a.getValue(i, 1) * b.getValue(1, j), TEST_ERROR)));
            }
        }
    }
    
    @Test
    public void mult_RandomValues3By3_IsCalculated() {
        Matrix2d a = new Matrix2d(3, 3);
        Matrix2d b = new Matrix2d(3, 3);
        for (int i = 0; i < a.getM(); i++) {
            for (int j = 0; j < b.getM(); j++) {
                a.setValue(i, j, (float)Math.random());
            }
        }
        Matrix2d result = a.mult(b);
        for (int i = 0; i < result.getM(); i++) {
            for (int j = 0; j < result.getN(); j++) {
                assertThat((double)result.getValue(i, j), is(closeTo(a.getValue(i, 0) * b.getValue(0, j) + a.getValue(i, 1) * b.getValue(1, j) + a.getValue(i, 2) * b.getValue(2, j), TEST_ERROR)));
            }
        }
    }
    
    @Test(expected =  MatrixSizeException.class)
    public void mult_MissMatch_Fails() {
        Matrix2d a = new Matrix2d(6, 4);
        Matrix2d b = new Matrix2d(6, 4);
        a.mult(b);
    }
    
    @Test
    public void scale_ScaleByOne_EqualsP() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)Math.random());
            points.setValue(1, j, (float)Math.random());
            points.setValue(2, j, (float)Math.random());
            points.setValue(3, j, 1);
        }
        Matrix2d t = Matrix2d.homScale3d(1, 1, 1);
        Points2d result = t.mult(points);
        for (int i = 0; i < points.getM(); i++) {
            for (int j = 0; j < points.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo(points.getValue(i, j))));
            }
        }
    }
    
    @Test
    public void scale_ScaleByRandom_IsCalculated() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)Math.random());
            points.setValue(1, j, (float)Math.random());
            points.setValue(2, j, (float)Math.random());
            points.setValue(3, j, 1);
        }
        float xScale = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        float yScale = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        float zScale = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        Matrix2d t = Matrix2d.homScale3d(xScale, yScale, zScale);
        Points2d result = t.mult(points);
        for (int j = 0; j < points.getN(); j++) {
            assertThat((double)result.getValue(0, j), is(closeTo(points.getValue(0, j) * xScale, TEST_ERROR)));
            assertThat((double)result.getValue(1, j), is(closeTo(points.getValue(1, j) * yScale, TEST_ERROR)));
            assertThat((double)result.getValue(2, j), is(closeTo(points.getValue(2, j) * zScale, TEST_ERROR)));
            assertThat((double)result.getValue(3, j), is(closeTo(points.getValue(3, j), TEST_ERROR)));
        }
    }
    
    @Test
    public void translate_TranslateByZero_EqualsP() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)Math.random());
            points.setValue(1, j, (float)Math.random());
            points.setValue(2, j, (float)Math.random());
            points.setValue(3, j, 1);
        }
        Matrix2d t = Matrix2d.homTranslate3d(0, 0, 0);
        Points2d result = t.mult(points);
        for (int i = 0; i < points.getM(); i++) {
            for (int j = 0; j < points.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo(points.getValue(i, j))));
            }
        }
    }
    
    @Test
    public void translate_translateByRandom_IsCalculated() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)Math.random());
            points.setValue(1, j, (float)Math.random());
            points.setValue(2, j, (float)Math.random());
            points.setValue(3, j, 1);
        }
        float xTranslation = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        float yTranslation = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        float zTranslation = (float)((Math.random() < 0.5) ? -Math.random() : Math.random());
        Matrix2d t = Matrix2d.homTranslate3d(xTranslation, yTranslation, zTranslation);
        Points2d result = t.mult(points);
        for (int j = 0; j < points.getN(); j++) {
            assertThat((double)result.getValue(0, j), is(closeTo(points.getValue(0, j) + xTranslation, TEST_ERROR)));
            assertThat((double)result.getValue(1, j), is(closeTo(points.getValue(1, j) + yTranslation, TEST_ERROR)));
            assertThat((double)result.getValue(2, j), is(closeTo(points.getValue(2, j) + zTranslation, TEST_ERROR)));
            assertThat((double)result.getValue(3, j), is(closeTo(points.getValue(3, j), TEST_ERROR)));
        }
    }
    
    @Test
    public void rotate_rotateByZero_IsP() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)Math.random());
            points.setValue(1, j, (float)Math.random());
            points.setValue(2, j, (float)Math.random());
            points.setValue(3, j, 1);
        }
        Matrix2d t = Matrix2d.homRotate3d(0);
        Points2d result = t.mult(points);
        for (int i = 0; i < points.getM(); i++) {
            for (int j = 0; j < points.getN(); j++) {
                assertThat(result.getValue(i, j), is(equalTo(points.getValue(i, j))));
            }
        }
    }
    
    @Test
    public void rotate_rotateRandomByRightAngle_IsP() {
        Points2d points = new Points2d(4, 40);
        for (int j = 0; j < points.getN(); j++) {
            points.setValue(0, j, (float)((Math.random() < 0.5) ? -Math.random() : Math.random()));
            points.setValue(1, j, (float)((Math.random() < 0.5) ? -Math.random() : Math.random()));
            points.setValue(2, j, (float)((Math.random() < 0.5) ? -Math.random() : Math.random()));
            points.setValue(3, j, 1);
        }
        Matrix2d t = Matrix2d.homRotate3d(90);
        Points2d result = t.mult(points);
        for (int j = 0; j < points.getN(); j++) {
            assertThat((double)result.getValue(0, j), is(closeTo(-points.getValue(1, j), TEST_ERROR)));
            assertThat((double)result.getValue(1, j), is(closeTo(points.getValue(0, j), TEST_ERROR)));
            assertThat((double)result.getValue(2, j), is(closeTo(points.getValue(2, j), TEST_ERROR)));
            assertThat((double)result.getValue(3, j), is(closeTo(points.getValue(3, j), TEST_ERROR)));
        }
    }

}
