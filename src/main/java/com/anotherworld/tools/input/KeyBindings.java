package com.anotherworld.tools.input;

import com.anotherworld.settings.KeySettings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyBindings {
    
    private static Logger logger = LogManager.getLogger(KeyBindings.class);

    private final int up;
    private final int left;
    private final int down;
    private final int right;
    private final int charge;
    private final int mute;
    
    /**
     * Loads the keybindings from settings and stores them so they can be used during the game.
     */
    public KeyBindings() {
        logger.trace("Creating key bindings");
        up = KeySettings.getUp();
        left = KeySettings.getLeft();
        right = KeySettings.getRight();
        down = KeySettings.getDown();
        charge = KeySettings.getCharge();
        mute = KeySettings.getMute();
    }
    
    public int getUp() {
        return up;
    }
    
    public int getLeft() {
        return left;
    }
    
    public int getDown() {
        return down;
    }
    
    public int getRight() {
        return right;
    }
    
    public int getCharge() {
        return charge;
    }
    
    public int getMute() {
        return mute;
    }


}
