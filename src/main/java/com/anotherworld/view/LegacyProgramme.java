package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.glBegin;
import static org.lwjgl.opengl.GL46.glColor4f;
import static org.lwjgl.opengl.GL46.glEnd;
import static org.lwjgl.opengl.GL46.glLoadIdentity;
import static org.lwjgl.opengl.GL46.glPopMatrix;
import static org.lwjgl.opengl.GL46.glPushMatrix;
import static org.lwjgl.opengl.GL46.glRotatef;
import static org.lwjgl.opengl.GL46.glScalef;
import static org.lwjgl.opengl.GL46.glTranslatef;
import static org.lwjgl.opengl.GL46.glVertex4f;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.TextDisplayObject;

import java.nio.FloatBuffer;

public class LegacyProgramme extends Programme {

    /**
     * Creates a rendering programme using the most basic parts of opengl.
     * @param window The glfw window id
     * @throws ProgrammeUnavailableException If the programme couldn't be initialised
     */
    public LegacyProgramme(long window) throws ProgrammeUnavailableException {
        super(window);
    }

    @Override
    public void pushMatrix() {
        glPushMatrix();
    }

    @Override
    public void loadIdentity() {
        glLoadIdentity();
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

    @Override
    public void use() {
    }

    @Override
    public void close() {
    }

    @Override
    public boolean supportsTextures() {
        return false;
    }

    @Override
    public boolean supportsVertArrayObj() {
        return false;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void draw(DisplayObject object) {
        FloatBuffer points = object.getVertexBuffer();
        glColor4f(object.getR(), object.getG(), object.getB(), 1f);
        glBegin(object.getDisplayType());
        while (points.hasRemaining()) {
            glVertex4f(points.get(), points.get(), points.get(), points.get());
        }
        glEnd();
    }

    @Override
    public int initialiseDisplayObject(DisplayObject displayObject) {
        return 0;
    }

    @Override
    public void deleteObject(DisplayObject displayObject) {
    }

    @Override
    public void updateObjectColour(DisplayObject displayObject) {
    }

    @Override
    public void updateBuffers(DisplayObject textDisplayObject) {
    }

}
