package com.anotherworld.view.graphics;

/**
 * Stores the information that defines where the camera is looking.
 * @author Jake Stewart
 *
 */
public interface Camera {

    /**
     * The Camera's x location in the world coordinate frame.
     * @return the x location
     */
    public float getX();

    /**
     * The Camera's y location in the world coordinate frame.
     * @return the y location
     */
    public float getY();

    /**
     * The Camera's width in the world coordinate frame.
     * @return the width
     */
    public float getWidth();

    /**
     * The Camera's height in the world coordinate frame.
     * @return the height
     */
    public float getHeight();

}
