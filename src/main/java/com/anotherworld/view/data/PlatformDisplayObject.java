package com.anotherworld.view.data;

import com.anotherworld.view.graphics.spritesheet.PlatformSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.nio.FloatBuffer;

public class PlatformDisplayObject extends RectangleDisplayObject {

    private RectangleDisplayData displayData;
    
    private final Programme programme;
    
    private final float maxWidth;
    
    private final float maxHeight;
    
    /**
     * Creates a display object to render a platform.
     * @param programme The programme to use for rendering
     * @param displayData The data to use for the object
     */
    public PlatformDisplayObject(Programme programme, RectangleDisplayData displayData) {
        super(new PlatformSpriteSheet(), programme, displayData);
        this.displayData = displayData;
        this.maxWidth = displayData.getWidth();
        this.maxHeight = displayData.getHeight();
        this.programme = programme;
    }
    
    @Override
    public FloatBuffer getTextureBuffer() {
        return PlatformSpriteSheet.getBuffer(maxWidth / 2, maxHeight / 2, displayData.getWidth(), displayData.getHeight());
    }
    
    @Override
    public void transform() {
        super.transform();
        
        programme.updateBuffers(this);
        
        programme.scalef(getXShear(), getYShear(), 1f);
        
    }
    
    public float getXShear() {
        return displayData.getWidth() / maxWidth;
    }
    
    public float getYShear() {
        return displayData.getHeight() / maxHeight;
    }

}
