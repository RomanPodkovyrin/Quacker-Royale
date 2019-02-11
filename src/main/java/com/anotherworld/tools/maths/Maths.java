package com.anotherworld.tools.maths;

import java.util.Random;

/**
 * Maths methods.
 *
 * @author Roman
 */
public class Maths {

    public static float getRandom(float min, float max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    public static int getRandom(int min, int max) {
        Random r = new Random();
        return min + r.nextInt() * (max - min);
    }
}
