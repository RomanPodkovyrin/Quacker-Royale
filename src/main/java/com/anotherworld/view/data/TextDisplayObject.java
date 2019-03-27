package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.graphics.spritesheet.TextSpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.nio.FloatBuffer;

public class TextDisplayObject extends DisplayObject {
    
    private TextDisplayData data;
    
    private Programme programme;
    
    private String currentText = "";
    
    /**
     * Creates an object that displays the text given by the text display data.
     * @param programme The programme to use for drawing
     * @param data The text data to display
     */
    public TextDisplayObject(Programme programme, TextDisplayData data) {
        super(new TextSpriteSheet(), programme, TextSpriteSheet.generateLetterPoints(data.getText(), data.getHeight() / 2), DrawType.QUADS, data.getTextR(), data.getTextG(), data.getTextB());
        this.data = data;
        this.programme = programme;
    }
    
    @Override
    public void transform() {
        super.transform();
        this.setColour(data.getTextR(), data.getTextG(), data.getTextB(), 1);
        if (!currentText.equals(data.getText())) {
            super.updatePoints(TextSpriteSheet.generateLetterPoints(data.getText(), data.getHeight() / 2));
            programme.updateBuffers(this);
            currentText = data.getText();
        }
    }

    @Override
    public float getTheta() {
        return data.getAngle();
    }

    @Override
    public float getX() {
        return data.getXCoordinate();
    }

    @Override
    public float getY() {
        return data.getYCoordinate();
    }

    @Override
    public float getZ() {
        return 0;
    }
    
    @Override
    public FloatBuffer getTextureBuffer() {
        if (data == null) {
            return super.getTextureBuffer();
        }
        return TextSpriteSheet.generateTexture(data.getText());
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
