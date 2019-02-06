package com.anotherworld.view;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.anotherworld.view.graphics.GameScene;
import com.anotherworld.view.graphics.Scene;
import com.anotherworld.view.graphics.displayobject.Ball;
import com.anotherworld.view.graphics.displayobject.DisplayObject;
import com.anotherworld.view.graphics.displayobject.Player;
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
    
    private void displayGame(DisplayObject[] players, DisplayObject[] balls, DisplayObject[] platform, DisplayObject[] wall) throws WrongSceneException {
        if (!currentScene.getClass().equals(GameScene.class)) {
            throw new WrongSceneException("Not currently in game state");
        }
        DisplayObject[] objects = new DisplayObject[players.length + balls.length + platform.length + wall.length];
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
        synchronized (eventQueue) {
            eventQueue.add(new UpdateDisplayObjects(objects));
        }
    }

    /**
     * A test method for starting the view.
     * @param args Accepts no args
     */
    @Deprecated
    public static void main(String[] args) {
        DisplayObject[] objects = new DisplayObject[10];
        for (int i = 0; i < 5; i++) {
            objects[i] = (new Ball((float) Math.random() * 160, (float) Math.random() * 90,
                    (float) Math.random() * 360));
            objects[i + 5] = (new Player((float) Math.random() * 160, (float) Math.random() * 90, 10f));
        }
        View view = new View();
        (new Thread(view)).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        try {
            view.displayGame(objects, new DisplayObject[0], new DisplayObject[0], new DisplayObject[0]);
        } catch (WrongSceneException e) {
            e.printStackTrace();
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

        currentScene = new GameScene();

        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT);
            
            synchronized (eventQueue) {
                while (!eventQueue.isEmpty()) {
                    completeEvent(eventQueue.poll());
                }
            }

            currentScene.draw(width, height);

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
