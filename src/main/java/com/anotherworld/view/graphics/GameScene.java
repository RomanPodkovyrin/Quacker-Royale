package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayData;

/**
 * Creates and manages the display of the game.
 * @author jake
 *
 */
public class GameScene extends Scene {
    
    private Camera camera; //TODO implement this
    
    public GameScene() {
        super();
        this.displays.add(new GameDisplay(-1f, -1f, 2f, 2f, camera));
    }
    
    /**
     * Updates all currently initialised GameDisplays with new game objects.
     * @param objects The new game objects
     */
    public void updateGameObjects(DisplayData[] objects) {
        for (int i = 0; i < displays.size(); i++) {
            if (displays.get(i).getClass().equals(GameDisplay.class)) {
                ((GameDisplay)displays.get(i)).updateObjects(objects);
            }
        }
    }
    
}
