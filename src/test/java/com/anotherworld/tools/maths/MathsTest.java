package com.anotherworld.tools.maths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MathsTest {

    private final float firstValue;
    private final float secondValue;
    private final float expected;
    private final float minRandomF;
    private final float maxRandomF;
    private final int minRandomI;
    private final int maxRandomI;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1f,1f,1f,1,2,1,2},
                {4f,2f,2f,1,6,1,6},
                {4f,0f,0f,4,8,4,8},
                {0f,0f,0f,1,2,1,2},
        });
    }

    public MathsTest(float firstValue,float secondValue, float expected,
                     float minRandomF, float maxRandomF, int minRandomI, int maxRandomI) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.expected = expected;
        this.minRandomF = minRandomF;
        this.maxRandomF = maxRandomF;
        this.minRandomI = minRandomI;
        this.maxRandomI = maxRandomI;
    }

    @Test
    public void mathsTest() {
        float delta = 0.000001f;
        assertEquals(expected,Maths.floatDivision(firstValue,secondValue),delta);
        int randomI = Maths.getRandom(minRandomI,maxRandomI);
        float randomF = Maths.getRandom(minRandomF,maxRandomF);
        assertTrue(randomF<maxRandomF & randomF > minRandomF);
    }
}
