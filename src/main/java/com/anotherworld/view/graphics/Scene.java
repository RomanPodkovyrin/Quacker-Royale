package com.anotherworld.view.graphics;

import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates and manages a view state like view game or main menu.
 * @author Jake Stewart
 *
 */
public class Scene {

    private static Logger logger = LogManager.getLogger(Scene.class);
    
    protected ArrayList<GraphicsDisplay> displays;
    
    public Scene() {
        displays = new ArrayList<>();
    }
    
    /**
     * Draws the current scene to the window.
     * @param width The width of the window in pixels
     * @param height The height of the window in pixels
     */
    public void draw(int width, int height) {
        for (int i = 0; i < displays.size(); i++) {
            GraphicsDisplay display = displays.get(i);
            int x = convertCoord(display.getX(), width);
            int y = convertCoord(display.getY(), height);
            int w = convertScale(display.getWidth(), width, x);
            int h = convertScale(display.getHeight(), height, y);
            glViewport(x, y, w, h);
            ArrayList<Matrix2d> toDraw = display.draw();
            for (int j = 0; j < toDraw.size(); j++) {
                drawMatrix(toDraw.get(j));
            }
            
        }
    }

    /**
     * Draws a matrix object to the screen.
     * @param a The matrix to draw
     */
    private void drawMatrix(Matrix2d a) {
        logger.trace("Drawing matrix " + a.toString());
        glBegin(GL_POLYGON);
        for (int j = 0; j < a.getN(); j++) {
            glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
        }
        glEnd();
        logger.trace("Finished drawing matrix");
    }
    
    /**
     * Converts a normalised device coordinate to pixel size.
     * @param value The normalised device coordinate
     * @param scale The pixel size of the window in that direction
     * @return The location in pixels
     */
    private int convertCoord(float value, int scale) {
        return Math.min(scale, Math.max(0, (int)Math.round(value * ((float)scale))));
    }
    
    /**
     * Converts the dimension of a display from normalised device coordinates to pixels.
     * @param floatScale The size of the display in normalised device scale.
     * @param intScale The size of the window in pixels
     * @param intValue The start of the normalised device scale in pixels
     * @return The size of a display in pixels
     */
    private int convertScale(float floatScale, int intScale, int intValue) {
        return Math.min(intScale - intValue, Math.max(0, (int)Math.round((floatScale / 2f) * ((float)intScale))));
    }
    
}
