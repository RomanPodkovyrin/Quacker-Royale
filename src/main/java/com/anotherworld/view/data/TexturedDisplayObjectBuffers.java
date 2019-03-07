package com.anotherworld.view.data;

import java.util.Optional;

public class TexturedDisplayObjectBuffers extends DisplayObjectBuffers {
    
    private Optional<Integer> textureId;
    
    public TexturedDisplayObjectBuffers() {
        textureId = Optional.empty();
    }
    
    public void setTextureId(int id) {
        this.textureId = Optional.of(id);
    }
    
    public Optional<Integer> getTextureId() {
        return this.textureId;
    }

}
