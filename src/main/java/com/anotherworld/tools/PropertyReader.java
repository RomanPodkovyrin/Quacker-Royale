package com.anotherworld.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

public class PropertyReader {
    private String fullFilePath;
    private String filePath = "/res/config/";
    private Properties propertyFile;

    public PropertyReader(String fullFilePath) throws Exception{
        this.fullFilePath = System.getProperty("user.dir") + filePath + fullFilePath;
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
