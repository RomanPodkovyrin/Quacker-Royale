package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL45.*;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.graphics.Matrix2d;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

/**
 * Stores information about an object to display to the screen which can be made of multiple shapes.
 * @author Jake Stewart
 *
 */
public abstract class DisplayObject {

    private static Logger logger = LogManager.getLogger(DisplayObject.class);
    
    private final Matrix2d points;
    
    private final int displayType;
    
    private int verticesId;

    private int edgesId;

    private int vaoId;
    
    private int colourId;
    
    private int texture;
    
    private float r;
    
    private float g;
    
    private float b;

    public DisplayObject(Matrix2d points, int displayType) {
        this(points, displayType, (float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    public DisplayObject(Matrix2d points, int displayType, float r, float g, float b) {
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
     * Generates the points of a wall with thickness adding to the outside of the object.
     * @param w The width of the wall
     * @param h The height of the wall
     * @param t The thickness of the wall
     * @return The wall's points
     */
    protected static final Matrix2d genWall(float w, float h, float t) {
        Matrix2d points = new Matrix2d(4, 10);
        points.setValue(0, 0, -w / 2 - t);
        points.setValue(1, 0, h / 2 + t);
        points.setValue(0, 1, -w / 2);
        points.setValue(1, 1, h / 2);
        points.setValue(0, 2, w / 2 + t);
        points.setValue(1, 2, h / 2 + t);
        points.setValue(0, 3, w / 2);
        points.setValue(1, 3, h / 2);
        points.setValue(0, 4, w / 2 + t);
        points.setValue(1, 4, -h / 2 - t);
        points.setValue(0, 5, w / 2);
        points.setValue(1, 5, -h / 2);
        points.setValue(0, 6, -w / 2 - t);
        points.setValue(1, 6, -h / 2 - t);
        points.setValue(0, 7, -w / 2);
        points.setValue(1, 7, -h / 2);
        points.setValue(0, 8, -w / 2 - t);
        points.setValue(1, 8, h / 2 + t);
        points.setValue(0, 9, -w / 2);
        points.setValue(1, 9, h / 2);
        for (int j = 0; j < 10; j++) {
            points.setValue(3, j, 1f);
        }
        return points;
    }
    
    /**
     * Generates the points of a rectangle.
     * @param w The width of the rectangle
     * @param h The height of the rectangle
     * @return The points of the rectangle
     */
    protected static final Matrix2d genRectangle(float w, float h) {
        Matrix2d points = new Matrix2d(4, 4);
        points.setValue(0, 0, -w / 2);
        points.setValue(1, 0, h / 2);
        points.setValue(0, 1, w / 2);
        points.setValue(1, 1, h / 2);
        points.setValue(0, 2, w / 2);
        points.setValue(1, 2, -h / 2);
        points.setValue(0, 3, -w / 2);
        points.setValue(1, 3, -h / 2);
        
        for (int j = 0; j < 4; j++) {
            points.setValue(3, j, 1f);
        }
        
        return points;
    }
    
    /**
     * Generates the points of a circle.
     * @param r The radius of the circle
     * @return The points of the circle
     */
    protected static final Matrix2d genCircle(float r) {
        Matrix2d points = new Matrix2d(4, 38);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(3, 0, 1f);
        for (int i = 0; i <= 36; i += 1) {
            points.setValue(0, i + 1, r * (float)(Math.sin(((double)i / 18) * Math.PI)));
            points.setValue(1, i + 1, r * (float)(Math.cos(((double)i / 18) * Math.PI)));
            points.setValue(3, i + 1, 1f);
        }
        return points;
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
    
    public void draw() {
        logger.trace("Buffer vaoID " + vaoId + " " + (glIsVertexArray(vaoId) ? "exists" : "wasn't found"));
        logger.trace("Buffer vertices " + verticesId + " " + (glIsBuffer(verticesId) ? "exists" : "wasn't found"));
        logger.trace("Buffer edges " + edgesId + " " + (glIsBuffer(edgesId) ? "exists" : "wasn't found"));
        
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
     * Returns true if the camera should track the object.
     * @return if the camera should track the object
     */
    public abstract boolean shouldCameraFollow();
    
}
