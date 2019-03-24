package com.anotherworld.tools.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

public class KeyBindings {

    private final int up;
    private final int left;
    private final int down;
    private final int right;
    private final int charge;
    
    public KeyBindings() {
        up = GLFW_KEY_UP;
        left = GLFW_KEY_LEFT;
        down = GLFW_KEY_DOWN;
        right = GLFW_KEY_RIGHT;
        charge = GLFW_KEY_SPACE;
    }
    
    public Integer getUp() {
        return up;
    }
    
    public Integer getLeft() {
        return left;
    }
    
    public Integer getDown() {
        return down;
    }
    
    public Integer getRight() {
        return right;
    }
    
    public Integer getCharge() {
        return charge;
    }


}
