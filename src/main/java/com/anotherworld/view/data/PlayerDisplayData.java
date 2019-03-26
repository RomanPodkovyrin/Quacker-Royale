package com.anotherworld.view.data;

/**
 * Defines the data needed to draw a player.
 * @author Jake Stewart
 *
 */
public interface PlayerDisplayData extends CircleDisplayData {

    public String getObjectID();
    
    public int getHealth();
    
    public int getMaxHealth();

    public boolean isShielded();

    public boolean isDeadByFalling();
    
}
