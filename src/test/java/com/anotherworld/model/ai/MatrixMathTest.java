package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Line;
import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixMathTest {

    @Test
    public void genericTest(){
        Line testLine = new Line(new Matrix(-4.0f, -4.0f),new Matrix(3.0f, 2.0f));
        float distance = MatrixMath.dist(testLine,new Matrix(5,6));

        assertEquals(round (3.328f,3),round(distance,3),0.0f);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
    @Test
    public void preprendicularTest(){

        boolean perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(5,5));
        assertTrue(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(0,0));
        assertFalse(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(5,0));
        assertFalse(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(0,3));
        assertFalse(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(5,2));
        assertTrue(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(2,4));
        assertTrue(perpendicular);

        perpendicular = MatrixMath.isPerpenducular(new Matrix(3,5),new Matrix(3,2), new Matrix(6,6));
        assertTrue(perpendicular);



    }

}
