package com.anotherworld.view;

import com.anotherworld.view.graphics.Camera;

/**
 * Stores information about how the game will be rendered and changes opengl options to use it.
 * @author Jake Stewart
 *
 */
public abstract class Programme {

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
        this.scalef(2 / camera.getWidth(), -2 / camera.getHeight(), 1);
        this.translatef(-camera.getX(), -camera.getY(), 0);
    }

    /**
     * Resets the current matrix transformations.
     */
    public abstract void loadIdentity();

    /**
     * Save the current matrix to the stack.
     */
    public abstract void pushMatrix();

    /**
     * Translates the current matrix in the x y and z axis.
     * @param x the x translation
     * @param y the y translation
     * @param z the z translation
     */
    public abstract void translatef(float x, float y, float z);

    /**
     * Scales the current matrix in the x y and z axis.
     * @param x the x scale
     * @param y the y scale
     * @param z the z scale
     */
    public abstract void scalef(float x, float y, float z);

    /**
     * Rotates the current matrix around the line x y z by the angle.
     * @param angle The angle in degrees
     * @param x the x component of the line
     * @param y the y component of the line
     * @param z the z component of the line
     */
    public abstract void rotatef(float angle, float x, float y, float z);

    /**
     * Resets to the last matrix pushed onto the stack or the identity if there are none left.
     */
    public abstract void popMatrix();
    
}
