package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

import java.util.ArrayList;

public class GraphicsDisplay {
    
    private final float x;
    private final float y;
    private final float height;
    private final float width;
    
    protected ArrayList<DisplayObject> objects;
    
    public GraphicsDisplay(float x, float y, float height, float width) {
        assert(-1f <= x && x <= 1f);
        assert(-1f <= y && y <= 1f);
        assert(0f <= height && height <= 2f);
        assert(0f <= width && width <= 2f);
        assert(-1f <= x + width && x + width <= 1f);
        assert(-1f <= y + height && y + height <= 1f);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        objects = new ArrayList<>();
    }

    public ArrayList<Matrix2d> draw() {
        ArrayList<Matrix2d> toDraw = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            toDraw.add(drawObject(objects.get(i)));
        }
        return toDraw;
    }
    
    private Matrix2d drawObject(DisplayObject obj) {
        Matrix2d modifier = Matrix2d.homTranslation2d(obj.getX(), obj.getY());

        modifier = modifier.mult(Matrix2d.homRotation2d(obj.getTheta()));

        return modifier.mult(obj.getPoints());

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
