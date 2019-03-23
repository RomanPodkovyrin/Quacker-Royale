package com.anotherworld.view.viewevent;

import com.anotherworld.view.graphics.Scene;

public class SwitchScene implements ViewEvent {
    
    private final Scene scene;

    public SwitchScene(Scene scene) {
        this.scene = scene;
    }
    
    public Scene getScene() {
        return scene;
    }
    
}
