package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

public class PlayerFollow2dCamera implements Camera {
    private final float height;
    private final float width;
    private ArrayList<DisplayObject> objects;

    public PlayerFollow2dCamera(float width, float height) {
        this(new ArrayList<>(), width, height);
    }
    
    public PlayerFollow2dCamera(ArrayList<DisplayObject> objects, float width, float height) {
        this.width = width;
        this.height = height;
        this.objects = objects;
    }
    
    public void updateObjects(ArrayList<DisplayObject> objects) {
        this.objects = objects;
    }
    
    @Override
    public float getX() {
        float x = 0;
        for (DisplayObject o : objects) {
            x += o.getX();
        }
        x /= objects.size();
        return x - (width / 2);
    }

    @Override
    public float getY() {
        float y = 0;
        for (DisplayObject o : objects) {
            y += o.getY();
        }
        y /= objects.size();
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
