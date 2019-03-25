package com.anotherworld.view.data.primatives;

import static org.lwjgl.opengl.GL46.GL_POINTS;
import static org.lwjgl.opengl.GL46.GL_QUADS;
import static org.lwjgl.opengl.GL46.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL46.GL_TRIANGLE_STRIP;

/**
 * An enum to represent how opengl should draw an object.
 * @author Jake Stewart
 *
 */
public enum DrawType {
    TRIANGLE_FAN, QUADS, TRIANGLE_STRIP;
    
    /**
     * Converts a draw type to the corresponding opengl int constant.
     * @param dt The drawtype to convert
     * @return the int constant
     */
    public static int convertToInt(DrawType dt) {
        switch (dt) {
            case TRIANGLE_FAN:
                return GL_TRIANGLE_FAN;
            case QUADS:
                return GL_QUADS;
            case TRIANGLE_STRIP:
                return GL_TRIANGLE_STRIP;
            default:
                return GL_POINTS;
        }
    }
}
