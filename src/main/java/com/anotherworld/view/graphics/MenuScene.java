package com.anotherworld.view.graphics;

public class MenuScene extends Scene {
    
    private GraphicsDisplay background;
    
    public MenuScene() {
        super();
        this.background =  new GraphicsDisplay();
    }

    public void changeMenuDisplay(GraphicsDisplay display) {
        this.clearDisplays();
        this.addDisplay(background);
        this.addDisplay(display);
    }

}
