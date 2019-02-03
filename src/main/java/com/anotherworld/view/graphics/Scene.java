package com.anotherworld.view.graphics;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Scene {

    private static Logger logger = LogManager.getLogger(Scene.class);
    
    protected ArrayList<GraphicsDisplay> displays;
    
    public Scene() {
        displays = new ArrayList<>();
    }
    
    public void draw(int width, int height) {
        for (int i = 0; i < displays.size(); i++) {
            GraphicsDisplay display = displays.get(i);
            int x = convertCoord(display.getX(), width);
            int y = convertCoord(display.getY(), height);
            int w = convertScale(display.getWidth(), width, x);
            int h = convertScale(display.getHeight(), height, y);
            glViewport(x, y, w, h);
            ArrayList<Matrix2d> toDraw = display.draw();
            for (int j = 0; j < toDraw.size(); j++) {
                drawMatrix(toDraw.get(j));
            }
            
        }
    }

    private void drawMatrix(Matrix2d a) {
        logger.trace("Drawing matrix " + a.toString());
        glBegin(GL_POLYGON);
        for (int j = 0; j < a.getN(); j++) {
            glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
        }
        glEnd();
        logger.trace("Finished drawing matrix");
    }
    
    private int convertCoord(float value, int scale) {
        return Math.min(scale, Math.max(0, (int)Math.round(value * ((float)scale))));
    }
    
    private int convertScale(float floatScale, int intScale, int intValue) {
        return Math.min(intScale - intValue, Math.max(0, (int)Math.round(floatScale * ((float)intScale))));
    }
    
}
