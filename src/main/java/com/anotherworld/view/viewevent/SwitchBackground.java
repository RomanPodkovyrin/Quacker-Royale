package com.anotherworld.view.viewevent;

import com.anotherworld.view.graphics.spritesheet.SpriteSheet;

public class SwitchBackground implements ViewEvent {

    private final SpriteSheet background;
    
    public SwitchBackground(SpriteSheet spriteSheet) {
        this.background = spriteSheet;
    }
    
    public SpriteSheet getBackground() {
        return background;
    }
    
}
