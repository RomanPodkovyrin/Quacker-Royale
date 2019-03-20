package com.anotherworld.view;

import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.view.data.Points2d;

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
    
    public static Points2d generateLetterPoints(String text, float characterSize) {
        Points2d points = new Points2d(4, text.length() * 4);
        float xOff = characterSize * text.length();
        xOff /= 2;
        for (int i = 0; i < text.length(); i++) {
            points.setValue(0, i * 4, i * characterSize - xOff);
            points.setValue(1, i * 4, -characterSize / 2);
            points.setValue(0, i * 4 + 1, (i + 1) * characterSize - xOff);
            points.setValue(1, i * 4 + 1,-characterSize / 2);
            points.setValue(0, i * 4 + 2, (i + 1) * characterSize - xOff);
            points.setValue(1, i * 4 + 2,characterSize / 2);
            points.setValue(0, i * 4 + 3, (i) * characterSize - xOff);
            points.setValue(1, i * 4 + 3, characterSize / 2);
            for (int j = 0; j < 4; j++) {
                points.setValue(3, i * 4 + j, 1);
            }
        }
        return points;
    }
    
    public static FloatBuffer generateTexture(String text) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(text.length() * 8);
        Matrix dimensions = TextureMap.getDimensions(TextureMap.TEXT_TEXTURE_BUFFER);
        for (int i = 0; i < text.length(); i++) {
            int id = text.charAt(i);
            buffer.put((id % dimensions.getX()) / dimensions.getX());
            buffer.put((float)Math.floor((float)id / dimensions.getY()) / dimensions.getY());
            buffer.put(((id % dimensions.getX()) + 1) / dimensions.getX());
            buffer.put((float)Math.floor((float)id / dimensions.getY()) / dimensions.getY());
            buffer.put(((id % dimensions.getX()) + 1) / dimensions.getX());
            buffer.put((float)(Math.floor((float)(id) / dimensions.getX()) + 1) / dimensions.getY());
            buffer.put((id % dimensions.getX()) / dimensions.getX());
            buffer.put((float)(Math.floor((float)(id) / dimensions.getX()) + 1) / dimensions.getY());
        }
        System.out.println(text);
        buffer.flip();
        return buffer;
    }

}
