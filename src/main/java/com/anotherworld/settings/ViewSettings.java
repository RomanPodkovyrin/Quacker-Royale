package com.anotherworld.settings;

import com.anotherworld.tools.PropertyReader;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewSettings {
    
    private static Optional<PropertyReader> viewSettings = Optional.empty();
    
    private static Logger logger = LogManager.getLogger(ViewSettings.class);
    
    private static void loadSettings() throws IOException {
        logger.trace("Loading from file");
        viewSettings = Optional.of(new PropertyReader("display.properties"));
    }
    
    public static int getWidth() {
        return getValue("WIDTH", 1920 / 2);
    }
    
    public static int getHeight() {
        return getValue("HEIGHT", 1080 / 2);
    }
    
    public static DisplayType getDisplayType() {
        return getDisplayValue(getValue("DISPLAY_TYPE", 1));
    }
    
    private static int getValue(String fileKey, int defaultValue) {
        try {
            if (!viewSettings.isPresent()) {
                loadSettings();
            }
            return Integer.parseInt(viewSettings.get().getValue(fileKey));
        } catch (NumberFormatException | IOException e) {
            logger.warn("Couldn't load " + fileKey + " from file");
            setValue(fileKey, defaultValue);
            return defaultValue;
        }
    }
    
    public static boolean setWidth(int value) {
        return setValue("WIDTH", value);
    }
    
    public static boolean setHeight(int value) {
        return setValue("HEIGHT", value);
    }
    
    public static boolean setDisplayType(DisplayType value) {
        return setValue("DISPLAY_TYPE", getIntValue(value));
    }
    
    private static boolean setValue(String fileKey, int value) {
        try {
            if (!viewSettings.isPresent()) {
                loadSettings();
            }
            viewSettings.get().setValue(fileKey, String.valueOf(value));
        } catch (NumberFormatException | IOException e) {
            logger.warn("Couldn't save " + fileKey + " to file");
        }
        return value == -1;
    }
    
    private static int getIntValue(DisplayType value) {
        switch (value) {
            case FULLSCREEN:
                return 0;
            case WINDOWED:
                return 1;
            case BOARDERLESS_WINDOWED:
                return 2;
            default:
                return 1;
        }
    }
    
    private static DisplayType getDisplayValue(int value) {
        switch (value) {
            case 0:
                return DisplayType.FULLSCREEN;
            case 1:
                return DisplayType.WINDOWED;
            case 2:
                return DisplayType.BOARDERLESS_WINDOWED;
            default:
                return DisplayType.WINDOWED;
        }
    }
    
}
