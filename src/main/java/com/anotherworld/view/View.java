package com.anotherworld.view;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.lwjgl.opengl.*;

import com.anotherworld.view.graphics.Matrix2d;
import com.anotherworld.view.graphics.displayobject.Ball;
import com.anotherworld.view.graphics.displayobject.DisplayObject;
import com.anotherworld.view.graphics.displayobject.Player;
import com.anotherworld.tools.input.KeyListener;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class View implements Runnable {
    
    private static Logger logger = LogManager.getLogger(View.class);

    private Long window;

    private DisplayObject[] objects;
    
    private KeyListener keyListener;

    public View() {
        logger.info("Creating view");
        objects = new DisplayObject[10];
        for (int i = 0; i < 5; i++) {
            objects[i] = (new Ball((float) Math.random() * 160, (float) Math.random() * 90,
                    (float) Math.random() * 360));
            objects[i + 5] = (new Player((float) Math.random() * 160, (float) Math.random() * 90, 10f));
        }
    }
    
    public KeyListener getKeyListener() {
        return keyListener;
    }

    public void display(DisplayObject[] players, DisplayObject[] balls, DisplayObject[] platform, DisplayObject[] wall) {
        synchronized (objects) {
            objects = new DisplayObject[players.length + balls.length + platform.length + wall.length];
            int index = 0;
            for (int i = 0; i < platform.length; i++) {
                objects[i] = platform[i];
            }
            index += platform.length;
            for (int i = 0; i < balls.length; i++) {
                objects[i + index] = balls[i];
            }
            index += balls.length;
            for (int i = 0; i < players.length; i++) {
                objects[i + index] = players[i];
            }
            index += players.length;
            for (int i = 0; i < wall.length; i++) {
                objects[i + index] = wall[i];
            }
            index += wall.length;
        }
    }

    private void drawMatrix(Matrix2d a) {
        glBegin(GL_POLYGON);
        for (int j = 0; j < a.getN(); j++) {
            glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
        }
        glEnd();
    }

    private Matrix2d calculateViewMatrix(float camX, float camY, float camW, float camH, float xRes, float yRes) {
        Matrix2d modifier;

        modifier = (Matrix2d.H_TRANSLATION_2D(-1f, 1f));

        modifier = modifier.mult(Matrix2d.H_SCALE_2D(1f, -1f));

        modifier = modifier.mult(Matrix2d.H_SCALE_2D(2 / camW, 2 / camH));

        modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(-camX, -camY));

        return modifier;
    }

    private void drawObject(DisplayObject obj, Matrix2d viewMatrix) {
        Matrix2d modifier = viewMatrix;

        modifier = modifier.mult(Matrix2d.H_TRANSLATION_2D(obj.getX(), obj.getY()));

        modifier = modifier.mult(Matrix2d.H_ROTATION_2D(obj.getTheta()));

        drawMatrix(modifier.mult(obj.getPoints()));

    }

    @Deprecated
    public static void main(String[] args) {
        View view = new View();
        view.run();
    }

    @Override
    public void run() {
        logger.info("Running view");
        if (!glfwInit()) {
            throw new IllegalStateException("Couldn't initialise glfw");
        }

        window = glfwCreateWindow(1120, 630, "Bullet Hell", NULL, NULL);

        if (window == null) {
            glfwTerminate();
            throw new RuntimeException("Couldn't create glfw window");
        }

        glfwMakeContextCurrent(window);
        
        keyListener = new KeyListener(window);

        GL.createCapabilities();

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);

            Matrix2d viewMatrix = calculateViewMatrix(0, 0, 160, 90, 0, 0);

            for (DisplayObject obj : objects) {
                drawObject(obj, viewMatrix);
            }
            
            glFlush();

            glfwSwapBuffers(window);

            glfwPollEvents();

        }

        glfwTerminate();
    }

}
