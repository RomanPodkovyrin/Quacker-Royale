package com.anotherworld.view;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL46.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL46.GL_LINEAR;
import static org.lwjgl.opengl.GL46.GL_NEAREST;
import static org.lwjgl.opengl.GL46.GL_RGBA;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL46.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL46.glBindTexture;
import static org.lwjgl.opengl.GL46.glDeleteTextures;
import static org.lwjgl.opengl.GL46.glGenTextures;
import static org.lwjgl.opengl.GL46.glTexImage2D;
import static org.lwjgl.opengl.GL46.glTexParameteri;

import static org.lwjgl.stb.STBImage.STBI_rgb_alpha;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

import com.anotherworld.view.data.Matrix2d;
import com.anotherworld.view.data.Points2d;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * Creates and manages a map of different textures.
 * @author Jake Stewart
 *
 */
public class TextureMap {
    
    private final int height;
    
    private final int width;
    
    private final int comp;
    
    private final int xNumOfTextures;
    
    private final int yNumOfTextures;
    
    private final ByteBuffer pixels;
    
    private final int id;
    
    /**
     * Loads the texture map from the specified location.
     * @param location the texture map location
     */
    public TextureMap(String location) throws IOException {
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        pixels = stbi_load(location, x, y, comp, STBI_rgb_alpha);
        height = y.get();
        width = x.get();
        xNumOfTextures = 4;
        yNumOfTextures = 5;
        this.comp = comp.get();
        if (pixels == null) {
            throw new IOException(stbi_failure_reason());
        }
        pixels.limit(pixels.capacity());
        id = loadTextureMap();
    }

    
    private int loadTextureMap() {
        int id = glGenTextures();
        
        glBindTexture(GL_TEXTURE_2D, id);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        glTexImage2D(GL_TEXTURE_2D, 0, this.getEncoding(), this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, this.getPixels());
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
        return id;
    }
    
    public void destroy() {
        glDeleteTextures(GL_TEXTURE_2D);
    }

    /**
     * Returns the height of the texture map in pixels.
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the texture map in pixels.
     * @return the width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns the type of opengl enum encoding used by the loaded image.
     * @return the type of encoding
     */
    private int getEncoding() {
        return GL_RGBA;
    }
    
    /**
     * Returns the opengl texture id.
     * @return the opengl id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the pixels as a float array.
     * @return the pixels
     */
    public ByteBuffer getPixels() {
        pixels.position(0);
        pixels.limit(pixels.capacity());
        return pixels;
    }
    
    @Override
    public String toString() {
        String s = "x: " + width + "y: " + height + "# of channels: " + comp + "size: " + pixels.capacity() + "\n";
        //TODO make this more efficient
        while (pixels.hasRemaining()) {
            s = s + pixels.get() + ";";
        }
        return s;
    }

    /**
     * The transformation required to move the object co-ords to the texture map co-ords.
     * @param textureId The texture id
     * @return The required transformation
     */
    public Points2d getTextureTransformation(int textureId) {
        Matrix2d transformation = Matrix2d.homScale3d(1, 1, 0); //Remove z
        transformation = Matrix2d.homTranslate3d(textureId % xNumOfTextures, (int)Math.floor(textureId / xNumOfTextures), 0).mult(transformation);
        transformation = Matrix2d.homScale3d(1 / (float)xNumOfTextures, 1 / (float)yNumOfTextures, 0).mult(transformation);
        return transformation;
    }
    
    public void loadTexture(int programmeId, SpriteSheet spriteSheet, float xShear, float yShear) {

        //Change this to select right texture from buffer
        glUniform1i(glGetUniformLocation(programmeId, "hasTex"), spriteSheet.isTextured() ? 1 : 0);
        
        if (spriteSheet.isTextured()) {
            float[] matrix = this.getTextureTransformation(spriteSheet.getTextureId()).getPoints();
            FloatBuffer temp2 = BufferUtils.createFloatBuffer(16);
            temp2.put(matrix);
            temp2.flip();
            glUniformMatrix4fv(glGetUniformLocation(programmeId, "textureTransformation"), false, temp2);
            glUniform2f(glGetUniformLocation(programmeId, "Shear"), xShear, yShear);
        }
        
    }
    
}
