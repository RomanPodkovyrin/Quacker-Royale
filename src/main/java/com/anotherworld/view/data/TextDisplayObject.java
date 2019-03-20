package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.GL_QUADS;

import com.anotherworld.view.Programme;
import com.anotherworld.view.TextSpriteSheet;

import java.nio.FloatBuffer;

public class TextDisplayObject extends DisplayObject {
    
    private TextDisplayData data;
    
    public TextDisplayObject(Programme programme, TextDisplayData data) {
        super(new TextSpriteSheet(), programme, TextSpriteSheet.generateLetterPoints(data.getText(), data.getHeight() / 2), GL_QUADS, data.getTextR(), data.getTextG(), data.getTextB());
        this.data = data;
        programme.updateBuffers(this);
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
