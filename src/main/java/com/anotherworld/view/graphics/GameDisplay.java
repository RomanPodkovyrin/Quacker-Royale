package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

public class GameDisplay extends GraphicsDisplay {

    private Matrix2d calculateViewMatrix(float camX, float camY, float camW, float camH, float xRes, float yRes) {
        Matrix2d modifier;

        modifier = (Matrix2d.H_TRANSLATION_2D(-1f, 1f));

        modifier = modifier.mult(Matrix2d.H_SCALE_2D(1f, -1f));

        modifier = modifier.mult(Matrix2d.H_SCALE_2D(2 / camW, 2 / camH));

        modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(-camX, -camY));

        return modifier;
    }
    
}
