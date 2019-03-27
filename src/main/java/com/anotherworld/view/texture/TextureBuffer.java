package com.anotherworld.view.texture;

import static org.lwjgl.opengl.GL46.GL_REPEAT;
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

import com.anotherworld.view.data.primatives.Matrix2d;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class TextureBuffer {
    
    private final int height;
    
    private final int width;
    
    private final int numOfChannels;
    
    private final int xNumOfTextures;
    
    private final int yNumOfTextures;
    
    private final ByteBuffer pixels;
    
    private final int id;
    
    /**
     * Creates a texture buffer from the image stored at the location.
     * @param location The image location
     * @param xNumOfTextures The number of texture tiles in the x axis
     * @param yNumOfTextures The number of texture tiles in the y axis
     * @throws IOException If the image couldn't be loaded
     */
    public TextureBuffer(String location, int xNumOfTextures, int yNumOfTextures) throws IOException {
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        pixels = stbi_load(location, x, y, comp, STBI_rgb_alpha);
        width = x.get();
        height = y.get();
        this.xNumOfTextures = xNumOfTextures;
        this.yNumOfTextures = yNumOfTextures;
        this.numOfChannels = comp.get();
        if (pixels == null) {
            throw new IOException(stbi_failure_reason());
        }
        pixels.limit(pixels.capacity());
        id = loadTextureMap();
    }
    
    private int loadTextureMap() {
        int id = glGenTextures();
        
        glBindTexture(GL_TEXTURE_2D, id);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        
        glTexImage2D(GL_TEXTURE_2D, 0, this.getEncoding(), this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, this.getPixels());
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
        return id;
    }
    
    /**
     * Destroys the open gl buffer.
     */
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
    
    public int getXNumOfTextures() {
        return xNumOfTextures;
    }
    
    public int getYNumOfTextures() {
        return yNumOfTextures;
    }

    /**
     * The transformation required to move the object co-ords to the texture map co-ords.
     * @param spriteSheet The texture id
     * @return The required transformation
     */
    public Points2d getTextureTransformation(SpriteSheet spriteSheet) {
        Matrix2d transformation = Matrix2d.homScale3d(1, 1, 0); //Remove z
        if (spriteSheet.shouldBeTransformed()) {
            transformation = Matrix2d.homTranslate3d(spriteSheet.getTextureId() % xNumOfTextures, (int)Math.floor(spriteSheet.getTextureId() / xNumOfTextures), 0).mult(transformation);
            transformation = Matrix2d.homScale3d(1 / (float)xNumOfTextures, 1 / (float)yNumOfTextures, 0).mult(transformation);
        }
        return transformation;
    }
    
    @Override
    public String toString() {
        String s = "x: " + width + "y: " + height + "# of channels: " + numOfChannels + "size: " + pixels.capacity() + "\n";
        //TODO make this more efficient
        while (pixels.hasRemaining()) {
            s = s + pixels.get() + ";";
        }
        return s;
    }

}
