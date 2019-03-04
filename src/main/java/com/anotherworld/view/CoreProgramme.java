package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.*;

import com.anotherworld.view.data.Matrix2d; 

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Optional;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.BufferUtils;

/**
 * The core rendering engine compatible with most systems.
 * @author Jake Stewart
 *
 */
public class CoreProgramme extends Programme {

    private static Logger logger = LogManager.getLogger();
    
    private Stack<Matrix2d> matrixStack;
    
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
        matrixStack = new Stack<>();
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

            this.vertexShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/Test.vs", GL_VERTEX_SHADER);
            this.fragShader = new Shader("src/main/glsl/com/anotherworld/view/shaders/Test.frag", GL_FRAGMENT_SHADER);
            
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
        glUniform1i(uniformId.get(), 0);
    }
    
    @Override
    public void use() {
        glUseProgram(programmeId);
        //glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureMap.getId());
        setUpUniform();
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
    public boolean supportsVertArrayObj() {
        return true;
    }
    
    private Matrix2d getIdentity() {
        return Matrix2d.genIdentity(4);
    }
    
    private Matrix2d getTranslation(float x, float y, float z) {
        return Matrix2d.homTranslate3d(x, y, z);
    }
    
    private Matrix2d getScale(float x, float y, float z) {
        return Matrix2d.homScale3d(x, y, z);
    }
    
    private Matrix2d getRotation(float theta) {
        return Matrix2d.homRotate3d(theta);
    }
    
    private Matrix2d getCurrentMatrix() {
        if (matrixStack.isEmpty()) {
            return getIdentity();
        }
        return matrixStack.peek();
    }
    
    private void multiplyCurrent(Matrix2d b) {
        Matrix2d currentMatrix = getCurrentMatrix();
        if (!matrixStack.isEmpty()) {
            matrixStack.pop();
        }
        matrixStack.push(currentMatrix.mult(b));
    }

    @Override
    public void pushMatrix() {
        matrixStack.push(getCurrentMatrix());
    }

    @Override
    public void loadIdentity() {
        matrixStack.clear();
    }

    @Override
    public void translatef(float x, float y, float z) {
        multiplyCurrent(getTranslation(x, y, z));
    }

    @Override
    public void scalef(float x, float y, float z) {
        multiplyCurrent(getScale(x, y, z));
    }

    @Override
    public void rotatef(float angle, float x, float y, float z) {
        assert (y == 0);
        assert (z == 0);
        multiplyCurrent(getRotation(angle));
    }

    @Override
    public void popMatrix() {
        if (!matrixStack.isEmpty()) {
            matrixStack.pop();
        }
    }

    @Override
    public void draw() {
        FloatBuffer temp = BufferUtils.createFloatBuffer(16);
        float[] matrix = getCurrentMatrix().getPoints();
        temp.put(matrix);
        temp.flip();
        int uniformId = glGetUniformLocation(programmeId, "Transformation");
        glUniformMatrix4fv(uniformId, false, temp);
    }

}
