package com.anotherworld.model.ai;

import com.anotherworld.model.ai.aiMathsTools.Matrix;
import com.anotherworld.model.ai.aiMathsTools.MatrixMath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PerpendicularTest {
    //private final MatrixMath j;
    private final Matrix vectorLine;
    private final Matrix vextorPoint;
    private final Matrix point;
    private final boolean expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Matrix(3,5),new Matrix(3,2), new Matrix(5,5),true},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(0,0),false},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(5,0),false},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(0,3),false},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(5,2),true},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(2,4),true},
                {new Matrix(3,5),new Matrix(3,2), new Matrix(6,6),true},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(5,5),false},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(0,0),true},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(5,0),true},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(5,2),false},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(2,4),false},
                {new Matrix(-3,-5),new Matrix(3,2), new Matrix(6,6),false},
        });
    }
    public PerpendicularTest(Matrix vectorLine, Matrix vectorPoint, Matrix point, boolean expected){
        //this.j = new MatrixMath();
        this.vectorLine = vectorLine;
        this.vextorPoint = vectorPoint;
        this.point = point;
        this.expected = expected;
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
    @Test
    public void perpendicularTest(){

        assertTrue(expected == MatrixMath.isPerpendicular(vectorLine,vextorPoint,point));



    }

}
