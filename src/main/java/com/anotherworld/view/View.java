package com.anotherworld.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.anotherworld.view.graphics.Matrix2d;
import com.anotherworld.view.graphics.displayobject.Ball;
import com.anotherworld.view.graphics.displayobject.DisplayObject;
import com.anotherworld.view.graphics.displayobject.Player;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class View implements Runnable {
    
    private static Logger logger = LogManager.getLogger(View.class);

    private Long window;

    private DisplayObject[] objects;

    public View() {
        logger.info("Creating view");
        objects = new DisplayObject[10];
        for (int i = 0; i < 5; i++) {
            objects[i] = (new Ball((float) Math.random() * 160, (float) Math.random() * 90,
                    (float) Math.random() * 360));
            objects[i + 5] = (new Player((float) Math.random() * 160, (float) Math.random() * 90, 10f));
        }
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
        logger.trace("Drawing matrix " + a.toString());
        glBegin(GL_POLYGON);
        for (int j = 0; j < a.getN(); j++) {
            glVertex2f(a.getValue(0, j) / a.getValue(2, j), a.getValue(1, j) / a.getValue(2, j));
        }
        glEnd();
        logger.trace("Finished drawing matrix");
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
            logger.fatal("Unable to initialise glfw");
            throw new IllegalStateException("Couldn't initialise glfw");
        }

        logger.debug("Creating window");
        window = glfwCreateWindow(1120, 630, "Bullet Hell", NULL, NULL);

        if (window == null) {
            logger.fatal("Unable to create game window");
            glfwTerminate();
            throw new RuntimeException("Couldn't create glfw window");
        }

        
        glfwMakeContextCurrent(window);

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
        logger.info("Closing window");
        glfwTerminate();
    }

}
