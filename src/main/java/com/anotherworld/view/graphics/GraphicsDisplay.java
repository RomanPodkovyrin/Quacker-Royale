package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

public class GraphicsDisplay {


    private void drawObject(DisplayObject obj, Matrix2d viewMatrix) {
        Matrix2d modifier = viewMatrix;

        modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(obj.getX(), obj.getY()));

        modifier = modifier.mult(Matrix2d.H_ROTATION_2D(obj.getTheta()));

        drawMatrix(modifier.mult(obj.getPoints()));

    }
    
}
