package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.*; 

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The core rendering engine compatible with most systems.
 * @author Jake Stewart
 *
 */
public class CoreProgramme extends Programme {

    private static Logger logger = LogManager.getLogger();
    
    private int programmeId;
    private Optional<Integer> uniformId;
    private TextureMap textureMap;
    private Shader vertexShader;
    private Shader fragShader;
    
    /**
     * Creates the core programme that should be compatible with most systems.
     * @throws ProgrammeUnavailableException If the programme isn't compatible with the system or wasn't found
     */
    public CoreProgramme() throws ProgrammeUnavailableException {
        init();
        uniformId = Optional.empty();
        try {
            textureMap = new TextureMap("res/images/alien.png");
        } catch (IOException ex) {
            logger.catching(ex);
            throw new ProgrammeUnavailableException("Couldn't load texture map");
        }
    }
    
    private void init() throws ProgrammeUnavailableException {
        logger.info("Creating shaders");
        
        try {

            this.vertexShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/Texture.vs", GL_VERTEX_SHADER);
            this.fragShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/Texture.frag", GL_FRAGMENT_SHADER);
            
        } catch (IOException e) {
            logger.warn("Couldn't load shader");
            throw new ProgrammeUnavailableException("Shaders couldn't be loaded");
        }
        
        this.programmeId = glCreateProgram();
        
        if (vertexShader.getId() == 0 || fragShader.getId() == 0 || programmeId == 0) {
            logger.warn("One of the shaders wasn't initialised");
            throw new ProgrammeUnavailableException("Shaders couldn't be initialised");
        }
        
        if (glGetShaderi(vertexShader.getId(), GL_COMPILE_STATUS) == 0) {
            logger.warn("Vertex shader could't be complied");
            logger.warn("Vertex shader log: " + glGetShaderInfoLog(vertexShader.getId()));
            throw new ProgrammeUnavailableException("Vertex shader wasn't complied");
        }
        
        if (glGetShaderi(fragShader.getId(), GL_COMPILE_STATUS) == 0) {
            logger.warn("Fragment shader could't be complied");
            logger.warn("Fragment shader log: " + glGetShaderInfoLog(fragShader.getId()));
            throw new ProgrammeUnavailableException("Fragment shader wasn't complied");
        }
        
        glAttachShader(programmeId, vertexShader.getId());
        glAttachShader(programmeId, fragShader.getId());
        
        glBindAttribLocation(programmeId, 0, "position");
        glBindAttribLocation(programmeId, 1, "colour");
        glBindAttribLocation(programmeId, 2, "texture");
        
        glLinkProgram(programmeId);
        
        logger.debug("Fragment shader id " + fragShader.getId());
        logger.debug("Vertex shader id " + vertexShader.getId());
        logger.debug("Programme shader id " + programmeId);
        
        logger.info("Programme info: " + glGetProgramInfoLog(programmeId));
        
        logger.debug("Loading texture map");
    }
    
    private void setUpUniform() {
        uniformId = Optional.of(glGetUniformLocation(programmeId, "tex"));
        glProgramUniform1i(programmeId, uniformId.get(), 0);
    }
    
    @Override
    public void use() {
        glEnable(GL_TEXTURE_2D);
        glUseProgram(programmeId);
        glBindTexture(GL_TEXTURE_2D, textureMap.getId());
        setUpUniform();
    }

    @Override
    public void close() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glUseProgram(0);
        glDisable(GL_TEXTURE_2D);
    }
    
    @Override
    public void destroy() {
        this.close();
        vertexShader.destroy();
        fragShader.destroy();
        glDeleteProgram(programmeId);
    }

    @Override
    public boolean supportsTextures() {
        return false;
    }

    @Override
    public boolean supportsVertArrayObj() {
        return true;
    }

    @Override
    public void loadIdentity() {
        glLoadIdentity();
    }

    @Override
    public void pushMatrix() {
        glPushMatrix();
    }

    @Override
    public void translatef(float x, float y, float z) {
        glTranslatef(x, y, z);
    }

    @Override
    public void scalef(float x, float y, float z) {
        glScalef(x, y, z);
    }

    @Override
    public void rotatef(float angle, float x, float y, float z) {
        glRotatef(angle, x, y, z);
    }

    @Override
    public void popMatrix() {
        glPopMatrix();
    }

}
