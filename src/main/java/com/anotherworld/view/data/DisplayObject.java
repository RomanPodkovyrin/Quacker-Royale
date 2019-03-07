package com.anotherworld.view.data;

import com.anotherworld.view.Programme;

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
    
    private final boolean isTextured;
    
    private final Programme programme;
    
    private final int programmeObjectId;
    
    private float r;
    
    private float g;
    
    private float b;

    public DisplayObject(Programme programme, Points2d points, int displayType, boolean isTextured) {
        this(programme, points, displayType, isTextured, (float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    /**
     * Creates a display object from the given points.
     * @param points The points to display the object
     * @param displayType The way the points should be displayed
     * @param isTextured If the object has a texture mapped to it
     * @param r How red the object is 0 to 1
     * @param g How green the object is 0 to 1
     * @param b How blue the object is 0 to 1
     */
    public DisplayObject(Programme programme, Points2d points, int displayType, boolean isTextured, float r, float g, float b) {
        this.points = points;
        this.displayType = displayType;
        this.isTextured = isTextured;
        this.r = r;
        this.g = g;
        this.b = b;
        this.programme = programme;
        programmeObjectId = programme.initialiseDisplayObject(this);
    }
    
    /**
     * Cleans opengl of the object's representation.
     */
    public void destroyObject() {
        programme.deleteObject(this);
    }
    
    /**
     * Returns the display mode needed to correctly display the object's points.
     * @return The opengl display mode
     */
    public int getDisplayType() {
        return displayType;
    }
    
    /**
     * Draws the object using the stored points.
     */
    public void draw() {
        
        if (this.shouldDraw()) {
            
            programme.draw(this);
        }
        
    }
    
    public void transform() {
        programme.translatef(this.getX(), this.getY(), 0);
        programme.rotatef(-this.getTheta(), 0, 0, 1);
    }
    
    public FloatBuffer getFloatBuffer() {
        FloatBuffer b = BufferUtils.createFloatBuffer(points.getPoints().length);
        for (Float f : points.getPoints()) {
            b.put(f);
        }
        b.flip();
        return b;
    }
    
    public FloatBuffer getColourBuffer() {
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
    
    public IntBuffer getIndexBuffer() {
        IntBuffer b = BufferUtils.createIntBuffer(points.getN());
        for (int i = 0; i < points.getN(); i++) {
            b.put(i);
        }
        b.flip();
        return b;
    }
    
    private float getXScale() {
        return getScale(0);
    }
    
    private float getYScale() {
        return getScale(1);
    }
    
    private float getScale(int axis) {
        float min = points.getValue(axis, 0);
        float max = points.getValue(axis, 0);
        for (int j = 1; j < points.getN(); j++) {
            if (points.getValue(axis, j) < min) {
                min = points.getValue(axis, j);
            } else if (points.getValue(axis, j) > max) {
                max = points.getValue(axis, j);
            }
        }
        return max - min;
    }
    
    public FloatBuffer getTextureBuffer() {
        FloatBuffer b = BufferUtils.createFloatBuffer(points.getPoints().length);
        float xScale = getXScale();
        float yScale = getYScale();
        for (int j = 0; j < points.getN(); j++) {
            b.put((points.getValue(0, j) / xScale) + 0.5f);
            b.put(-(points.getValue(1, j) / yScale) + 0.5f);
        }
        b.flip();
        return b;
    }
    
    protected void setColour(float r, float g, float b) {
        if (floatNotEq(this.r, r) || floatNotEq(this.g, g) || floatNotEq(this.b, b)) {
            this.r = r;
            this.g = g;
            this.b = b;
            programme.updateObjectColour(this);
        }
    }
    
    private boolean floatNotEq(float a, float b) {
        if (a != b) {
            return true;
        }
        return false;
    }
    
    public int getProgrammeObjectId() {
        return programmeObjectId;
    }
    
    /**
     * Returns how red the object is.
     * @return how red the object is
     */
    public float getR() {
        return r;
    }

    /**
     * Returns how green the object is.
     * @return how green the object is
     */
    public float getG() {
        return g;
    }

    /**
     * Returns how blue the object is.
     * @return how blue the object is
     */
    public float getB() {
        return b;
    }
    
    public boolean hasTexture() {
        return isTextured;
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

    public int getNumberOfPoints() {
        return this.points.getN();
    }
    
}
