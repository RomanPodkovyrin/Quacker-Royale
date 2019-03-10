package com.anotherworld.view.data;

import java.util.Optional;

/**
 * Creates the opengl buffers for a textured displayobject.
 */
public class TexturedDisplayObjectBuffers extends DisplayObjectBuffers {
    
    private Optional<Integer> textureId;
    
    /**
     * Initialises the buffer values to unknown.
     */
    public TexturedDisplayObjectBuffers() {
        textureId = Optional.empty();
    }
    
    /**
     * Assigns the texture buffer id for the display object.
     * @param id the texture buffer id
     */
    public void setTextureId(int id) {
        this.textureId = Optional.of(id);
    }
    
    /**
     * returns the texture buffer id for the display object.
     * @return the texture buffer id
     */
    public Optional<Integer> getTextureId() {
        return this.textureId;
    }

}
