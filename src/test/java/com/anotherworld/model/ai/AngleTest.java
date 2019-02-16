package com.anotherworld.model.ai;


import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.ai.tools.MatrixMath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AngleTest {
    //private final MatrixMath j;
    private final Matrix vectorLine;
    private final float expectedAngle;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Matrix(-1,-1), 315},
                {new Matrix(0,-1), 0},
                {new Matrix(1,-1), 45},
                {new Matrix(1,0), 90},
                {new Matrix(1,1), 135},
                {new Matrix(0,1), 180},
                {new Matrix(-1,1), 225},
                {new Matrix(-1,0), 270},
        });
    }
    public AngleTest(Matrix vectorLine, float expectedAngle){
        //this.j = new MatrixMath();
        this.vectorLine = vectorLine;
        this.expectedAngle = expectedAngle;
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
    @Test
    public void angelTest(){


        assertEquals(expectedAngle , MatrixMath.vectorAngle(vectorLine),0.1);



    }

}
