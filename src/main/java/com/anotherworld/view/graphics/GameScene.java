package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

/**
 * Creates and manages the display of the game.
 * @author Jake Stewart
 *
 */
public class GameScene extends Scene {
    
    private Camera camera;
    
    public GameScene() {
        super();
        this.camera = new Static2dCamera(0f, 0f, 160f, 90f);
        this.displays.add(new GameDisplay(-1f, -1f, 2f, 2f, camera));
    }
    
    /**
     * Updates all currently initialised GameDisplays with new game objects.
     * @param objects The new game objects
     */
    public void updateGameObjects(ArrayList<DisplayObject> objects) {
        for (int i = 0; i < displays.size(); i++) {
            if (displays.get(i).getClass().equals(GameDisplay.class)) {
                ((GameDisplay)displays.get(i)).updateObjects(objects);
            }
        }
    }
    
}
