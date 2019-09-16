package com.anotherworld.view.programme;

import static org.lwjgl.opengl.GL46.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL46.GL_BLEND;
import static org.lwjgl.opengl.GL46.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL46.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL46.GL_FLOAT;
import static org.lwjgl.opengl.GL46.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL46.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL46.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL46.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL46.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL46.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL46.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL46.glAttachShader;
import static org.lwjgl.opengl.GL46.glBindAttribLocation;
import static org.lwjgl.opengl.GL46.glBindBuffer;
import static org.lwjgl.opengl.GL46.glBindTexture;
import static org.lwjgl.opengl.GL46.glBindVertexArray;
import static org.lwjgl.opengl.GL46.glBlendFunc;
import static org.lwjgl.opengl.GL46.glBufferData;
import static org.lwjgl.opengl.GL46.glCreateProgram;
import static org.lwjgl.opengl.GL46.glDeleteBuffers;
import static org.lwjgl.opengl.GL46.glDeleteProgram;
import static org.lwjgl.opengl.GL46.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL46.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL46.glDrawElements;
import static org.lwjgl.opengl.GL46.glEnable;
import static org.lwjgl.opengl.GL46.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL46.glGenBuffers;
import static org.lwjgl.opengl.GL46.glGenVertexArrays;
import static org.lwjgl.opengl.GL46.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL46.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL46.glGetShaderi;
import static org.lwjgl.opengl.GL46.glGetUniformLocation;
import static org.lwjgl.opengl.GL46.glLinkProgram;
import static org.lwjgl.opengl.GL46.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL46.glUseProgram;
import static org.lwjgl.opengl.GL46.glVertexAttribPointer;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.TexturedDisplayObjectBuffers;
import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.texture.Shader;
import com.anotherworld.view.texture.TextureMap;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.BufferUtils;

/**
 * The core rendering engine compatible with most systems.
 * 
 * @author Jake Stewart
 *
 */
public class TexturedProgramme extends Programme {

    private static Logger logger = LogManager.getLogger();

    private int programmeId;
    private TextureMap textureMap;
    private Shader vertexShader;
    private Shader fragShader;

    private final ArrayList<TexturedDisplayObjectBuffers> bufferObjects;
    private final ArrayList<Integer> freeBuffers;

    /**
     * Creates the core programme that should be compatible with most systems.
     * 
     * @param window the glfw window id
     * @throws ProgrammeUnavailableException If the programme isn't compatible with
     *                                       the system or wasn't found
     */
    public TexturedProgramme(String location, long window) throws ProgrammeUnavailableException {
        super(window);
        init(location);
        try {
            textureMap = new TextureMap("res/images/");
        } catch (IOException ex) {
            logger.catching(ex);
            throw new ProgrammeUnavailableException("Couldn't load texture map");
        }
        bufferObjects = new ArrayList<>();
        freeBuffers = new ArrayList<>();
    }

    private void init(String location) throws ProgrammeUnavailableException {
        logger.info("Creating shaders");

        try {

            this.vertexShader = new Shader(location + "vertex.glsl", GL_VERTEX_SHADER);
            this.fragShader = new Shader(location + "frag.glsl", GL_FRAGMENT_SHADER);

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

    private void initialiseDisplayObject(DisplayObject displayObject) {

        TexturedDisplayObjectBuffers bufferObject = new TexturedDisplayObjectBuffers();

        bufferObject.setVertexArrayObjectId(glGenVertexArrays());
        glBindVertexArray(bufferObject.getVertexArrayObjectId());

        bufferObject.setVerticesId(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getVerticesId());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getVertexBuffer(), GL_STATIC_DRAW);

        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        bufferObject.setColourId(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getColourId());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getColourBuffer(), GL_STATIC_DRAW);

        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        bufferObject.setTextureId(glGenBuffers());
        glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getTextureId().get());
        glBufferData(GL_ARRAY_BUFFER, displayObject.getTextureBuffer(), GL_STATIC_DRAW);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        bufferObject.setEdgesId(glGenBuffers());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject.getEdgesId());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, displayObject.getEdgeBuffer(), GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        displayObject.setProgrammeObjectId(allocateIndex(bufferObject));
        logger.trace("Created object " + displayObject.getClass().toString() + ":" + displayObject.getProgrammeObjectId());

    }

    @Override
    public void use() {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glUseProgram(programmeId);
    }

    @Override
    public void close() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glUseProgram(0);
    }

    @Override
    public void destroy() {
        this.close();
        for (int i = 0; i < bufferObjects.size(); i++) {
            destroyBufferObject(i);
        }
        textureMap.destroy();
        vertexShader.destroy();
        fragShader.destroy();
        glDeleteProgram(programmeId);
    }

    private void destroyBufferObject(int indexToFree) {
        TexturedDisplayObjectBuffers bufferObject = bufferObjects.get(indexToFree);
        glDeleteBuffers(bufferObject.getVerticesId());
        glDeleteBuffers(bufferObject.getEdgesId());
        glDeleteBuffers(bufferObject.getColourId());
        if (bufferObject.getTextureId().isPresent()) {
            glDeleteBuffers(bufferObject.getTextureId().get());
        }
        glDeleteVertexArrays(bufferObject.getVertexArrayObjectId());
        freeBuffers.add(indexToFree);
    }
    
    @Override
    public void destory(DisplayObject object) {
        if (object.getProgrammeObjectId().isPresent()) {
            destroyBufferObject(object.getProgrammeObjectId().get());
            object.removeProgrammeObjectId();
        }
    }

    private int allocateIndex(TexturedDisplayObjectBuffers bufferObject) {
        if (freeBuffers.size() > 0) {
            int returnBuffer = freeBuffers.get(0);
            freeBuffers.remove(0);
            bufferObjects.remove(returnBuffer);
            bufferObjects.add(returnBuffer, bufferObject);
            return returnBuffer;
        }
        bufferObjects.add(bufferObject);
        return bufferObjects.size() - 1;
    }

    @Override
    public boolean supportsTextures() {
        return true;
    }

    @Override
    public void draw(DisplayObject displayObject) {
        if (displayObject.shouldDraw()) {
            if (!displayObject.getProgrammeObjectId().isPresent()) {
                this.initialiseDisplayObject(displayObject);
            }
            FloatBuffer temp = BufferUtils.createFloatBuffer(16);
            float[] matrix = getCurrentMatrix().getPoints();
            temp.put(matrix);
            temp.flip();
            glUniformMatrix4fv(glGetUniformLocation(programmeId, "Transformation"), false, temp);

            textureMap.loadTexture(programmeId, displayObject.getSpriteSheet());

            TexturedDisplayObjectBuffers bufferObject = bufferObjects.get(displayObject.getProgrammeObjectId().get());

            glBindVertexArray(bufferObject.getVertexArrayObjectId());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject.getEdgesId());

            glDrawElements(DrawType.convertToInt(displayObject.getDisplayType()), displayObject.getNumberOfPoints(),
                    GL_UNSIGNED_INT, 0);

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
    public void updateObjectColour(DisplayObject displayObject) {
        if (displayObject.getProgrammeObjectId().isPresent()) {
            TexturedDisplayObjectBuffers bufferObject = bufferObjects.get(displayObject.getProgrammeObjectId().get());

            glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getColourId());
            glBufferData(GL_ARRAY_BUFFER, displayObject.getColourBuffer(), GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    @Override
    public void updateBuffers(DisplayObject displayObject) {
        if (displayObject.getProgrammeObjectId().isPresent()) {
            TexturedDisplayObjectBuffers bufferObject = bufferObjects.get(displayObject.getProgrammeObjectId().get());
            glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getVerticesId());
            glBufferData(GL_ARRAY_BUFFER, displayObject.getVertexBuffer(), GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getColourId());
            glBufferData(GL_ARRAY_BUFFER, displayObject.getColourBuffer(), GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            if (displayObject.getSpriteSheet().isTextured()) {
                glBindBuffer(GL_ARRAY_BUFFER, bufferObject.getTextureId().get());
                glBufferData(GL_ARRAY_BUFFER, displayObject.getTextureBuffer(), GL_STATIC_DRAW);
                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject.getEdgesId());
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, displayObject.getEdgeBuffer(), GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }

}
