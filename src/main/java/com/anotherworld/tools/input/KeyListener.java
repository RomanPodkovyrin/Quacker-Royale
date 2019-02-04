package com.anotherworld.tools.input;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {

    private Long window;
    
    public KeyListener(Long window) {
        this.window = window;
    }

    public ArrayList<Input> getKeyPresses() {
        ArrayList<Input> keyPresses = new ArrayList<>();

        if (glfwGetKey(window, GLFW_KEY_UP)    == 1) keyPresses.add(Input.UP);
        if (glfwGetKey(window, GLFW_KEY_DOWN)  == 1) keyPresses.add(Input.DOWN);
        if (glfwGetKey(window, GLFW_KEY_LEFT)  == 1) keyPresses.add(Input.LEFT);
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == 1) keyPresses.add(Input.RIGHT);

        return keyPresses;
    }

    /*
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
    */
}
