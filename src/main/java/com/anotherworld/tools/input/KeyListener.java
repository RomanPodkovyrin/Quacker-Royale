package com.anotherworld.tools.input;

import java.util.ArrayList;
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

    public ArrayList<Input> getKeyPresses() {
        ArrayList<Input> keyPresses = new ArrayList<>();

        if (glfwGetKey(window, GLFW_KEY_UP)    == 1) keyPresses.add(Input.UP);
        if (glfwGetKey(window, GLFW_KEY_DOWN)  == 1) keyPresses.add(Input.DOWN);
        if (glfwGetKey(window, GLFW_KEY_LEFT)  == 1) keyPresses.add(Input.LEFT);
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == 1) keyPresses.add(Input.RIGHT);
        if (glfwGetKey(window, GLFW_KEY_SPACE) == 1) keyPresses.add(Input.CHARGE);

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
