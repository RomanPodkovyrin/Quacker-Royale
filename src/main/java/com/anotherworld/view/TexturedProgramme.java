package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.*;

import com.anotherworld.view.data.DisplayObject;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.BufferUtils;

/**
 * The core rendering engine compatible with most systems.
 * @author Jake Stewart
 *
 */
public class TexturedProgramme extends Programme {

    private static Logger logger = LogManager.getLogger();
    
    private int programmeId;
    private TextureMap textureMap;
    private Shader vertexShader;
    private Shader fragShader;
    
    /**
     * Creates the core programme that should be compatible with most systems.
     * @throws ProgrammeUnavailableException If the programme isn't compatible with the system or wasn't found
     */
    public TexturedProgramme() throws ProgrammeUnavailableException {
        init();
        try {
            textureMap = new TextureMap("res/images/NeutralBall/NeutralBall0.png");
        } catch (IOException ex) {
            logger.catching(ex);
            throw new ProgrammeUnavailableException("Couldn't load texture map");
        }
    }
    
    private void init() throws ProgrammeUnavailableException {
        logger.info("Creating shaders");
        
        try {

            this.vertexShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/texture/vertex.glsl", GL_VERTEX_SHADER);
            this.fragShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/texture/frag.glsl", GL_FRAGMENT_SHADER);
            
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
    
    @Override
    public void initialiseDisplayObject(DisplayObject displayObject) {
        
        displayObject.setVertexArrayObjectID(glGenVertexArrays());
        glBindVertexArray(displayObject.getVertexArrayObjectID());
        
        displayObject.setVerticesID(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.getVerticesID());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getFloatBuffer(), GL_STATIC_DRAW);
        
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        
        glEnableVertexAttribArray(0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        displayObject.setColourID(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.getColourID());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getColourBuffer(), GL_STATIC_DRAW);
        
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);
        
        glEnableVertexAttribArray(1);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        displayObject.setTextureID(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.getTextureID());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getTextureBuffer(), GL_STATIC_DRAW);
        
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);
        
        glEnableVertexAttribArray(2);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        
        glBindVertexArray(0);
        
        displayObject.setEdgesID(glGenBuffers());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, displayObject.getEdgesID());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, displayObject.getIndexBuffer(), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    
    @Override
    public void use() {
        glUseProgram(programmeId);
        glBindTexture(GL_TEXTURE_2D, textureMap.getId());
        glUniform1i(glGetUniformLocation(programmeId, "tex"), 0);
    }

    @Override
    public void close() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glUseProgram(0);
    }
    
    @Override
    public void destroy() {
        this.close();
        textureMap.destroy();
        vertexShader.destroy();
        fragShader.destroy();
        glDeleteProgram(programmeId);
    }

    @Override
    public boolean supportsTextures() {
        return false;
    }

    @Override
    public void draw(DisplayObject object) {
        if (object.shouldDraw()) {
            FloatBuffer temp = BufferUtils.createFloatBuffer(16);
            float[] matrix = getCurrentMatrix().getPoints();
            temp.put(matrix);
            temp.flip();
            glUniformMatrix4fv(glGetUniformLocation(programmeId, "Transformation"), false, temp);
            glUniform1i(glGetUniformLocation(programmeId, "hasTex"), object.hasTexture() ? 1 : 0);
            
            logger.trace("VAO " + object.getVertexArrayObjectID() + (glIsVertexArray(object.getVertexArrayObjectID()) ? " Exists" : " Doesn't exist"));
            logger.trace("Vertices buffer " + object.getVerticesID() + (glIsBuffer(object.getVerticesID()) ? " Exists" : " Doesn't exist"));
            
            glBindVertexArray(object.getVertexArrayObjectID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
    
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, object.getEdgesID());
            
            glDrawElements(object.getDisplayType(), object.getNumberOfPoints(), GL_UNSIGNED_INT, 0);
    
            //glDisableClientState(GL_VERTEX_ARRAY);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    
            glDisableVertexAttribArray(2);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(0);
            glBindVertexArray(0);
        }
    }

    @Override
    public boolean supportsVertArrayObj() {
        return true;
    }

    @Override
    public void deleteObject(DisplayObject displayObject) {
        glDeleteBuffers(displayObject.getVerticesID());
        glDeleteBuffers(displayObject.getEdgesID());
        glDeleteBuffers(displayObject.getColourID());
        glDeleteBuffers(displayObject.getTextureID());
        glDeleteBuffers(displayObject.getVertexArrayObjectID());
    }

    @Override
    public void updateObjectColour(DisplayObject displayObject) {
        glBindBuffer(GL_ARRAY_BUFFER, displayObject.getColourID());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getColourBuffer(), GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

}
