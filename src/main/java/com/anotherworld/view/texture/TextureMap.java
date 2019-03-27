package com.anotherworld.view.texture;

import static org.lwjgl.opengl.GL46.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46.glBindTexture;
import static org.lwjgl.opengl.GL46.glGetUniformLocation;
import static org.lwjgl.opengl.GL46.glUniform1i;
import static org.lwjgl.opengl.GL46.glUniformMatrix4fv;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.view.graphics.spritesheet.SpriteLocation;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

/**
 * Creates and manages a map of different textures.
 * @author Jake Stewart
 *
 */
public class TextureMap {
    
    private static TextureBuffer[] textureBuffers;
    
    /**
     * Loads the textures from files the specified folder.
     * @param location the texture folder location
     */
    public TextureMap(String location) throws IOException {
        textureBuffers = new TextureBuffer[SpriteLocation.NUMBER_OF_LOCATIONS];
        textureBuffers[SpriteLocation.NONE.getInt()] = new TextureBuffer(location + "alien.png", 1, 1);
        textureBuffers[SpriteLocation.PLAYER.getInt()] = new TextureBuffer(location + "mega duck.png", 4, 11);
        textureBuffers[SpriteLocation.SAFE_BALL.getInt()] = new TextureBuffer(location + "basketball.png", 2, 2);
        textureBuffers[SpriteLocation.DANGEROUS_BALL.getInt()] = new TextureBuffer(location + "DangerBall.png", 2, 2);
        textureBuffers[SpriteLocation.TEXT.getInt()] = new TextureBuffer(location + "tom_font.png", 32, 4);
        textureBuffers[SpriteLocation.POWER_UP.getInt()] = new TextureBuffer(location + "powerups.png", 4, 3);
        textureBuffers[SpriteLocation.POLICE_HAT.getInt()] = new TextureBuffer(location + "police.png", 4, 5);
        textureBuffers[SpriteLocation.PIRATE_HAT.getInt()] = new TextureBuffer(location + "pirate.png", 4, 5);
        textureBuffers[SpriteLocation.ROBBER_HAT.getInt()] = new TextureBuffer(location + "robber.png", 4, 5);
        textureBuffers[SpriteLocation.SANTA_HAT.getInt()] = new TextureBuffer(location + "santa.png", 4, 5);
        textureBuffers[SpriteLocation.BACKGROUND.getInt()] = new TextureBuffer(location + "Background.png", 2, 5);
        textureBuffers[SpriteLocation.INSTRUCTIONS.getInt()] = new TextureBuffer(location + "instructions.png", 1, 1);
        textureBuffers[SpriteLocation.TILE.getInt()] = new TextureBuffer(location + "tile.png", 1, 1);
    }
    
    /**
     * Loads a texture the correct texture for an object to the opengl buffers.
     * @param programmeId The programme id to use
     * @param spriteSheet The sprite sheet to load
     */
    public void loadTexture(int programmeId, SpriteSheet spriteSheet) {

        glUniform1i(glGetUniformLocation(programmeId, "hasTex"), spriteSheet.isTextured() ? 1 : 0);
        
        if (spriteSheet.isTextured()) {
            glBindTexture(GL_TEXTURE_2D, textureBuffers[spriteSheet.getTextureBuffer().getInt()].getId());
            glUniform1i(glGetUniformLocation(programmeId, "tex"), 0);
            float[] matrix = textureBuffers[spriteSheet.getTextureBuffer().getInt()].getTextureTransformation(spriteSheet).getPoints();
            FloatBuffer temp2 = BufferUtils.createFloatBuffer(16);
            temp2.put(matrix);
            temp2.flip();
            glUniformMatrix4fv(glGetUniformLocation(programmeId, "textureTransformation"), false, temp2);
        }
        
    }

    /**
     * Destroys the opengl buffers for the textures.
     */
    public void destroy() {
        for (TextureBuffer textureBuffer : textureBuffers) {
            textureBuffer.destroy();
        }
        textureBuffers = new TextureBuffer[0];
    }

    public static Matrix getDimensions(SpriteLocation spriteLocation) {
        return new Matrix(textureBuffers[spriteLocation.getInt()].getXNumOfTextures(), textureBuffers[spriteLocation.getInt()].getYNumOfTextures());
    }

    public static Matrix getSpriteDimensions(SpriteLocation spriteLocation) {
        return new Matrix(textureBuffers[spriteLocation.getInt()].getWidth() / textureBuffers[spriteLocation.getInt()].getXNumOfTextures(),
                textureBuffers[spriteLocation.getInt()].getHeight() / textureBuffers[spriteLocation.getInt()].getYNumOfTextures());
    }
    
}
