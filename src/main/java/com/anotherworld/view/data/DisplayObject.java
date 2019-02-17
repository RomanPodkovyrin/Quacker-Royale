package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glEnableClientState;
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
public class DisplayObject {

    private static Logger logger = LogManager.getLogger(DisplayObject.class);
    
    private final DisplayData displayData;
    
    private final Matrix2d points;
    
    private final int displayType;
    
    private int vertices;
    
    private boolean cameraShouldFollow;

    private int edges;

    private int vaoId;
    
    /**
     * Creates a display object to display a ball.
     * @param displayData The ball data to display
     */
    public DisplayObject(BallDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getRadius());
        displayType = GL_TRIANGLE_FAN;
        cameraShouldFollow = false;
        createOpenglObjects(this);
    }

    /**
     * Creates a display object to display a rectangle like the platform.
     * @param displayData The rectangle to display.
     */
    public DisplayObject(RectangleDisplayData displayData) {
        this.displayData = displayData;
        points = genRectangle(displayData.getWidth(), displayData.getHeight());
        displayType = GL_TRIANGLE_FAN;
        cameraShouldFollow = false;
        createOpenglObjects(this);
    }

    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public DisplayObject(PlayerDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getRadius());
        displayType = GL_TRIANGLE_FAN;
        cameraShouldFollow = true;
        createOpenglObjects(this);
    }
    
    /**
     * Creates a display object to display the wall.
     * @param displayData The wall to display
     */
    public DisplayObject(WallData displayData) {
        this.displayData = displayData;
        this.points = genWall(displayData.getWidth(), displayData.getHeight(), 1);
        this.displayType = GL_TRIANGLE_STRIP;
        cameraShouldFollow = false;
        createOpenglObjects(this);
    }
    
    private static void createOpenglObjects(DisplayObject displayObject) {
        
        displayObject.vaoId = glGenVertexArrays();
        glBindVertexArray(displayObject.vaoId);
        
        
        displayObject.vertices = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.vertices);
        FloatBuffer f = displayObject.getFloatBuffer();
        glBufferData(GL_ARRAY_BUFFER, f, GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        
        glEnableVertexAttribArray(0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        displayObject.edges = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, displayObject.edges);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, displayObject.getIndexBuffer(), displayObject.edges);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        
        glBindVertexArray(0);
        
        
    }
    
    /**
     * Cleans opengl of the object's representation.
     */
    public void destroyObject() {
        logger.trace("Destroying object containing " + displayData.toString());
        glDeleteBuffers(vertices);
        glDeleteBuffers(edges);
        glDeleteBuffers(vaoId);
    }
    
    /**
     * Generates the points of a wall with thickness adding to the outside of the object.
     * @param w The width of the wall
     * @param h The height of the wall
     * @param t The thickness of the wall
     * @return The wall's points
     */
    private final Matrix2d genWall(float w, float h, float t) {
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
    private final Matrix2d genRectangle(float w, float h) {
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
    private final Matrix2d genCircle(float r) {
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
     * Returns the display mode needed to correctly display the object's points.
     * @return The opengl display mode
     */
    public int getDisplayType() {
        return displayType;
    }
    
    /**
     * Returns the angle of the object in degrees.
     * @return the angle of the object
     */
    public float getTheta() {
        return displayData.getAngle();
    }
    
    /**
     * Returns the x position of the object.
     * @return the x position
     */
    public float getX() {
        return displayData.getXCoordinate();
    }
    
    /**
     * Returns the y position of the object.
     * @return the y position
     */
    public float getY() {
        return displayData.getYCoordinate();
    }
    
    public void draw() {
        logger.trace("Buffer vaoID " + (glIsBuffer(vaoId) ? "exists" : "wasn't found"));
        logger.trace("Buffer vertices " + (glIsBuffer(vertices) ? "exists" : "wasn't found"));
        logger.trace("Buffer edges " + (glIsBuffer(edges) ? "exists" : "wasn't found"));
        
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vertices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, edges);
        glVertexPointer(4, GL_FLOAT, 0, 0l);
        glEnableClientState(GL_VERTEX_ARRAY);
        
        glDrawArrays(GL_POINTS, 0, this.points.getN());

        glDisableClientState(GL_VERTEX_ARRAY);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        
    }
    
    public void transform() {
        glTranslatef(this.getX(), this.getY(), 0);
        glRotatef(-this.getTheta(), 0, 0, 1);
        animate();
    }
    
    private void animate() {
        
    }
    
    private FloatBuffer getFloatBuffer() {
        FloatBuffer b = BufferUtils.createFloatBuffer(points.getPoints().length);
        b.put(points.getPoints());
        b.flip();
        return b;
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
    
    /**
     * Returns true if the camera should track the object.
     * @return if the camera should track the object
     */
    public boolean shouldCameraFollow() {
        return cameraShouldFollow;
    }
    
}
