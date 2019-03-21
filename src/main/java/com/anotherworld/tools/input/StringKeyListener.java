package com.anotherworld.tools.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringKeyListener {

    private static Logger logger = LogManager.getLogger(GameKeyListener.class);

    private final Long window;

    private String currentString;

    private ArrayList<Integer> trackedKeys;

    private boolean[] keyDown;

    public StringKeyListener(Long window, String currentString) {
        logger.info("Creating keylistener for window " + window);
        this.window = window;
        trackedKeys = new ArrayList<>();
        trackedKeys.add(GLFW_KEY_SPACE);
        trackedKeys.add(GLFW_KEY_APOSTROPHE);
        for (int i = GLFW_KEY_COMMA; i <= GLFW_KEY_RIGHT_BRACKET; i++) {
            trackedKeys.add(i);
        }
        trackedKeys.add(GLFW_KEY_BACKSPACE);
        this.currentString = currentString;
        keyDown = new boolean[264];
    }

    public String getCurrentString() {
        for (Integer key : trackedKeys) {
            if (glfwGetKey(window, key) == 1) {
                if (!keyDown[key]) {
                    if (key == GLFW_KEY_BACKSPACE) {
                        currentString = currentString.substring(0, Math.max(0, currentString.length() - 1));
                    } else {
                        currentString = currentString + String.valueOf((char) ((int) key));
                    }
                    keyDown[key] = true;
                }
            } else {
                keyDown[key] = false;
            }
        }
        return currentString;
    }

}