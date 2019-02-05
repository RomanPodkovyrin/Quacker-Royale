package com.anotherworld.view.input;

import static org.lwjgl.glfw.GLFW.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyListener {

    private static Logger logger = LogManager.getLogger(KeyListener.class);
    
    private final Long window;
    
    public KeyListener(Long window) {
        logger.info("Creating keylistener for window " + window);
        this.window = window;
    }
    
    public boolean isUpPressed() {
        return glfwGetKey(window, GLFW_KEY_UP) == 1;
    }
    
    public boolean isDownPressed() {
        return glfwGetKey(window, GLFW_KEY_DOWN) == 1;
    }
    
    public boolean isLeftPressed() {
        return glfwGetKey(window, GLFW_KEY_LEFT) == 1;
    }
    
    public boolean isRightPressed() {
        return glfwGetKey(window, GLFW_KEY_RIGHT) == 1;
    }
    
}
