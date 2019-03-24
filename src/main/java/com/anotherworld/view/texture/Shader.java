package com.anotherworld.view.texture;

import static org.lwjgl.opengl.GL46.glCompileShader;
import static org.lwjgl.opengl.GL46.glCreateShader;
import static org.lwjgl.opengl.GL46.glDeleteShader;
import static org.lwjgl.opengl.GL46.glShaderSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Loads and stores a shader's information.
 * @author Jake Stewart
 *
 */
public class Shader {
    
    private static Logger logger = LogManager.getLogger(Shader.class);
    
    private final String fileName;
    
    private final int shaderType;
    
    private final int shaderId;
    
    /**
     * Creates a shader from the specified file of the specified type.
     * @param fileName The location of the shader
     * @param shaderType The type of shader
     * @throws IOException If the shader couldn't be loaded
     */
    public Shader(String fileName, int shaderType) throws IOException {
        this.fileName = fileName;
        this.shaderType = shaderType;
        shaderId = this.createShader();
    }

    /**
     * Creates an opengl shader and allocates it an id.
     * @return Returns the shader's opengl id
     * @throws IOException If the file given couldn't be loaded
     */
    private int createShader() throws IOException {
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
    
    public int getId() {
        return shaderId;
    }
    
    public void destroy() {
        glDeleteShader(shaderId);
    }
    
}
