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
}
