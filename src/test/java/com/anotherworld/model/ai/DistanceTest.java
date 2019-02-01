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
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {}
        });
    }
    public DistanceTest(){
    }


    @Test
    public void genericTest(){
        Line testLine = new Line(new Matrix(-4.0f, -4.0f),new Matrix(3.0f, 2.0f));
        float distance = MatrixMath.dist(testLine,new Matrix(5,6));

        System.out.println(MatrixMath.nearestNeighbour(testLine,new Matrix(5,6)));

        //assertEquals(round (3.328f,3),round(distance,3),0.0f);

        Line line = new Line(new Matrix(2.0f, 2.0f),new Matrix(2.0f, 0.0f));
        Matrix q = new Matrix(6,1);
        System.out.println("NormalVector: "+ line.getOrthogonalVector() +" Distance: "+MatrixMath.dist(line,q)+" Nearest Neighbour: "+ MatrixMath.nearestNeighbour(line,q));

        line = new Line(new Matrix(2.0f, 2.0f),new Matrix(0.0f, 2.0f));
        q = new Matrix(1,1);
        System.out.println("NormalVector: "+ line.getOrthogonalVector() +" Distance: "+MatrixMath.dist(line,q)+" Nearest Neighbour: "+ MatrixMath.nearestNeighbour(line,q));

    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long temp = Math.round(value);
        return (double) temp / factor;
    }
}
