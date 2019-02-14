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
        return min + ((float) Math.random()) * (max - min);
    }

    public static int getRandom(int min, int max) {
        Random r = new Random();
        return min + ((int)Math.random()) * (max - min);
    }
}
