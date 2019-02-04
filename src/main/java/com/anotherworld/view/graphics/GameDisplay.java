package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

import java.util.ArrayList;

public class GameDisplay extends GraphicsDisplay {

    public GameDisplay(float x, float y, float height, float width) {
        super(x, y, height, width);
    }

    @Override
    public ArrayList<Matrix2d> draw() {
        ArrayList<Matrix2d> toDraw = super.draw();
        ArrayList<Matrix2d> result = new ArrayList<>();
        Matrix2d viewMatrix = calculateViewMatrix(0f, 0f, 160f, 90f);
        for (int i = 0; i < toDraw.size(); i++) {
            result.add(viewMatrix.mult(toDraw.get(i)));
        }
        return result;
    }

    private Matrix2d calculateViewMatrix(float camX, float camY, float camW, float camH) {
        Matrix2d modifier;

        modifier = (Matrix2d.homTranslation2d(-1f, 1f));

        modifier = modifier.mult(Matrix2d.homScale2d(1f, -1f));

        modifier = modifier.mult(Matrix2d.homScale2d(2 / camW, 2 / camH));

        modifier = modifier.mult(Matrix2d.homTranslation2d(-camX, -camY));

        return modifier;
    }

    public void updateObjects(DisplayObject[] obj) {
        this.objects.clear();
        for (int i = 0; i < obj.length; i++) {
            this.objects.add(obj[i]);
        }
    }
    
    
    
}
