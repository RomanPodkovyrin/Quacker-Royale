package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;

/**
 * The graphics display responsible for displaying the game state.
 * @author Jake Stewart
 *
 */
public class GameDisplay extends GraphicsDisplay {
    
    /**
     * Creates a display for the game to add objects to.
     * @param x the display x
     * @param y the display y
     * @param height the display height
     * @param width the display width
     * @param camera the display camera
     */
    public GameDisplay(float x, float y, float height, float width, Camera camera) {
        super(x, y, height, width, camera);
        Long startTime = System.currentTimeMillis();
        ButtonData loadingText = new ButtonData(() -> {
            return "Loading" + repeatDots((int)((System.currentTimeMillis() - startTime) % 599) / 150);
        }, true);
        loadingText.setTextColour(1, 1, 1);
        loadingText.setPosition(20, 85);
        loadingText.setHeight(10);
        this.addButton(loadingText);
    }
    
    private String repeatDots(int n) {
        String r = "";
        for (int i = 0; i < 3; i++) {
            r = r + ((i < n) ? "." : " ");
        }
        return r;
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

    /**
     * Destroys the objects used to display the game.
     */
    public void destroyObjects() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).destroy();
        }
    }
    
}
