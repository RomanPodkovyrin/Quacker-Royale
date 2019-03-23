package com.anotherworld.tools.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameKeyListener {

    private static Logger logger = LogManager.getLogger(GameKeyListener.class);

    private KeyListener keyListener;

    private final int up = GLFW_KEY_UP;
    private final int left = GLFW_KEY_LEFT;
    private final int down = GLFW_KEY_DOWN;
    private final int right = GLFW_KEY_RIGHT;
    private final int charge = GLFW_KEY_SPACE;

    public GameKeyListener(Long window) {
        logger.debug("Creating GameKeyListener for window " + window);
        ArrayList<Integer> trackedKeys = new ArrayList<>();
        trackedKeys.add(up);
        trackedKeys.add(left);
        trackedKeys.add(right);
        trackedKeys.add(down);
        trackedKeys.add(charge);
        keyListener = new KeyListener(trackedKeys, window);
    }

    public ArrayList<Input> getKeyPresses() {
        ArrayList<Input> keyPresses = new ArrayList<>();
        ArrayList<Integer> downKeys = keyListener.getPressedKeys();
        for (Integer key : downKeys) {
            switch (key) {
                case up:
                    keyPresses.add(Input.UP);
                    break;
                case down:
                    keyPresses.add(Input.DOWN);
                    break;
                case left:
                    keyPresses.add(Input.LEFT);
                    break;
                case right:
                    keyPresses.add(Input.RIGHT);
                    break;
                case charge:
                    keyPresses.add(Input.CHARGE);
                    break;
                default:
                    logger.warn("Unexpected key tracked by game");
            }
        }

        return keyPresses;
    }

}
