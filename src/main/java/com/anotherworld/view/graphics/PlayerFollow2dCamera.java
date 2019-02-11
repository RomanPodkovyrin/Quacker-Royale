package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

/**
 * Creates a camera that follows objects that return true from shouldCameraFollow.
 * @author Jake Stewart
 *
 */
public class PlayerFollow2dCamera implements Camera {
    private final float height;
    private final float width;
    private ArrayList<DisplayObject> objects;

    public PlayerFollow2dCamera(float width, float height) {
        this(new ArrayList<>(), width, height);
    }
    
    /**
     * Creates a camera that alters its position based on the given objects.
     * @param objects The objects to centre
     * @param width The width of the camera
     * @param height The height of the camera
     */
    public PlayerFollow2dCamera(ArrayList<DisplayObject> objects, float width, float height) {
        this.width = width;
        this.height = height;
        this.objects = objects;
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
        x /= i;
        return x - (width / 2);
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
        y /= i;
        return y - (height / 2);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

}
