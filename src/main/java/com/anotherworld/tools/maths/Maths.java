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
        return min + ((int)Math.random()) * (max - min);
    }

    /**
     * prevents form the nan error when used in ai
     * @param a
     * @param b
     * @return
     */
    public static float floatDivision(float a,float b ) {
        if (b == 0f) {
            b = 0.0f;
            return  b;
        }

        return a / b;
    }
}
