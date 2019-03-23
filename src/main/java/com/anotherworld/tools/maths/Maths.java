package com.anotherworld.tools.maths;

import java.util.Random;

/**
 * Maths methods.
 *
 * @author Roman
 */
public class Maths {

    public static float getRandom(float min, float max) {
        return min + ((float) Math.random()) * (max - min);
    }

    public static int getRandom(int min, int max) {
        return (int)(min + (Math.random()) * (max - min));
    }

    /**
     * prevents form the nan error when used in ai.
     * @param a value a to be divided by b
     * @param b value b which is going to divide a
     * @return a / b
     */
    public static float floatDivision(float a,float b) {
        if (b == 0f) {
            b = 0.0f;
            return  b;
        }

        return a / b;
    }
}
