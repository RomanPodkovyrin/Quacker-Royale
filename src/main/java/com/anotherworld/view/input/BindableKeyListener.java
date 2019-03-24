package com.anotherworld.view.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_APOSTROPHE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_COMMA;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_END;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_BRACKET;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.anotherworld.tools.input.KeyListener;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BindableKeyListener {

    private static Logger logger = LogManager.getLogger(BindableKeyListener.class);
    
    private ArrayList<Integer> trackedKeys;
    
    private KeyListener keyListener;
    
    public BindableKeyListener(Long window) {
        logger.info("Creating keylistener for window " + window);
        trackedKeys = new ArrayList<>();
        trackedKeys.add(GLFW_KEY_SPACE);
        trackedKeys.add(GLFW_KEY_APOSTROPHE);
        for (int i = GLFW_KEY_COMMA; i <= GLFW_KEY_RIGHT_BRACKET; i++) {
            trackedKeys.add(i);
        }
        for (int i = GLFW_KEY_ESCAPE; i <= GLFW_KEY_END; i++) {
            trackedKeys.add(i);
        }
        keyListener = new KeyListener(trackedKeys, window);
    }

    /**
     * Returns the string entered into the text field.
     * @return the current string
     */
    public ArrayList<Integer> getBindableKey() {
        return keyListener.getPressedKeys();
    }

}