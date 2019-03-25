package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;

/**
 * The graphics display responsible for displaying the game state.
 * @author Jake Stewart
 *
 */
public class GameDisplay extends GraphicsDisplay {
    
    public GameDisplay(float x, float y, float height, float width, Camera camera) {
        super(x, y, height, width, camera);
    }

    /**
     * Updates the game display's objects to match the current games.
     * @param obj The new objects
     */
    public void updateObjects(ArrayList<DisplayObject> obj) {
        this.objects.clear();
        for (int i = 0; i < obj.size(); i++) {
            this.objects.add(obj.get(i));
        }
    }
    
    @Override
    public void transform(Programme programme) {
        super.transform(programme);
    }

    public void destroyObjects() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).destroy();
        }
    }
    
}
