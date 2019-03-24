package com.anotherworld.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * This class can be used to retrieve or edit contents of the
 * property files.
 *
 * @author Roman P
 * @author Jake Stewart
 */
public class PropertyReader {
    private String fullFilePath;
    private String filePath = "/res/config/";
    private Properties propertyFile;
    private FileInputStream fileStream;

    /**
     * Class used to initialize the  property reader for the given file.
     * @param filename the name of the Property file if the format something.properties
     * @throws IOException - If no file is find throws an exception
     */
    public PropertyReader(String filename) throws IOException {
        this.fullFilePath = System.getProperty("user.dir") + filePath + filename;
        propertyFile = new Properties();
        
        File file = new File(this.fullFilePath);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        fileStream = new FileInputStream(file);
        
        propertyFile.load(fileStream);

    }

    public String getFullFilePath() {

        return fullFilePath;
    }

    /**
     * Used to get a specified value from the properties file.
     * @param key - the key of the value
     * @return - the value
     * @throws IOException - Throws exception if the file is no longer present
     */
    public String getValue(String key) throws IOException {

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        return this.propertyFile.getProperty(key);
    }

    /**
     * Sets the given key to the given value.
     * @param key The key to be set
     * @param value the value to be set
     * @throws IOException - if file is no longer available
     */
    public void setValue(String key, String value) throws IOException {

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        this.propertyFile.setProperty(key,value);
        this.propertyFile.store(new FileWriter(this.fullFilePath),null);
    }

    /**
     * Used to delete the given value by it key.
     * @param key - the key to be deleted
     * @throws IOException - if file is no longer available
     */
    public void deleteValue(String key) throws IOException {

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        this.propertyFile.remove(key);
        this.propertyFile.store(new FileWriter(this.fullFilePath), null);

    }
    
    /**
     * Closes the properties file.
     * @throws IOException If the file couldn't be closed
     */
    public void close() throws IOException {
        fileStream.close();
    }

}
