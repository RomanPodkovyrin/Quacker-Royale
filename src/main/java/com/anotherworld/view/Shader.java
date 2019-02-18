package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Shader {
    
    private static Logger logger = LogManager.getLogger(Shader.class);
    
    private final String fileName;
    
    private final int shaderType;
    
    public Shader(String fileName, int shaderType) {
        this.fileName = fileName;
        this.shaderType = shaderType;
    }

    public int createShader() throws IOException {
        int shader = 0;
        logger.debug("Attempting to create shader type " + shaderType + " from " + fileName);
        shader = glCreateShader(shaderType);
        if (shader == 0) {
            logger.warn("Shader " + shaderType + " couldn't be created");
            return 0;
        }
        
        glShaderSource(shader, readToString());
        
        glCompileShader(shader);
        
        return shader;
    }
    
    private String readToString() throws IOException {
        List<String> file = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        String s = "";
        for (String line : file) {
            s = s + line + "\n";
        }
        return s;
    }
    
}
