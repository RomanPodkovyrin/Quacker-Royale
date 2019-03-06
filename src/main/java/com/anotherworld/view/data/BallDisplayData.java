package com.anotherworld.view.data;

/**
 * Defines the data need to display a ball.
 * @author Jake Stewart
 *
 */
public interface BallDisplayData extends CircleDisplayData {

    /**
     * Returns true if the ball is dangerous (has been hit by a player).
     * @return if the ball is dangerous
     */
    public boolean isDangerous();
    
}
