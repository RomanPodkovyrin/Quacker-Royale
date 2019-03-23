package com.anotherworld.tools.input;

import static org.lwjgl.glfw.GLFW.glfwGetKey;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyListener {

    private static Logger logger = LogManager.getLogger(KeyListener.class);

    private final Long window;

    private ArrayList<Integer> trackedKeys;
    
    public KeyListener(ArrayList<Integer> trackedKeys, Long window) {
        logger.debug("Creating keylistener for window " + window);
        this.trackedKeys = trackedKeys;
        this.window = window;
    }

    public ArrayList<Integer> getPressedKeys() {
        ArrayList<Integer> pressedKeys = new ArrayList<>();
        for (Integer key : trackedKeys) {
            if (glfwGetKey(window, key) == 1) {
                pressedKeys.add(key);
            }
        }
        return pressedKeys;
    }

}