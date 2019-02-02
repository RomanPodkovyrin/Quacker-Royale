package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Line;
import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class DistanceTest {

    private final Line line;
    private final Matrix point;
    private final float expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Line(new Matrix(-4.0f, -4.0f),new Matrix(3.0f, 2.0f)), new Matrix(5,6), -3.328f},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(1,3), -1.414f},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(3,4), -0.707f},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(5,5), 0},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(4,3), 0.707f},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(3,1), 1.414f},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(3,1), 1},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(4,0), 2},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(5,2), 0},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(6,3), -1},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(7,4), -2},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(1,3), -1},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(0,4), -2},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(2,5), 0},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(3,6), 1},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(4,7), 2},
        });
    }
    public DistanceTest(Line line, Matrix point, float expected){
        this.line = line;
        this.point = point;
        this.expected = expected;

    }


    @Test
    public void distanceFromLineTest(){

        assertEquals(round(expected,3),round(MatrixMath.dist(line,point),3),0.0f);


    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
}
