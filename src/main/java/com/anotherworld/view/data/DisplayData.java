package com.anotherworld.view.data;

import com.anotherworld.model.movable.ObjectState;

/**
 * Defines the basic data needed to display an object.
 * @author Jake Stewart
 *
 */
public interface DisplayData {

    /**
     * The object's current x coordinate in the world system.
     * @return the x coordinate
     */
    public float getXCoordinate();

    /**
     * The object's current y coordinate in the world system.
     * @return the y coordinate
     */
    public float getYCoordinate();

    /**
     * The object's current angle in the world system.
     * @return the angle
     */
    public float getAngle();
    
    /**
     * The object's current state.
     * @return the object state
     */
    public ObjectState getState();
    
}
