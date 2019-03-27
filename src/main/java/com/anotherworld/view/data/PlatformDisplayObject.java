package com.anotherworld.view.data;

import com.anotherworld.view.graphics.spritesheet.PlatformSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.nio.FloatBuffer;

public class PlatformDisplayObject extends RectangleDisplayObject {

    private RectangleDisplayData displayData;
    
    public PlatformDisplayObject(Programme programme, RectangleDisplayData displayData) {
        super(new PlatformSpriteSheet(), programme, displayData);
        this.displayData = displayData;
    }
    
    @Override
    public FloatBuffer getTextureBuffer() {
        return PlatformSpriteSheet.getBuffer(displayData.getWidth(), displayData.getHeight());
    }

}
