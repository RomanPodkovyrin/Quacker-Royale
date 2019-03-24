package com.anotherworld.tools.input;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class GameKeyListener {

    private static Logger logger = LogManager.getLogger(GameKeyListener.class);

    private KeyListener keyListener;

    private final int up = GLFW_KEY_UP;
    private final int left = GLFW_KEY_LEFT;
    private final int down = GLFW_KEY_DOWN;
    private final int right = GLFW_KEY_RIGHT;
    private final int charge = GLFW_KEY_SPACE;
    private final int mute = GLFW_KEY_M;

    /**
     * Creates a game key listener for the parsed window.
     * @param window the window to check for keyboard input
     */
    public GameKeyListener(Long window) {
        logger.debug("Creating GameKeyListener for window " + window);
        ArrayList<Integer> trackedKeys = new ArrayList<>();
        trackedKeys.add(up);
        trackedKeys.add(left);
        trackedKeys.add(right);
        trackedKeys.add(down);
        trackedKeys.add(charge);
        trackedKeys.add(mute);
        keyListener = new KeyListener(trackedKeys, window);
    }

    /**
     * Returns an array list of keys that are currently pressed.
     * @return the pressed keys
     */
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
                case mute:
                    keyPresses.add(Input.MUTE);
                    break;
                default:
                    logger.warn("Unexpected key tracked by game");
            }
        }

        return keyPresses;
    }

}
