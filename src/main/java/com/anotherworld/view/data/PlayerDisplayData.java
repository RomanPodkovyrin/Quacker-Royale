package com.anotherworld.view.data;

/**
 * Defines the data needed to draw a player.
 * @author Jake Stewart
 *
 */
public interface PlayerDisplayData extends CircleDisplayData {

    public String getCharacterID();
    
    public int getHealth();
    
    public int getMaxHealth();
    
}
