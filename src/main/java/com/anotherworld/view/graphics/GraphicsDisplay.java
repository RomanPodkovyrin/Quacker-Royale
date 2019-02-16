package com.anotherworld.view.graphics;

import static org.lwjgl.opengl.GL46.*;

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
    
    private Camera camera;
    
    protected ArrayList<DisplayObject> objects;

    /**
     * Creates a new Graphics display (Uses normalised device coordinates).
     * @param x The x position
     * @param y The y position
     * @param height The display height
     * @param width The display width
     * @throws IncoherentGraphicsDisplay If the display would go outside of the window
     */
    public GraphicsDisplay(float x, float y, float height, float width, Camera camera) {
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
        this.camera = camera;
        objects = new ArrayList<>();
    }

    /**
     * Returns an array list of matrices containing the objects to be drawn.
     * @return The list of matrices
     */
    public void draw() {
        glPushMatrix();
        Camera.transform(camera);
        for (int i = 0; i < objects.size(); i++) {
            glPushMatrix();
            objects.get(i).transform();
            objects.get(i).draw();
            glPopMatrix();
        }
        glPopMatrix();
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

    public void destroyObjects() {
        for (DisplayObject d : objects) {
            d.destroyObject();
        }
    }
    
}
