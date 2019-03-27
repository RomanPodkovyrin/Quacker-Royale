package com.anotherworld.view.graphics;

public class MenuScene extends Scene {
    
    
    public MenuScene() {
        super();
    }

    /**
     * Changes the menu display to the passed one.
     * @param display The new display to render
     */
    public void changeMenuDisplay(GraphicsDisplay display) {
        super.clearDisplays();
        this.addDisplay(display);
    }

}
