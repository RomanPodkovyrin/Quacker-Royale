package com.anotherworld.view.graphics;

import static org.lwjgl.opengl.GL46.glScalef;
import static org.lwjgl.opengl.GL46.glTranslatef;

public interface Camera {

    /**
     * Applies matrix transformations using the camera's position so it is centred on the screen.
     * @param camera The camera to use for display
     */
    public static void transform(Camera camera) {
        
        glScalef(2 / camera.getWidth(), -2 / camera.getHeight(), 1);
        
        glTranslatef(-camera.getX(), -camera.getY(), 0);
        
        /*Matrix2d modifier;

        modifier = (Matrix2d.homTranslation2d(-1f, 1f));

        modifier = modifier.mult(Matrix2d.homScale2d(1f, -1f));

        modifier = modifier.mult(Matrix2d.homScale2d(2 / camera.getWidth(), 2 / camera.getHeight()));

        modifier = modifier.mult(Matrix2d.homTranslation2d(-camera.getX(), -camera.getY()));*/
    }

    public float getX();

    public float getY();

    public float getWidth();

    public float getHeight();

}
