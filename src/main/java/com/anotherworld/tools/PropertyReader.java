package com.anotherworld.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

/**
 * This class can be used to retrieve or edit contents of the
 * property files
 *
 * @author Roman P
 */
public class PropertyReader {
    private String fullFilePath;
    private String filePath = "/res/config/";
    private Properties propertyFile;

    /**
     * @param filename the name of the Property file if the format something.properties
     * @throws Exception
     */
    public PropertyReader(String filename) throws Exception{
        this.fullFilePath = System.getProperty("user.dir") + filePath + filename;
        propertyFile = new Properties();

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));

    }

    public String getFullFilePath() {

        return fullFilePath;
    }

    public String getValue(String key) throws Exception{

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        return this.propertyFile.getProperty(key);
    }

    public void setValue(String key , String value) throws Exception{

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        this.propertyFile.setProperty(key,value);
        this.propertyFile.store(new FileWriter(this.fullFilePath),null);
    }

    public void deleteValue(String key) throws Exception{

        propertyFile.load(new FileInputStream(new File(this.fullFilePath)));
        this.propertyFile.remove(key);
        this.propertyFile.store(new FileWriter(this.fullFilePath), null);

    }

}
