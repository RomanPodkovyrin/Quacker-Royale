package com.anotherworld.view.input;

import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {

    private Long window;
    
    public KeyListener(Long window) {
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
