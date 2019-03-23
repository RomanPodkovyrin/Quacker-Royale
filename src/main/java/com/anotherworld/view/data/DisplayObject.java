package com.anotherworld.view.data;

import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

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

    private final float xShear;
    
    private final float yShear;
    
    private Points2d points;
    
    private final int displayType;
    
    private final Programme programme;
    
    private final int programmeObjectId;
    
    private float r;
    
    private float g;
    
    private float b;
    
    private SpriteSheet spriteSheet;

    public DisplayObject(Programme programme, Points2d points, int displayType) {
        this(new SpriteSheet(), programme, points, displayType, (float)Math.random(), (float)Math.random(), (float)Math.random());
    }

    public DisplayObject(SpriteSheet spriteSheet, Programme programme, Points2d points, int displayType) {
        this(spriteSheet, programme, points, displayType, (float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    public DisplayObject(Programme programme, Points2d points, int displayType, float r, float g, float b) {
        this(new SpriteSheet(), programme, points, displayType, r, g, b);
    }
    
    /**
     * Creates a display object from the given points.
     * @param programme The programme used to display the object
     * @param points The points to display the object
     * @param displayType The type of opengl object to display
     * @param r How red the object is 0 to 1
     * @param g How green the object is 0 to 1
     * @param b How blue the object is 0 to 1
     */
    public DisplayObject(SpriteSheet spriteSheet, Programme programme, Points2d points, int displayType, float r, float g, float b) {
        this.points = points;
        this.displayType = displayType;
        this.r = r;
        this.g = g;
        this.b = b;
        this.programme = programme;
        this.xShear = 1;
        this.yShear = 1;
        this.spriteSheet = spriteSheet;
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
        programme.translatef(this.getX(), this.getY(), this.getZ());
        //programme.rotatef(-this.getTheta(), 0, 0, 1);
    }
    
    /**
     * Returns a float buffer containing the object co-ordinate points for the display object.
     * @return the point buffer for the display object.
     */
    public FloatBuffer getVertexBuffer() {
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
    
    /**
     * Returns an int buffer containing the edges of the object.
     * @return the edges
     */
    public IntBuffer getEdgeBuffer() {
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
    
    private float getZScale() {
        return -getScale(2);
    }
    
    private float getScale(int axis) {
        if (points.getM() == 0 || points.getN() == 0) {
            return 1;
        }
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
    
    /**
     * Returns a float buffer containing the texture co-ordinates for each point.
     * @return the texture co-ordinates
     */
    public FloatBuffer getTextureBuffer() {
        FloatBuffer b = BufferUtils.createFloatBuffer(points.getPoints().length);
        float xScale = getXScale();
        float yScale = getYScale();
        for (int j = 0; j < points.getN(); j++) {
            b.put((points.getValue(0, j) / xScale) + 0.5f);
            b.put((points.getValue(1, j) / yScale) + 0.5f);
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
     * Returns the z position of the object.
     * @return the z position
     */
    public abstract float getZ();
    
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

    public float getXShear() {
        return xShear;
    }

    public float getYShear() {
        return yShear;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }
    
    @Override
    public String toString() {
        return points.toString();
    }

    public void updatePoints(Points2d points) {
        this.points = points;
    }
    
}
