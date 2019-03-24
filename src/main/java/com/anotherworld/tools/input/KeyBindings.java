package com.anotherworld.tools.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.anotherworld.tools.PropertyReader;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyBindings {
    
    private static Logger logger = LogManager.getLogger(KeyBindings.class);

    private int up;
    private int left;
    private int down;
    private int right;
    private int charge;
    
    public KeyBindings() {
        logger.trace("Creating key bindings");
        PropertyReader keySettings;
        try {
            logger.trace("Loading from file");
            keySettings = new PropertyReader("keys.properties");
            try {
                up = Integer.parseInt(keySettings.getValue("UP"));
                left = Integer.parseInt(keySettings.getValue("LEFT"));
                down = Integer.parseInt(keySettings.getValue("DOWN"));
                right = Integer.parseInt(keySettings.getValue("RIGHT"));
                charge = Integer.parseInt(keySettings.getValue("CHARGE"));
            } catch (IOException e) {
                logger.warn("Key bindings file couldn't be loaded");
                up = GLFW_KEY_UP;
                left = GLFW_KEY_LEFT;
                down = GLFW_KEY_DOWN;
                right = GLFW_KEY_RIGHT;
                charge = GLFW_KEY_SPACE;
            }
            keySettings.close();
        } catch (IOException e) {
            logger.warn("Key bindings file couldn't be loaded");
            up = GLFW_KEY_UP;
            left = GLFW_KEY_LEFT;
            down = GLFW_KEY_DOWN;
            right = GLFW_KEY_RIGHT;
            charge = GLFW_KEY_SPACE;
        }
    }
    
    public int getUp() {
        return up;
    }
    
    public int getLeft() {
        return left;
    }
    
    public int getDown() {
        return down;
    }
    
    public int getRight() {
        return right;
    }
    
    public int getCharge() {
        return charge;
    }


}
