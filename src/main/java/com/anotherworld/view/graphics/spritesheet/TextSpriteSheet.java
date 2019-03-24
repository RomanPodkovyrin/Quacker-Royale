package com.anotherworld.view.graphics.spritesheet;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.texture.TextureMap;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class TextSpriteSheet extends SpriteSheet {
    
    @Override
    public boolean isTextured() {
        return true;
    }

    @Override
    public int getTextureId() {
        return 0;
    }
    
    @Override
    public int getTextureBuffer() {
        return TextureMap.TEXT_TEXTURE_BUFFER;
    }
    
    @Override
    public boolean shouldBeTransformed() {
        return false;
    }
    
    /**
     * Returns a set of points that define how text objects should be made to match with textures.
     * @param text The text to be drawn
     * @param characterSize The text height
     * @return The points to draw
     */
    public static Points2d generateLetterPoints(String text, float characterSize) {
        Points2d points = new Points2d(4, text.length() * 4);
        float characterWidth;
        try {
            Matrix dimensions = TextureMap.getSpriteDimensions(TextureMap.TEXT_TEXTURE_BUFFER);
            characterWidth = characterSize * (dimensions.getX() / dimensions.getY());
        } catch (Exception ex) {
            characterWidth = characterSize;
        }
        float xOff = characterWidth * text.length();
        xOff /= 2;
        for (int i = 0; i < text.length(); i++) {
            points.setValue(0, i * 4, i * characterWidth - xOff);
            points.setValue(1, i * 4, -characterSize / 2);
            points.setValue(0, i * 4 + 1, (i + 1) * characterWidth - xOff);
            points.setValue(1, i * 4 + 1,-characterSize / 2);
            points.setValue(0, i * 4 + 2, (i + 1) * characterWidth - xOff);
            points.setValue(1, i * 4 + 2,characterSize / 2);
            points.setValue(0, i * 4 + 3, (i) * characterWidth - xOff);
            points.setValue(1, i * 4 + 3, characterSize / 2);
            for (int j = 0; j < 4; j++) {
                points.setValue(3, i * 4 + j, 1);
            }
        }
        return points;
    }
    
    /**
     * Returns a buffer containing the texture co-ordinates need to map the text to the sprite sheet.
     * @param text The text to draw
     * @return the float buffer containing the points
     */
    public static FloatBuffer generateTexture(String text) {
        text = text.toUpperCase();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(text.length() * 8);
        Matrix dimensions = TextureMap.getDimensions(TextureMap.TEXT_TEXTURE_BUFFER);
        for (int i = 0; i < text.length(); i++) {
            int id = text.charAt(i);
            buffer.put((id % dimensions.getX()) / dimensions.getX());
            buffer.put((float)Math.floor((float)id / dimensions.getX()) / dimensions.getY());
            buffer.put(((id % dimensions.getX()) + 1) / dimensions.getX());
            buffer.put((float)Math.floor((float)id / dimensions.getX()) / dimensions.getY());
            buffer.put(((id % dimensions.getX()) + 1) / dimensions.getX());
            buffer.put((float)(Math.floor((float)(id) / dimensions.getX()) + 1) / dimensions.getY());
            buffer.put((id % dimensions.getX()) / dimensions.getX());
            buffer.put((float)(Math.floor((float)(id) / dimensions.getX()) + 1) / dimensions.getY());
        }
        buffer.flip();
        return buffer;
    }

}
