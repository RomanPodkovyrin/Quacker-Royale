package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayData;
import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

/**
 * The graphics display responsible for displaying the game state.
 * @author Jake Stewart
 *
 */
public class GameDisplay extends GraphicsDisplay {

    private Camera camera;
    
    public GameDisplay(float x, float y, float height, float width, Camera camera) {
        super(x, y, height, width);
        this.camera = camera;
    }

    @Override
    public ArrayList<DisplayObject> draw() {
        ArrayList<DisplayObject> toDraw = super.draw();
        Matrix2d viewMatrix = calculateViewMatrix(camera);
        for (int i = 0; i < toDraw.size(); i++) {
            toDraw.get(i).transform(viewMatrix);
        }
        return toDraw;
    }

    private Matrix2d calculateViewMatrix(Camera camera) {
        Matrix2d modifier;

        modifier = (Matrix2d.homTranslation2d(-1f, 1f));

        modifier = modifier.mult(Matrix2d.homScale2d(1f, -1f));

        modifier = modifier.mult(Matrix2d.homScale2d(2 / camera.getWidth(), 2 / camera.getHeight()));

        modifier = modifier.mult(Matrix2d.homTranslation2d(-camera.getX(), -camera.getY()));

        return modifier;
    }

    /**
     * Updates the game display's objects to match the current games.
     * @param obj The new objects
     */
    public void updateObjects(ArrayList<DisplayData> obj) {
        this.objects.clear();
        for (int i = 0; i < obj.size(); i++) {
            this.objects.add(new DisplayObject(obj.get(i)));
        }
    }
    
    
    
}
