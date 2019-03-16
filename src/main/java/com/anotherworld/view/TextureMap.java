package com.anotherworld.view;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL46.glGetUniformLocation;
import static org.lwjgl.opengl.GL46.glUniform1i;
import static org.lwjgl.opengl.GL46.glUniform2f;
import static org.lwjgl.opengl.GL46.glUniformMatrix4fv;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * Creates and manages a map of different textures.
 * @author Jake Stewart
 *
 */
public class TextureMap {
    
    private TextureBuffer textureBuffer;
    
    /**
     * Loads the texture map from the specified location.
     * @param location the texture map location
     */
    public TextureMap(String location) throws IOException {
        textureBuffer = new TextureBuffer(location, 4, 5);
    }
    
    /**
     * Loads a texture the correct texture for an object to the opengl buffers.
     * @param programmeId The programme id to use
     * @param spriteSheet The sprite sheet to load
     * @param xShear The x shear on the sprite
     * @param yShear The y shear on the sprite
     */
    public void loadTexture(int programmeId, SpriteSheet spriteSheet, float xShear, float yShear) {

        //Change this to select right texture from buffer
        glUniform1i(glGetUniformLocation(programmeId, "hasTex"), spriteSheet.isTextured() ? 1 : 0);
        
        if (spriteSheet.isTextured()) {
            glBindTexture(GL_TEXTURE_2D, textureBuffer.getId());
            glUniform1i(glGetUniformLocation(programmeId, "tex"), 0);
            float[] matrix = textureBuffer.getTextureTransformation(spriteSheet.getTextureId()).getPoints();
            FloatBuffer temp2 = BufferUtils.createFloatBuffer(16);
            temp2.put(matrix);
            temp2.flip();
            glUniformMatrix4fv(glGetUniformLocation(programmeId, "textureTransformation"), false, temp2);
            glUniform2f(glGetUniformLocation(programmeId, "Shear"), xShear, yShear);
        }
        
    }

    public void destroy() {
        textureBuffer.destroy();
    }
    
}
