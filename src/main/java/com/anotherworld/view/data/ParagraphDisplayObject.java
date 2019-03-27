package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.graphics.spritesheet.TextSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.nio.FloatBuffer;

public class ParagraphDisplayObject extends DisplayObject {

    private TextDisplayData displayData;
    
    /**
     * Creates a display object to drawn a block of text to the screen.
     * @param programme The programme to use for rendering
     * @param displayData The text to draw
     */
    public ParagraphDisplayObject(Programme programme, TextDisplayData displayData) {
        super(new TextSpriteSheet(), programme, TextSpriteSheet.generateParagraphLetterPoints(displayData.getText(), displayData.getTextHeight(), displayData.getWidth()), DrawType.QUADS, 1, 1, 1);
        this.displayData = displayData;
        programme.updateBuffers(this);
    }
    
    @Override
    public FloatBuffer getTextureBuffer() {
        if (displayData == null) {
            return super.getTextureBuffer();
        }
        return TextSpriteSheet.generateTexture(displayData.getText());
    }
    
    @Override
    public float getTheta() {
        return displayData.getAngle();
    }

    @Override
    public float getX() {
        return displayData.getXCoordinate();
    }

    @Override
    public float getY() {
        return displayData.getYCoordinate();
    }

    @Override
    public float getZ() {
        return 0;
    }

    @Override
    public boolean shouldDraw() {
        return true;
    }

    @Override
    public boolean shouldCameraFollow() {
        return false;
    }

}
