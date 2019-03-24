package com.anotherworld.settings;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.anotherworld.tools.PropertyReader;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Loads the bound keys and writes them.
 * @author Jake Stewart
 *
 */
public class KeySettings {
    
    private static Optional<PropertyReader> keySettings = Optional.empty();
    
    private static Logger logger = LogManager.getLogger(KeySettings.class);
    
    private static void loadSettings() throws IOException {
        logger.trace("Loading from file");
        keySettings = Optional.of(new PropertyReader("keys.properties"));
    }
    
    public static int getUp() {
        return getKey("UP", GLFW_KEY_UP);
    }
    
    public static int getLeft() {
        return getKey("LEFT", GLFW_KEY_LEFT);
    }
    
    public static int getRight() {
        return getKey("RIGHT", GLFW_KEY_RIGHT);
    }
    
    public static int getDown() {
        return getKey("DOWN", GLFW_KEY_DOWN);
    }
    
    public static int getCharge() {
        return getKey("CHARGE", GLFW_KEY_SPACE);
    }
    
    private static int getKey(String fileKey, int defaultValue) {
        try {
            if (!keySettings.isPresent()) {
                loadSettings();
            }
            return Integer.parseInt(keySettings.get().getValue(fileKey));
        } catch (NumberFormatException | IOException e) {
            logger.warn("Couldn't load " + fileKey + " from file");
            setKey(fileKey, defaultValue);
            return defaultValue;
        }
    }
    
    public static boolean setUp(int value) {
        return setKey("UP", value);
    }
    
    public static boolean setLeft(int value) {
        return setKey("LEFT", value);
    }
    
    public static boolean setRight(int value) {
        return setKey("RIGHT", value);
    }
    
    public static boolean setDown(int value) {
        return setKey("DOWN", value);
    }
    
    public static boolean setCharge(int value) {
        return setKey("CHARGE", value);
    }
    
    private static boolean keyInUse(int value) {
        if (value == GLFW_KEY_ESCAPE) {
            return true;
        }
        return getUp() == value || getLeft() == value
                || getRight() == value || getDown() == value
                || getCharge() == value;
    }
    
    private static boolean setKey(String fileKey, int value) {
        try {
            if (!keySettings.isPresent()) {
                loadSettings();
            }
            keySettings.get().setValue(fileKey, String.valueOf(-1));
            if (keyInUse(value)) {
                value = -1;
            }
            keySettings.get().setValue(fileKey, String.valueOf(value));
        } catch (NumberFormatException | IOException e) {
            logger.warn("Couldn't save " + fileKey + " to file");
        }
        return value == -1;
    }

}
