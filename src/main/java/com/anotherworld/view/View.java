package com.anotherworld.view;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glGetError;

import static org.lwjgl.system.MemoryUtil.NULL;

import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.KeyListener;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.data.BallDisplayData;
import com.anotherworld.view.data.BallDisplayObject;
import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.HealthBarDisplayObject;
import com.anotherworld.view.data.PlayerDisplayData;
import com.anotherworld.view.data.PlayerDisplayObject;
import com.anotherworld.view.data.RectangleDisplayData;
import com.anotherworld.view.data.RectangleDisplayObject;
import com.anotherworld.view.data.WallDisplayObject;
import com.anotherworld.view.graphics.GameScene;
import com.anotherworld.view.graphics.Scene;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Platform;


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
    
    private volatile boolean running;

    private int height;

    private int width;
    
    private Queue<ViewEvent> eventQueue;
    
    private Programme programme;

    /**
     * Creates the View object initialising it's values.
     * @param width The screen width
     * @param height The screen height
     */
    public View(int width, int height) {
        logger.info("Creating view");
        this.height = height;
        this.width = width;
        eventQueue = new LinkedList<>();
        keyListenerLatch = new CountDownLatch(1);
        running = true;
        logger.info("Running view");
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
        throw new KeyListenerNotFoundException("Timeout of 10 seconds, check if window was initialized");
    }

    /**
     * Stages new objects to be used for the game display.
     * @param playerObjects The new player objects.
     * @param ballObjects The new ball objects.
     * @param rectangleObjects The new platform objects.
     * @param wallObjects The new wall objects.
     */
    public void updateGameObjects(ArrayList<? extends PlayerDisplayData> playerObjects, ArrayList<? extends BallDisplayData> ballObjects,
            ArrayList<? extends RectangleDisplayData> rectangleObjects, ArrayList<? extends WallData> wallObjects) {
        synchronized (eventQueue) {
            eventQueue.add(new UpdateDisplayObjects(playerObjects, ballObjects, rectangleObjects, wallObjects));
        }
    }

    @Override
    public void run() {
        if (Platform.get() == Platform.MACOSX) {
            java.awt.Toolkit.getDefaultToolkit();
            Configuration.GLFW_CHECK_THREAD0.set(false);
        }
        if (!glfwInit()) {
            logger.fatal("Unable to initialise glfw");
            throw new IllegalStateException("Couldn't initialise glfw");
        }
        running = true;

        logger.debug("Creating window");
        window = glfwCreateWindow(width, height, "Bullet Hell", NULL, NULL);

        glfwSetWindowPos(window, width / 8, height / 8);
        
        if (window == null) {
            logger.fatal("Unable to create game window");
            attemptDestroy();
            throw new RuntimeException("Couldn't create glfw window");
        }

        glfwMakeContextCurrent(window);

        GL.createCapabilities();
        
        keyListener = new KeyListener(window);
        
        keyListenerLatch.countDown();
        //TODO change this
        currentScene = new GameScene();
        
        try {
            programme = new TexturedProgramme(window);
        } catch (ProgrammeUnavailableException e1) {
            logger.catching(e1);
            logger.warn("Couldn't start Textured programme renderer");
            try {
                programme = new LegacyProgramme(window);
            } catch (ProgrammeUnavailableException e2) {
                logger.catching(e2);
                logger.fatal("Couldn't start any rendering engines");
                attemptDestroy();
                throw new RuntimeException("Couldn't start any rendering program");
            }
        }
        
        int error = glGetError();
        
        while (error != GL_NO_ERROR) {
            logger.error("Initialise GL error " + error);
            error = glGetError();
        }
        
        while (!glfwWindowShouldClose(window) && running) {

            glClear(GL_COLOR_BUFFER_BIT);
            
            programme.use();
            
            synchronized (eventQueue) {
                while (!eventQueue.isEmpty()) {
                    completeEvent(eventQueue.poll());
                }
            }
            
            programme.loadIdentity();
            
            currentScene.draw(width, height, programme);

            glFlush();
            
            error = glGetError();
            
            while (error != GL_NO_ERROR) {
                logger.error("Display GL error " + error);
                error = glGetError();
            }

            glfwSwapBuffers(window);

            logger.debug("Polling for glfw events");

            glfwPollEvents();

        }
        attemptDestroy(programme);
    }
    
    public void switchToGameScene() {
        synchronized (eventQueue) {
            eventQueue.add(new SwitchScene(new GameScene()));
        }
    }
    
    private void attemptDestroy() {
        logger.info("Closing window");
        if (currentScene != null) {
            currentScene.destoryObjects();
        }
        glfwTerminate();
    }
    
    private void attemptDestroy(Programme programme) {
        logger.info("Closing window");
        currentScene.destoryObjects();
        programme.destroy();
        glfwTerminate();
    }
    
    private void completeEvent(ViewEvent event) {
        if (event.getClass().equals(UpdateDisplayObjects.class) && currentScene.getClass().equals(GameScene.class)) {
            ArrayList<DisplayObject> disObj = new ArrayList<>();
            UpdateDisplayObjects updateEvent = ((UpdateDisplayObjects)event);
            for (int i = 0; i < updateEvent.getRectangleObjects().size(); i++) {
                disObj.add(new RectangleDisplayObject(programme, updateEvent.getRectangleObjects().get(i)));
            }
            for (int i = 0; i < updateEvent.getWallObjects().size(); i++) {
                disObj.add(new WallDisplayObject(programme, updateEvent.getWallObjects().get(i)));
            }
            for (int i = 0; i < updateEvent.getPlayerObjects().size(); i++) {
                disObj.add(new PlayerDisplayObject(programme, updateEvent.getPlayerObjects().get(i)));
                disObj.add(new HealthBarDisplayObject(programme, updateEvent.getPlayerObjects().get(i)));
            }
            for (int i = 0; i < updateEvent.getBallObjects().size(); i++) {
                disObj.add(new BallDisplayObject(programme, updateEvent.getBallObjects().get(i)));
            }
            ((GameScene)currentScene).updateGameObjects(disObj);
        } else if (event.getClass().equals(SwitchScene.class)) {
            SwitchScene sceneEvent = (SwitchScene)event;
            currentScene.destoryObjects();
            currentScene = sceneEvent.getScene();
        }
    }

    public void close() {
        running = false;
    }

}
