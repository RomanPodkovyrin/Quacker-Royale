package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates a camera that follows objects that return true from shouldCameraFollow.
 * @author Jake Stewart
 *
 */
public class PlayerFollow2dCamera implements Camera {
    
    private static Logger logger = LogManager.getLogger();
    
    private final float height;
    private final float width;
    
    private float currentX;
    private float currentY;
    
    private ArrayList<DisplayObject> objects;

    public PlayerFollow2dCamera(float x, float y, float width, float height) {
        this(new ArrayList<>(), x, y, width, height);
    }
    
    /**
     * Creates a camera that alters its position based on the given objects.
     * @param objects The objects to centre
     * @param width The width of the camera
     * @param height The height of the camera
     */
    public PlayerFollow2dCamera(ArrayList<DisplayObject> objects, float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.objects = objects;
        this.currentX = x;
        this.currentY = y;
    }
    
    /**
     * Changes the objects for the camera to track.
     * @param objects The new objects to track
     */
    public void updateObjects(ArrayList<DisplayObject> objects) {
        this.objects = objects;
    }
    
    @Override
    public float getX() {
        float x = 0;
        int i = 0;
        for (DisplayObject o : objects) {
            if (o.shouldCameraFollow()) {
                x += o.getX();
                i++;
            }
        }
        x /= Math.max(i, 1);
        if (x > currentX) {
            currentX += Math.min(Math.abs(x - currentX), 0.2f);
        } else {
            currentX -= Math.min(Math.abs(x - currentX), 0.2f);
        }
        logger.debug("Camera x is " + currentX);
        return currentX;
    }

    @Override
    public float getY() {
        float y = 0;
        int i = 0;
        for (DisplayObject o : objects) {
            if (o.shouldCameraFollow()) {
                y += o.getY();
                i++;
            }
        }
        y /= Math.max(i, 1);
        if (y > currentY) {
            currentY += Math.min(Math.abs(y - currentY), 0.2f);
        } else {
            currentY -= Math.min(Math.abs(y - currentY), 0.2f);
        }
        logger.debug("Camera y is " + currentY);
        return currentY;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
    
    @Override
    public float getDepth() {
        return 1;
    }

    @Override
    public float getZ() {
        return 0;
    }

}
