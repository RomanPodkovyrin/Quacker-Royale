package com.anotherworld.view;

import static org.lwjgl.opengl.GL46.glBegin;
import static org.lwjgl.opengl.GL46.glColor4f;
import static org.lwjgl.opengl.GL46.glEnd;
import static org.lwjgl.opengl.GL46.glVertex4f;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.Points2d;

public class LegacyProgramme extends Programme {

    public LegacyProgramme() throws ProgrammeUnavailableException {
        super();
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
        Points2d points = object.getPoints();
        glColor4f(object.getR(), object.getG(), object.getB(), 1f);
        glBegin(object.getDisplayType());
        points = this.getCurrentMatrix().mult(points);
        for (int j = 0; j < points.getN(); j++) {
            glVertex4f(points.getValue(0, j), points.getValue(1, j), points.getValue(2, j), points.getValue(3, j));
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

}
