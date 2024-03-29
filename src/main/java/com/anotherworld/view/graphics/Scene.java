package com.anotherworld.view.graphics;

import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Creates and manages a view state like view game or menus.
 * @author Jake Stewart
 *
 */
public class Scene {

    private static Logger logger = LogManager.getLogger(Scene.class);
    
    protected ArrayList<GraphicsDisplay> displays;
    
    private GraphicsDisplay background;
    
    private BackgroundData backgroundData;
    
    /**
     * Creates a scene that can have multiple displays with objects in.
     */
    public Scene() {
        displays = new ArrayList<>();
        this.background = new GraphicsDisplay();
        backgroundData = new BackgroundData(0, 0, 2, 2);
        background.addBackground(backgroundData);
        displays.add(background);
    }
    
    /**
     * Draws the current scene to the window.
     * @param width The width of the window in pixels
     * @param height The height of the window in pixels
     * @param programme The display programme to use
     */
    public void draw(int width, int height, Programme programme) {
        logger.debug("Drawing Scene");
        
        boolean pressed = programme.getCursorPressed();
        
        for (int i = 0; i < displays.size(); i++) {
            logger.trace("Drawing scene: " + i);
            GraphicsDisplay display = displays.get(i);
            int x = convertCoord(display.getX(), width);
            int y = convertCoord(display.getY(), height);
            int w = convertScale(display.getWidth(), width, x);
            int h = convertScale(display.getHeight(), height, y);
            programme.setViewport(x, y, w, h);
            display.draw(programme, pressed);
        }
    }
    
    /**
     * Converts a normalised device coordinate to pixel size.
     * @param value The normalised device coordinate
     * @param scale The pixel size of the window in that direction
     * @return The location in pixels
     */
    private int convertCoord(float value, int scale) {
        logger.trace("Converting coord " + value);
        return Math.min(scale, Math.max(0, (int)Math.round(value * ((float)scale))));
    }
    
    /**
     * Converts the dimension of a display from normalised device coordinates to pixels.
     * @param floatScale The size of the display in normalised device scale.
     * @param intScale The size of the window in pixels
     * @param intValue The start of the normalised device scale in pixels
     * @return The size of a display in pixels
     */
    private int convertScale(float floatScale, int intScale, int intValue) {
        logger.trace("Converting scale " + floatScale);
        return Math.min(intScale - intValue, Math.max(0, (int)Math.round((floatScale / 2f) * ((float)intScale))));
    }
    
    protected void clearDisplays() {
        displays.clear();
        this.addDisplay(background);
    }
    
    public void addDisplay(GraphicsDisplay d) {
        displays.add(d);
    }
    
    public void switchBackgroundImage(SpriteSheet spriteSheet) {
        backgroundData.setSpriteSheet(spriteSheet);
    }
    
}
