package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Line;
import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;


@RunWith(Parameterized.class)
public class NeighbourTest {

    private final Line line;
    private final Matrix point;
    private final Matrix expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(1,3), new Matrix(2,2)},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(3,4), new Matrix(3.5f,3.5f)},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(5,5), new Matrix(5,5)},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(4,3), new Matrix(3.5f,3.5f)},
                {new Line(new Matrix(0, 0),new Matrix(3.0f, 3.0f)), new Matrix(3,1), new Matrix(2,2)},
                {new Line(new Matrix(0, 0),new Matrix(-3.0f, -3.0f)), new Matrix(1,3), new Matrix(2,2)},
                {new Line(new Matrix(0, 0),new Matrix(-3.0f, -3.0f)), new Matrix(3,4), new Matrix(3.5f,3.5f)},
                {new Line(new Matrix(0, 0),new Matrix(-3.0f, -3.0f)), new Matrix(5,5), new Matrix(5,5)},
                {new Line(new Matrix(0, 0),new Matrix(-3.0f, -3.0f)), new Matrix(4,3), new Matrix(3.5f,3.5f)},
                {new Line(new Matrix(0, 0),new Matrix(-3.0f, -3.0f)), new Matrix(3,1), new Matrix(2,2)},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(3,1), new Matrix(3,2)},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(4,0), new Matrix(4,2)},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(5,2), new Matrix(5,2)},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(6,3), new Matrix(6,2)},
                {new Line(new Matrix(2, 2),new Matrix(1, 0)), new Matrix(7,4), new Matrix(7,2)},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(1,3), new Matrix(2,3)},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(0,4), new Matrix(2,4)},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(2,5), new Matrix(2,5)},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(3,6), new Matrix(2,6)},
                {new Line(new Matrix(2, 2),new Matrix(0, 1)), new Matrix(4,7), new Matrix(2,7)},
        });
    }
    public NeighbourTest(Line line, Matrix point, Matrix expected){
        this.line = line;
        this.point = point;
        this.expected = expected;

    }


    @Test
    public void nearestPointTest(){
        Matrix neighbour = MatrixMath.nearestNeighbour(line,point);

        assertEquals(round(expected.getX(),3), round(neighbour.getX(),3) );
        assertEquals(round(expected.getY(),3), round(neighbour.getY(),3) );


    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
}
