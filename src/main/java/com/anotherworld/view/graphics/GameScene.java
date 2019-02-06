package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

/**
 * Creates and manages the display of the game.
 * @author jake
 *
 */
public class GameScene extends Scene {
    
    public GameScene(boolean lockMouse) {
        super(lockMouse);
        this.displays.add(new GameDisplay(-1f, -1f, 2f, 2f));
    }
    
    /**
     * Updates all currently initialised GameDisplays with new game objects.
     * @param objects The new game objects
     */
    public void updateGameObjects(DisplayObject[] objects) {
        for (int i = 0; i < displays.size(); i++) {
            if (displays.get(i).getClass().equals(GameDisplay.class)) {
                ((GameDisplay)displays.get(i)).updateObjects(objects);
            }
        }
    }
    
}
