package com.anotherworld.view.graphics;

public class MenuScene extends Scene {
    
    private GraphicsDisplay background;
    
    public MenuScene() {
        super();
        this.background =  new GraphicsDisplay();
    }

    /**
     * Changes the menu display to the passed one.
     * @param display The new display to render
     */
    public void changeMenuDisplay(GraphicsDisplay display) {
        this.clearDisplays();
        this.addDisplay(background);
        this.addDisplay(display);
    }

}
