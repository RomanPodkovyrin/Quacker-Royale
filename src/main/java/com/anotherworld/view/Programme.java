package com.anotherworld.view;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.Matrix2d;
import com.anotherworld.view.data.TextDisplayObject;
import com.anotherworld.view.graphics.Camera;
import com.anotherworld.view.input.MouseState;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

import org.lwjgl.BufferUtils;

/**
 * Stores information about how the game will be rendered, changes opengl options to use it and renders all of the game.
 * @author Jake Stewart
 *
 */
public abstract class Programme {
    
    private Stack<Matrix2d> matrixStack;
    
    private final long window;
    
    public Programme(long window) throws ProgrammeUnavailableException {
        matrixStack = new Stack<>();
        this.window = window;
    }

    /**
     * Switch to use this programme for rendering.
     */
    public abstract void use();
    
    /**
     * Stop using this programme for rendering.
     */
    public abstract void close();
    
    /**
     * Returns true if the programme supports rendering textures.
     * @return if the programme supports textures
     */
    public abstract boolean supportsTextures();
    
    /**
     * Returns if the programme supports vertex array objects.
     * @return returns if the programme supports vaos
     */
    public abstract boolean supportsVertArrayObj();

    /**
     * Deletes all of the opengl compiled code.s
     */
    public abstract void destroy();

    /**
     * Applies matrix transformations using the camera's position so it is centred on the screen.
     * @param camera The camera to use for display
     */
    public void transform(Camera camera) {
        this.multiplyCurrent(camera.transform());
    }

    /**
     * Returns a 4 by 4 identity matrix.
     * @return the matrix
     */
    private Matrix2d getIdentity() {
        return Matrix2d.genIdentity(4);
    }
    
    /**
     * Returns a 4 by 4 translation matrix.
     * @param x the x translation
     * @param y the y translation
     * @param z the z translation
     * @return the translation matrix
     */
    private Matrix2d getTranslation(float x, float y, float z) {
        return Matrix2d.homTranslate3d(x, y, z);
    }
    
    /**
     * Returns a 4 by 4 scale matrix.
     * @param x the x scale
     * @param y the y scale
     * @param z the z scale
     * @return the scale matrix
     */
    private Matrix2d getScale(float x, float y, float z) {
        return Matrix2d.homScale3d(x, y, z);
    }
    
    /**
     * Returns a matrix of theta degrees around the z axis.
     * @param theta the angle
     * @return the rotation matrix
     */
    private Matrix2d getRotation(float theta) {
        return Matrix2d.homRotate3d(theta);
    }
    
    /**
     * Translates the current matrix on the stack.
     * @param b the translation matrix
     */
    private void multiplyCurrent(Matrix2d b) {
        Matrix2d currentMatrix = getCurrentMatrix();
        if (!matrixStack.isEmpty()) {
            matrixStack.pop();
        }
        matrixStack.push(currentMatrix.mult(b));
    }
    
    /**
     * Returns the current matrix on the stack or the identity if the stack is empty.
     * @return The current matrix
     */
    public Matrix2d getCurrentMatrix() {
        if (matrixStack.isEmpty()) {
            return getIdentity();
        }
        return matrixStack.peek();
    }

    /**
     * Save the current matrix to the stack.
     */
    public void pushMatrix() {
        matrixStack.push(getCurrentMatrix());
    }


    /**
     * Resets the current matrix transformations.
     */
    public void loadIdentity() {
        matrixStack.clear();
    }

    /**
     * Translates the current matrix in the x y and z axis.
     * @param x the x translation
     * @param y the y translation
     * @param z the z translation
     */
    public void translatef(float x, float y, float z) {
        multiplyCurrent(getTranslation(x, y, z));
    }

    /**
     * Scales the current matrix in the x y and z axis.
     * @param x the x scale
     * @param y the y scale
     * @param z the z scale
     */
    public void scalef(float x, float y, float z) {
        multiplyCurrent(getScale(x, y, z));
    }

    /**
     * Rotates the current matrix around the line x y z by the angle.
     * @param angle The angle in degrees
     * @param x the x component of the line
     * @param y the y component of the line
     * @param z the z component of the line
     */
    public void rotatef(float angle, float x, float y, float z) {
        assert (y == 0);
        assert (z == 0);
        multiplyCurrent(getRotation(angle));
    }


    /**
     * Resets to the last matrix pushed onto the stack or the identity if there are none left.
     */
    public void popMatrix() {
        if (!matrixStack.isEmpty()) {
            matrixStack.pop();
        }
    }

    /**
     * Draws an object.
     * @param object The display object to draw
     */
    public abstract void draw(DisplayObject object);

    /**
     * Creates the programme buffers for the display object.
     * @param displayObject the object to initialise for
     */
    public abstract int initialiseDisplayObject(DisplayObject displayObject);

    /**
     * Deletes the programme buffers for the display object.
     * @param displayObject the object to delete the buffers for
     */
    public abstract void deleteObject(DisplayObject displayObject);

    /**
     * Updates the colour of the display object.
     * @param displayObject the object to update
     */
    public abstract void updateObjectColour(DisplayObject displayObject);

    /**
     * Returns the cursors current position.
     * @return A mouse state containing the position
     */
    public MouseState getCursorPosition() {
        DoubleBuffer cursorX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer cursorY = BufferUtils.createDoubleBuffer(1);
        
        glfwGetCursorPos(window, cursorX, cursorY);
        
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        
        glfwGetWindowSize(window, width, height);
        float x = -1 + (2 * (float)cursorX.get() / ((float)width.get()));
        float y = 1 + -(2 * (float)cursorY.get() / ((float)height.get()));
        
        return new MouseState(x, y, glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == 1);
    }

    public abstract void updateBuffers(DisplayObject DisplayObject);
    
}
