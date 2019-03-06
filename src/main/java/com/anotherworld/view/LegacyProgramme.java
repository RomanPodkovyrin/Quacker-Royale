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

import java.nio.FloatBuffer;

public class LegacyProgramme extends Programme {

    public LegacyProgramme() throws ProgrammeUnavailableException {
        super();
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
        FloatBuffer points = object.getFloatBuffer();
        glColor4f(object.getR(), object.getG(), object.getB(), 1f);
        glBegin(object.getDisplayType());
        while (points.hasRemaining()) {
            glVertex4f(points.get(), points.get(), points.get(), points.get());
        }
        glEnd();
    }

    @Override
    public void initialiseDisplayObject(DisplayObject displayObject) {
    }

    @Override
    public void deleteObject(DisplayObject displayObject) {
    }

    @Override
    public void updateObjectColour(DisplayObject displayObject) {
    }

}
