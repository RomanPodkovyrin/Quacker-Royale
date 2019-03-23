package com.anotherworld.view.viewevent;

import com.anotherworld.view.graphics.GraphicsDisplay;

public class MenuSwitch implements ViewEvent {

    private GraphicsDisplay display;
    
    public MenuSwitch(GraphicsDisplay display) {
        this.display = display;
    }
    
    public GraphicsDisplay getDisplay() {
        return display;
    }
    
}
