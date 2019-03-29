package com.anotherworld.view.programme;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;

import static org.lwjgl.opengl.GL46.glViewport;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.primatives.Matrix2d;
import com.anotherworld.view.data.primatives.Points2d;
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
    
    private Stack<Matrix2d> inverseMatrixStack;
    
    private final long window;
    
    private boolean mouseDown;
    
    /**
     * Creates a programme that will draw to the selected window.
     * @param window The window to draw to
     * @throws ProgrammeUnavailableException If the programme couldn't be created for rendering
     */
    public Programme(long window) throws ProgrammeUnavailableException {
        matrixStack = new Stack<>();
        inverseMatrixStack = new Stack<>();
        this.mouseDown = false;
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
    public final void transform(Camera camera) {
        this.multiplyCurrent(camera.transform());
        this.multiplyInverseCurrent(camera.inverseTransform());
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
     * Translates the current matrix on the inverse stack.
     * @param b the translation matrix
     */
    private void multiplyInverseCurrent(Matrix2d b) {
        Matrix2d currentMatrix = getCurrentInverseMatrix();
        if (!inverseMatrixStack.isEmpty()) {
            inverseMatrixStack.pop();
        }
        inverseMatrixStack.push(b.mult(currentMatrix));
    }
    
    /**
     * Returns the current matrix on the stack or the identity if the stack is empty.
     * @return The current matrix
     */
    public final Matrix2d getCurrentMatrix() {
        if (matrixStack.isEmpty()) {
            return getIdentity();
        }
        return matrixStack.peek();
    }
    
    /**
     * Returns the current matrix on the stack or the identity if the stack is empty.
     * @return The current matrix
     */
    public final Matrix2d getCurrentInverseMatrix() {
        if (inverseMatrixStack.isEmpty()) {
            return getIdentity();
        }
        return inverseMatrixStack.peek();
    }

    /**
     * Save the current matrix to the stack.
     */
    public final void pushMatrix() {
        matrixStack.push(getCurrentMatrix());
        inverseMatrixStack.push(getCurrentInverseMatrix());
    }


    /**
     * Resets the current matrix transformations.
     */
    public final void loadIdentity() {
        matrixStack.clear();
        inverseMatrixStack.clear();
    }

    /**
     * Translates the current matrix in the x y and z axis.
     * @param x the x translation
     * @param y the y translation
     * @param z the z translation
     */
    public final void translatef(float x, float y, float z) {
        multiplyCurrent(getTranslation(x, y, z));
        multiplyInverseCurrent(getTranslation(1 / x, 1 / y, 1 / z));
    }

    /**
     * Scales the current matrix in the x y and z axis.
     * @param x the x scale
     * @param y the y scale
     * @param z the z scale
     */
    public final void scalef(float x, float y, float z) {
        multiplyCurrent(getScale(x, y, z));
        multiplyInverseCurrent(getScale(1 / x, 1 / y, 1 / z));
    }

    /**
     * Rotates the current matrix around the line x y z by the angle.
     * @param angle The angle in degrees
     * @param x the x component of the line
     * @param y the y component of the line
     * @param z the z component of the line
     */
    public final void rotatef(float angle, float x, float y, float z) {
        assert (y == 0);
        assert (x == 0);
        multiplyCurrent(getRotation(angle));
        multiplyInverseCurrent(getRotation(-angle));
    }


    /**
     * Resets to the last matrix pushed onto the stack or the identity if there are none left.
     */
    public final void popMatrix() {
        if (!matrixStack.isEmpty()) {
            matrixStack.pop();
        }
        if (!inverseMatrixStack.isEmpty()) {
            inverseMatrixStack.pop();
        }
    }

    /**
     * Draws an object.
     * @param object The display object to draw
     */
    public abstract void draw(DisplayObject object);

    /**
     * Updates the colour of the display object.
     * @param displayObject the object to update
     */
    public abstract void updateObjectColour(DisplayObject displayObject);
    
    /**
     * Returns if the cursor has just been pressed.
     * @return if the cursor has just been pressed since last call
     */
    public boolean getCursorPressed() {
        
        boolean pressed = glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == 1;
        
        if (pressed) {
            if (!mouseDown) {
                mouseDown = true;
            } else {
                pressed = false;
            }
        } else {
            mouseDown = false;
        }
        
        return pressed;
        
    }

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
        
        Points2d cursorPoint = new Points2d(4, 1);
        
        cursorPoint.setValue(0, 0, -1 + (2 * (float)cursorX.get() / ((float)width.get())));
        cursorPoint.setValue(1, 0, 1 - (2 * (float)cursorY.get() / ((float)height.get())));
        cursorPoint.setValue(3, 0, 1);
        
        cursorPoint = getCurrentInverseMatrix().mult(cursorPoint);
        
        float x = cursorPoint.getValue(0, 0) / cursorPoint.getValue(3, 0);
        float y = cursorPoint.getValue(1, 0) / cursorPoint.getValue(3, 0);
        
        return new MouseState(x, y);
    }

    /**
     * Updates the stored buffers for the given object.
     * @param displayObject the object to update
     */
    public abstract void updateBuffers(DisplayObject displayObject);

    public void setViewport(int x, int y, int w, int h) {
        glViewport(x, y, w, h);
    }

    public abstract void destory(DisplayObject displayObject);
    
}
