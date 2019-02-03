package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

public class GameScene extends Scene {
    
    public GameScene() {
        super();
        this.displays.add(new GameDisplay(-1f, -1f, 2f, 2f));
    }
    
    public void updateObjects(DisplayObject[] objects) {
        for (int i = 0; i < displays.size(); i++) {
            if (displays.get(i).getClass().equals(GameDisplay.class)) {
                ((GameDisplay)displays.get(i)).updateObjects(objects);
            }
        }
    }
    
}
