package com.anotherworld.view;

/**
 * Stores information about how the game will be rendered and changes opengl options to use it.
 * @author Jake Stewart
 *
 */
public interface Programme {

    /**
     * Switch to use this programme for rendering.
     */
    public void use();
    
    /**
     * Stop using this programme for rendering.
     */
    public void close();
    
    /**
     * Returns true if the programme supports rendering textures.
     * @return if the programme supports textures
     */
    public boolean supportsTextures();
    
    /**
     * Returns if the programme supports vertex array objects.
     * @return returns if the programme supports vaos
     */
    public boolean supportsVertArrayObj();

    /**
     * Deletes all of the opengl compiled code.s
     */
    public void delete();
    
}
