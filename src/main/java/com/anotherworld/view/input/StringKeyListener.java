package com.anotherworld.view.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_BACKSPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.anotherworld.tools.input.KeyListener;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringKeyListener {

    private static Logger logger = LogManager.getLogger(StringKeyListener.class);

    private String currentString;
    
    private ArrayList<Integer> trackedKeys;
    
    private KeyListener keyListener;

    private boolean[] keyDown;

    /**
     * Creates a key listener that collects the key input into a string.
     * @param window the window to collect input from
     * @param currentString the initial string
     */
    public StringKeyListener(Long window, String currentString) {
        logger.info("Creating keylistener for window " + window);
        trackedKeys = new ArrayList<>();
        trackedKeys.add(GLFW_KEY_SPACE);
        trackedKeys.add(GLFW_KEY_APOSTROPHE);
        for (int i = GLFW_KEY_COMMA; i <= GLFW_KEY_RIGHT_BRACKET; i++) {
            trackedKeys.add(i);
        }
        trackedKeys.add(GLFW_KEY_BACKSPACE);
        keyListener = new KeyListener(trackedKeys, window);
        this.currentString = currentString;
        keyDown = new boolean[264];
    }

    /**
     * Returns the string entered into the text field.
     * @return the current string
     */
    public String getCurrentString() {
        ArrayList<Integer> downKeys = keyListener.getPressedKeys();
        for (Integer key : trackedKeys) {
            if (downKeys.contains(key)) {
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