package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the display objects for part of a scene.
 * @author Jake Stewart
 *
 */
public class GraphicsDisplay {
    
    private static Logger logger = LogManager.getLogger();
    
    private final float x;
    private final float y;
    private final float height;
    private final float width;
    
    protected ArrayList<DisplayObject> objects;
    
    /**
     * Creates a new Graphics display (Uses normalised device coordinates).
     * @param x The x position
     * @param y The y position
     * @param height The display height
     * @param width The display width
     * @throws IncoherentGraphicsDisplay If the display would go outside of the window
     */
    public GraphicsDisplay(float x, float y, float height, float width) {
        if (!(-1f <= x && x <= 1f
                && -1f <= y && y <= 1f
                && 0f <= height && height <= 2f
                && 0f <= width && width <= 2f
                && -1f <= x + width && x + width <= 1f
                && -1f <= y + height && y + height <= 1f)) {
            logger.catching(new IncoherentGraphicsDisplay("Display doesn't fit on screen"));
        }
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        objects = new ArrayList<>();
    }

    /**
     * Returns an array list of matrices containing the objects to be drawn.
     * @return The list of matrices
     */
    public ArrayList<DisplayObject> draw() {
        for (int i = 0; i < objects.size(); i++) {
            //objects.get(i).clearTransformations();
            transformObject(objects.get(i));
        }
        return objects;
    }
    
    private void transformObject(DisplayObject obj) {
        Matrix2d modifier = Matrix2d.homTranslation2d(obj.getX(), obj.getY());

        modifier = modifier.mult(Matrix2d.homRotation2d(obj.getTheta()));

        //obj.transform(modifier);

    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWidth() {
        return width;
    }
    
}
