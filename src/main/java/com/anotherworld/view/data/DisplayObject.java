package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.graphics.Matrix2d;

/**
 * Stores information about an object to display to the screen which can be made of multiple shapes.
 * @author Jake Stewart
 *
 */
public class DisplayObject {

    private final DisplayData displayData;
    
    private final Matrix2d points;
    private Matrix2d tempPoints;
    
    private final int displayType;

    private float rColour;
    private float gColour;
    private float bColour;
    
    /**
     * Creates a display object to display a ball.
     * @param displayData The ball data to display
     */
    public DisplayObject(BallDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getRadius());
        displayType = GL_TRIANGLE_FAN;
        setColours();
    }

    /**
     * Creates a display object to display a rectangle like the platform.
     * @param displayData The rectangle to display.
     */
    public DisplayObject(RectangleDisplayData displayData) {
        this.displayData = displayData;
        points = genRectangle(displayData.getWidth(), displayData.getHeight());
        displayType = GL_TRIANGLE_FAN;
        setColours();
    }

    /**
     * Creates a display object to display a player.
     * @param displayData The player to display
     */
    public DisplayObject(PlayerDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getRadius());
        displayType = GL_TRIANGLE_FAN;
        setColours();
    }
    
    /**
     * Creates a display object to display the wall.
     * @param displayData The wall to display
     */
    public DisplayObject(WallData displayData) {
        this.displayData = displayData;
        this.points = genWall(displayData.getWidth(), displayData.getHeight(), 1);
        this.displayType = GL_TRIANGLE_STRIP;
        setColours();
    }
    
    /**
     * Initialises the object to a random colour.
     */
    private final void setColours() {
        rColour = (float)Math.random();
        gColour = (float)Math.random();
        bColour = (float)Math.random();
    }
    
    /**
     * Generates the points of a wall with thickness adding to the outside of the object.
     * @param w The width of the wall
     * @param h The height of the wall
     * @param t The thickness of the wall
     * @return The wall's points
     */
    private final Matrix2d genWall(float w, float h, float t) {
        Matrix2d points = new Matrix2d(3, 10);
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
            points.setValue(2, j, 1f);
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
        Matrix2d points = new Matrix2d(3, 4);
        points.setValue(0, 0, -w / 2);
        points.setValue(1, 0, h / 2);
        points.setValue(0, 1, w / 2);
        points.setValue(1, 1, h / 2);
        points.setValue(0, 2, w / 2);
        points.setValue(1, 2, -h / 2);
        points.setValue(0, 3, -w / 2);
        points.setValue(1, 3, -h / 2);
        
        for (int j = 0; j < 4; j++) {
            points.setValue(2, j, 1f);
        }
        
        return points;
    }
    
    /**
     * Generates the points of a circle.
     * @param r The radius of the circle
     * @return The points of the circle
     */
    private final Matrix2d genCircle(float r) {
        Matrix2d points = new Matrix2d(3, 38);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(2, 0, 1f);
        for (int i = 0; i <= 36; i += 1) {
            points.setValue(0, i + 1, r * (float)(Math.sin(((double)i / 18) * Math.PI)));
            points.setValue(1, i + 1, r * (float)(Math.cos(((double)i / 18) * Math.PI)));
            points.setValue(2, i + 1, 1f);
        }
        return points;
    }
    
    /**
     * Transforms the points of the display objected by the given matrix.
     * @param b The transformation matrix
     */
    public void transform(Matrix2d b) {
        tempPoints = b.mult(tempPoints);
    }
    
    /**
     * Resets any transformations to the object.
     */
    public void clearTransformations() {
        tempPoints = points;
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
    
    /**
     * Returns the red component of the object's colour.
     * @return the red component
     */
    public float getColourR() {
        return rColour;
    }
    
    /**
     * Returns the green component of the object's colour.
     * @return the green component
     */
    public float getColourG() {
        return gColour;
    }
    
    /**
     * Returns the blue component of the object's colour.
     * @return the blue component
     */
    public float getColourB() {
        return bColour;
    }
    
    /**
     * Returns the points that make up the object with the transformations applied.
     * @return the points
     */
    public Matrix2d getPoints() {
        return tempPoints;
    }
    
}
