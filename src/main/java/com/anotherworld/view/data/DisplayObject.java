package com.anotherworld.view.data;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.graphics.Matrix2d;

public class DisplayObject {

    private final DisplayData displayData;
    
    private Matrix2d points;
    
    public DisplayObject(BallDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getR());
    }

    public DisplayObject(RectangleDisplayData displayData) {
        this.displayData = displayData;
        genRectangle(displayData.getWidth(), displayData.getHeight());
    }

    public DisplayObject(PlayerDisplayData displayData) {
        this.displayData = displayData;
        points = genCircle(displayData.getR());
    }
    
    public DisplayObject(WallData displayData) {
        //TODO implement this in the future
        this.displayData = null;
    }
    
    private final Matrix2d genRectangle(float w, float h) {
        Matrix2d points = new Matrix2d(3, 5);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(2, 0, 1f);
        //TODO add rest of rectangle points
        return points;
    }
    
    private final Matrix2d genCircle(float r) {
        Matrix2d points = new Matrix2d(3, 102);
        points.setValue(0, 0, 0f);
        points.setValue(1, 0, 0f);
        points.setValue(2, 0, 1f);
        for (int i = 0; i <= 360; i += 10) {
            points.setValue(0, i + 1, (float)(Math.sin(((double)i / 180) * Math.PI)));
            points.setValue(1, i + 1, (float)(Math.cos(((double)i / 180) * Math.PI)));
            points.setValue(2, i + 1, 1f);
        }
        return points;
    }
    
}
