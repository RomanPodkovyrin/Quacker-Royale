package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

/**
 * Stores and displays information about an object to display to the screen which can be made of multiple shapes.
 * @author Jake Stewart
 *
 */
public abstract class DisplayObject {

    private static Logger logger = LogManager.getLogger(DisplayObject.class);
    
    private final Points2d points;
    
    private final int displayType;
    
    private int verticesId;

    private int edgesId;

    private int vaoId;
    
    private int colourId;
    
    private int texture;
    
    private float r;
    
    private float g;
    
    private float b;

    public DisplayObject(Points2d points, int displayType) {
        this(points, displayType, (float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    /**
     * Creates a display object from the given points.
     * @param points The points to display the object
     * @param displayType The way the points should be displayed
     * @param r How red the object is 0 to 1
     * @param g How green the object is 0 to 1
     * @param b How blue the object is 0 to 1
     */
    public DisplayObject(Points2d points, int displayType, float r, float g, float b) {
        this.points = points;
        this.displayType = displayType;
        this.r = r;
        this.g = g;
        this.b = b;
        createOpenglObjects(this);
    }
    
    private static void createOpenglObjects(DisplayObject displayObject) {
        
        displayObject.vaoId = glGenVertexArrays();
        glBindVertexArray(displayObject.vaoId);
        
        displayObject.verticesId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.verticesId);
        FloatBuffer f = displayObject.getFloatBuffer();
        glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
        
        glEnableVertexAttribArray(0);
        
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        displayObject.colourId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.colourId);
        FloatBuffer g = displayObject.getColourBuffer();
        glBufferData(GL_ARRAY_BUFFER, g, GL_STATIC_DRAW);
        
        glEnableVertexAttribArray(1);
        
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        //displayObject.texture = glGenBuffers();
        //glBindBuffer(GL_TEXTURE_2D, displayObject.texture);
        
        glBindVertexArray(0);
        
        displayObject.edgesId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, displayObject.edgesId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, displayObject.getIndexBuffer(), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        
        
    }
    
    /**
     * Cleans opengl of the object's representation.
     */
    public void destroyObject() {
        logger.debug("Destroying object containing vaoId " + vaoId + ", vertices " + verticesId + ", edges " + edgesId);
        glDeleteBuffers(verticesId);
        glDeleteBuffers(edgesId);
        glDeleteBuffers(vaoId);
    }
    
    /**
     * Returns the display mode needed to correctly display the object's points.
     * @return The opengl display mode
     */
    private int getDisplayType() {
        return displayType;
    }
    
    /**
     * Draws the object using the stored points.
     */
    public void draw() {
        logger.trace("Buffer vaoID " + vaoId + " " + (glIsVertexArray(vaoId) ? "exists" : "wasn't found"));
        logger.trace("Buffer vertices " + verticesId + " " + (glIsBuffer(verticesId) ? "exists" : "wasn't found"));
        logger.trace("Buffer edges " + edgesId + " " + (glIsBuffer(edgesId) ? "exists" : "wasn't found"));
        
        if (this.shouldDraw()) {
            glBindVertexArray(vaoId);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
    
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, edgesId);
            
            glDrawElements(this.getDisplayType(), this.points.getN(), GL_UNSIGNED_INT, 0);
    
            glDisableClientState(GL_VERTEX_ARRAY);
    
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
        }
        
    }
    
    public void transform() {
        glTranslatef(this.getX(), this.getY(), 0);
        glRotatef(-this.getTheta(), 0, 0, 1);
    }
    
    private FloatBuffer getFloatBuffer() {
        FloatBuffer b = BufferUtils.createFloatBuffer(points.getPoints().length);
        b.put(points.getPoints());
        b.flip();
        return b;
    }
    
    private FloatBuffer getColourBuffer() {
        FloatBuffer buff = BufferUtils.createFloatBuffer(points.getPoints().length);
        for (int i = 0; i < points.getN(); i++) {
            buff.put(this.r);
            buff.put(this.g);
            buff.put(this.b);
            buff.put(1f);
        }
        buff.flip();
        return buff;
    }
    
    private IntBuffer getIndexBuffer() {
        IntBuffer b = BufferUtils.createIntBuffer(points.getN());
        int[] bs = new int[points.getN()];
        for (int i = 0; i < points.getN(); i++) {
            bs[i] = i;
        }
        b.put(bs);
        b.flip();
        return b;
    }
    
    protected void setColour(float r, float g, float b) {
        if (floatNotEq(this.r, r) || floatNotEq(this.g, g) || floatNotEq(this.b, b)) {
            this.r = r;
            this.g = g;
            this.b = b;
            glBindBuffer(GL_ARRAY_BUFFER, this.colourId);
            FloatBuffer f = this.getColourBuffer();
            glBufferData(GL_ARRAY_BUFFER, f, GL_DYNAMIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }
    
    private boolean floatNotEq(float a, float b) {
        if (a != b) {
            return true;
        }
        return false;
    }
    
    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }
    
    /**
     * Returns the angle of the object in degrees.
     * @return the angle of the object
     */
    public abstract float getTheta();
    
    /**
     * Returns the x position of the object.
     * @return the x position
     */
    public abstract float getX();
    
    /**
     * Returns the y position of the object.
     * @return the y position
     */
    public abstract float getY();
    
    /**
     * Returns true if the object should be drawn.
     * @return if the object should be drawn
     */
    public abstract boolean shouldDraw();
    
    /**
     * Returns true if the camera should track the object.
     * @return if the camera should track the object
     */
    public abstract boolean shouldCameraFollow();
    
}
