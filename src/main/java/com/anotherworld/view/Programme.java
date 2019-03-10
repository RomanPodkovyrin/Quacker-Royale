package com.anotherworld.view;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.Matrix2d;
import com.anotherworld.view.data.Points2d;
import com.anotherworld.view.graphics.Camera;

import java.util.Stack;

/**
 * Stores information about how the game will be rendered, changes opengl options to use it and renders all of the game.
 * @author Jake Stewart
 *
 */
public abstract class Programme {
    
    private Stack<Matrix2d> matrixStack;
    
    public Programme() throws ProgrammeUnavailableException {
        matrixStack = new Stack<>();
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
        this.cameraProjectionf(camera.getDepth());
        this.scalef(-2 / camera.getWidth(), -2 / camera.getHeight(), 1);
        this.cameraRotation(camera);
        this.translatef(-camera.getX(), -camera.getY(), -camera.getZ());
    }
    
    private void cameraRotation(Camera camera) {
        Points2d n = camera.getViewDirection().normalise();
        Points2d v = camera.getHandedness().normalise();
        Points2d u = camera.getUpDirection().normalise();
        Matrix2d rotation = new Matrix2d(4, 4);
        for (int i = 0; i < 3; i++) {
            rotation.setValue(0, i, v.getValue(i, 0));
            rotation.setValue(1, i, u.getValue(i, 0));
            rotation.setValue(2, i, n.getValue(i, 0));
        }
        rotation.setValue(3, 3, 1f);
        this.multiplyCurrent(rotation);
    }

    private void cameraProjectionf(float depth) {
        Matrix2d projection = this.getIdentity();
        projection.setValue(3, 2, 1 / depth);
        this.multiplyCurrent(projection);
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
    
}
