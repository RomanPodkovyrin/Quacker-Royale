package com.anotherworld.view.programme;

import static org.lwjgl.opengl.GL46.glBegin;
import static org.lwjgl.opengl.GL46.glColor4f;
import static org.lwjgl.opengl.GL46.glEnd;
import static org.lwjgl.opengl.GL46.glVertex4f;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.primatives.DrawType;
import com.anotherworld.view.data.primatives.Points2d;

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
        glBegin(DrawType.convertToInt(object.getDisplayType()));
        points = this.getCurrentMatrix().mult(points);
        for (int j = 0; j < points.getN(); j++) {
            glVertex4f(points.getValue(0, j), points.getValue(1, j), points.getValue(2, j), points.getValue(3, j));
        }
        glEnd();
    }

    @Override
    public void updateObjectColour(DisplayObject displayObject) {
    }

    @Override
    public void updateBuffers(DisplayObject textDisplayObject) {
    }

}
