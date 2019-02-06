package com.anotherworld.view.data;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.graphics.Matrix2d;

public class DisplayObject {

    private final DisplayData displayData;
    
    private final Matrix2d points;
    private Matrix2d tempPoints;
    
    private final int displayType;

    private float rColour;
    private float gColour;
    private float bColour;

    public DisplayObject(DisplayData displayData) {
        if (displayData instanceof BallDisplayData) {
            this.displayData = displayData;
            points = genCircle(((BallDisplayData)displayData).getRadius());
            displayType = GL_TRIANGLE_FAN;
            setColours();
        } else if (displayData instanceof RectangleDisplayData) {
            this.displayData = displayData;
            points = genRectangle(((RectangleDisplayData)displayData).getWidth(), ((RectangleDisplayData)displayData).getHeight());
            displayType = GL_TRIANGLE_FAN;
            setColours();
        } else if (displayData instanceof PlayerDisplayData) {
            this.displayData = displayData;
            points = genCircle(((PlayerDisplayData)displayData).getRadius());
            displayType = GL_TRIANGLE_FAN;
            setColours();
        } else if (displayData instanceof WallData) {
            this.displayData = displayData;
            this.points = genWall(((WallData)displayData).getWidth(), ((WallData)displayData).getHeight(), 5);
            this.displayType = GL_TRIANGLE_STRIP;
            setColours();
        } else {
            this.displayData = displayData;
            points = genCircle(20);
            displayType = GL_TRIANGLE_FAN;
            setColours();
        }
    }

    private final void setColours() {
        rColour = 1f;
        gColour = 1f;
        bColour = 1f;
    }
    
    private static final Matrix2d genWall(float w, float h, float t) {
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
    
    private static final Matrix2d genRectangle(float w, float h) {
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
    
    private static final Matrix2d genCircle(float r) {
        Matrix2d points = new Matrix2d(3, 38);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(2, 0, 1f);
        for (int i = 0; i < 37; i += 1) {
            points.setValue(0, i + 1, r * (float)(Math.sin(((double)i / 18) * Math.PI)));
            points.setValue(1, i + 1, r * -(float)(Math.cos(((double)i / 18) * Math.PI)));
            points.setValue(2, i + 1, 1f);
        }
        return points;
    }
    
    public void transform(Matrix2d b) {
        tempPoints = b.mult(tempPoints);
    }
    
    public void clearTransformations() {
        tempPoints = points;
    }
    
    public int getDisplayType() {
        return displayType;
    }
    
    public float getTheta() {
        return 0;//displayData.getAngle();
    }
    
    public float getX() {
        return 20;//displayData.getXCoordinate();
    }
    
    public float getY() {
        return 20;//displayData.getYCoordinate();
    }
    
    public float getColourR() {
        return rColour;
    }
    
    public float getColourG() {
        return gColour;
    }
    
    public float getColourB() {
        return bColour;
    }
    
    public Matrix2d getPoints() {
        return tempPoints;
    }
    
}