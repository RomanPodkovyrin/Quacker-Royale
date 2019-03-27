package com.anotherworld.view.data;

import com.anotherworld.tools.maths.Matrix;

/**
 * Defines the data needed to display a circle.
 * @author Jake Stewart
 *
 */
public interface CircleDisplayData extends DisplayData {

    public float getRadius();
    
    /**
     * The object's current velocity in the world system.
     * @return the velocity
     */
    public Matrix getVelocity();
    
}
