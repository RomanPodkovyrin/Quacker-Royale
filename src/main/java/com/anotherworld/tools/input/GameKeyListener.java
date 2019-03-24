package com.anotherworld.tools.input;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameKeyListener {

    private static Logger logger = LogManager.getLogger(GameKeyListener.class);

    private KeyListener keyListener;

    private final KeyBindings keyBindings;

    /**
     * Creates a game key listener for the parsed window.
     * 
     * @param window the window to check for keyboard input
     */
    public GameKeyListener(Long window, KeyBindings keyBindings) {
        logger.info("Creating GameKeyListener for window " + window);
        ArrayList<Integer> trackedKeys = new ArrayList<>();
        this.keyBindings = keyBindings;
        trackedKeys.add(keyBindings.getUp());
        trackedKeys.add(keyBindings.getLeft());
        trackedKeys.add(keyBindings.getRight());
        trackedKeys.add(keyBindings.getDown());
        trackedKeys.add(keyBindings.getCharge());
        keyListener = new KeyListener(trackedKeys, window);
    }

    /**
     * Returns an array list of keys that are currently pressed.
     * 
     * @return the pressed keys
     */
    public ArrayList<Input> getKeyPresses() {
        ArrayList<Input> keyPresses = new ArrayList<>();
        ArrayList<Integer> downKeys = keyListener.getPressedKeys();
        for (Integer key : downKeys) {
            if (key == keyBindings.getUp()) {
                keyPresses.add(Input.UP);
            } else if (key == keyBindings.getDown()) {
                keyPresses.add(Input.DOWN);
            } else if (key == keyBindings.getLeft()) {
                keyPresses.add(Input.LEFT);
            } else if (key == keyBindings.getRight()) {
                keyPresses.add(Input.RIGHT);
            } else if (key == keyBindings.getCharge()) {
                keyPresses.add(Input.CHARGE);
            }
        }
        return keyPresses;
    }

}
