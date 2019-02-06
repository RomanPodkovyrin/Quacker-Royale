package com.anotherworld.view;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.anotherworld.view.graphics.GameScene;
import com.anotherworld.view.graphics.Scene;
import com.anotherworld.view.graphics.displayobject.DisplayObject;
import com.anotherworld.view.input.KeyListener;
import com.anotherworld.view.input.KeyListenerNotFoundException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.opengl.*;

/**
 * Creates a window and manages the game's display.
 * @author Jake Stewart
 *
 */
public class View implements Runnable {

    private static Logger logger = LogManager.getLogger(View.class);

    private Long window;

    private Scene currentScene;

    private CountDownLatch keyListenerLatch;
    
    private volatile KeyListener keyListener;

    private int height;

    private int width;
    
    private Queue<ViewEvent> eventQueue;

    /**
     * Creates the View object initialising it's values.
     */
    public View() {
        logger.info("Creating view");
        height = 630;
        width = 1120;
        eventQueue = new LinkedList<>();
        keyListenerLatch = new CountDownLatch(1);
    }

    /**
     * Returns the key listener for the view's window.
     * @return The keyListener
     * @throws KeyListenerNotFoundException If the keyListener couldn't be retrieved in 10 seconds
     */
    public KeyListener getKeyListener() throws KeyListenerNotFoundException {
        logger.info("Request for key listener objected");
        try {
            if (keyListenerLatch.await(10, TimeUnit.SECONDS)) {
                return keyListener;
            }
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        throw new KeyListenerNotFoundException("Timeout of 10 seconds, was window initialized");
    }
    
    private void displayGame(DisplayObject[] objects) throws WrongSceneException {
        if (!currentScene.getClass().equals(GameScene.class)) {
            throw new WrongSceneException("Not currently in game state");
        }
        synchronized (eventQueue) {
            eventQueue.add(new UpdateDisplayObjects(objects));
        }
    }

    @Override
    public void run() {
        logger.info("Running view");
        if (!glfwInit()) {
            logger.fatal("Unable to initialise glfw");
            throw new IllegalStateException("Couldn't initialise glfw");
        }

        logger.debug("Creating window");
        window = glfwCreateWindow(width, height, "Bullet Hell", NULL, NULL);

        if (window == null) {
            logger.fatal("Unable to create game window");
            glfwTerminate();
            throw new RuntimeException("Couldn't create glfw window");
        }

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        keyListener = new KeyListener(window);
        
        keyListenerLatch.countDown();

        currentScene = new GameScene(true);

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);
            
            synchronized (eventQueue) {
                while (!eventQueue.isEmpty()) {
                    completeEvent(eventQueue.poll());
                }
            }

            currentScene.draw(width, height, window);

            glFlush();

            glfwSwapBuffers(window);

            logger.debug("Polling for glfw events");

            glfwPollEvents();

        }
        logger.info("Closing window");
        glfwTerminate();
    }
    
    private void completeEvent(ViewEvent event) {
        if (event.getClass().equals(UpdateDisplayObjects.class) && currentScene.getClass().equals(GameScene.class)) {
            ((GameScene)currentScene).updateGameObjects(((UpdateDisplayObjects)event).getObjects());
        }
    }

}
