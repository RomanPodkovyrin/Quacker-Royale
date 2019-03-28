package com.anotherworld.view;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glGetError;

import static org.lwjgl.system.MemoryUtil.NULL;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.settings.DisplayType;
import com.anotherworld.settings.ViewSettings;
import com.anotherworld.tools.Action;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.GameKeyListener;
import com.anotherworld.tools.input.KeyBindings;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.data.BallDisplayData;
import com.anotherworld.view.data.BallDisplayObject;
import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.PlatformDisplayObject;
import com.anotherworld.view.data.PlayerDisplayData;
import com.anotherworld.view.data.PlayerDisplayObject;
import com.anotherworld.view.data.PowerUpDisplayObject;
import com.anotherworld.view.data.RectangleDisplayData;
import com.anotherworld.view.data.WallDisplayObject;
import com.anotherworld.view.graphics.GameScene;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.MenuScene;
import com.anotherworld.view.graphics.Scene;
import com.anotherworld.view.graphics.spritesheet.RectangleSpriteSheet;
import com.anotherworld.view.graphics.spritesheet.SpriteLocation;
import com.anotherworld.view.input.BindableKeyListener;
import com.anotherworld.view.input.StringKeyListener;
import com.anotherworld.view.programme.LegacyProgramme;
import com.anotherworld.view.programme.Programme;
import com.anotherworld.view.programme.ProgrammeUnavailableException;
import com.anotherworld.view.programme.TexturedProgramme;
import com.anotherworld.view.viewevent.ChangeWindowTitle;
import com.anotherworld.view.viewevent.MenuSwitch;
import com.anotherworld.view.viewevent.ReloadWindow;
import com.anotherworld.view.viewevent.SwitchBackground;
import com.anotherworld.view.viewevent.SwitchScene;
import com.anotherworld.view.viewevent.UpdateDisplayObjects;
import com.anotherworld.view.viewevent.ViewEvent;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Platform;

/**
 * Creates a window and manages the game's display.
 * 
 * @author Jake Stewart
 *
 */
public class View implements Runnable {

    private static Logger logger = LogManager.getLogger(View.class);

    private Optional<Long> window;

    private Scene currentScene;

    private CountDownLatch keyListenerLatch;

    private volatile boolean running;

    private int height;

    private int width;

    private Queue<ViewEvent> eventQueue;

    private Programme programme;
    
    private MenuScene menuScene;
    
    private DisplayType displayType;

    private int refreshRate;
    
    private boolean enableFrameCounter;
    
    private BindableKeyManager keyManager;
    
    private Thread keyManagerThread;

    /**
     * Creates the View object initialising it's values.
     */
    public View() {
        logger.info("Creating view");
        eventQueue = new LinkedList<>();
        keyListenerLatch = new CountDownLatch(1);
        running = false;
        menuScene = new MenuScene();
        window = Optional.empty();
        enableFrameCounter = true;
        logger.info("Running view");
    }

    /**
     * Returns the key listener for the view's window.
     * 
     * @return The keyListener
     * @throws KeyListenerNotFoundException If the keyListener couldn't be retrieved
     *                                      in 10 seconds
     */
    public GameKeyListener getKeyListener() throws KeyListenerNotFoundException {
        logger.info("Request for key listener objected");
        try {
            if (keyListenerLatch.await(10, TimeUnit.SECONDS)) {
                return new GameKeyListener(window.get(), new KeyBindings());
            }
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        throw new KeyListenerNotFoundException("Timeout of 10 seconds, check if window was initialized");
    }

    /**
     * Stages new objects to be used for the game display.
     * 
     * @param playerObjects    The new player objects.
     * @param ballObjects      The new ball objects.
     * @param rectangleObjects The new platform objects.
     * @param wallObjects      The new wall objects.
     */
    public void updateGameObjects(ArrayList<? extends PlayerDisplayData> playerObjects,
                                  ArrayList<? extends BallDisplayData> ballObjects,
                                  ArrayList<? extends RectangleDisplayData> rectangleObjects,
                                  ArrayList<? extends WallData> wallObjects,
                                  GameSessionData gameSessionData) {
        synchronized (eventQueue) {
            eventQueue.add(new UpdateDisplayObjects(playerObjects, ballObjects, rectangleObjects, wallObjects, gameSessionData));
        }
    }
    
    public boolean shouldShowFramerate() {
        return enableFrameCounter;
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
        
        loadWindow();

        if (window == null) {
            logger.fatal("Unable to create game window");
            attemptDestroy();
            throw new RuntimeException("Couldn't create glfw window");
        }

        glfwMakeContextCurrent(window.get());

        GL.createCapabilities();

        currentScene = new Scene();

        try {
            programme = new TexturedProgramme("src/main/glsl/com/anotherworld/view/shaders/texture/", window.get());
        } catch (ProgrammeUnavailableException e1) {
            logger.catching(e1);
            logger.warn("Couldn't start Textured programme renderer");
            try {
                programme = new TexturedProgramme("src/main/glsl/com/anotherworld/view/shaders/core/", window.get());
            } catch (ProgrammeUnavailableException e2) {
                logger.catching(e2);
                logger.warn("Couldn't start core programme renderer");
                try {
                    programme = new LegacyProgramme(window.get());
                } catch (ProgrammeUnavailableException e3) {
                    logger.catching(e3);
                    logger.fatal("Couldn't start any rendering engines");
                    attemptDestroy();
                    throw new RuntimeException("Couldn't start any rendering program");
                }
            }
        }

        keyListenerLatch.countDown();
        
        keyManager = new BindableKeyManager(new BindableKeyListener(window.get()));
        keyManagerThread = new Thread(keyManager);
        keyManagerThread.start();

        int error = glGetError();

        while (error != GL_NO_ERROR) {
            logger.error("Initialise GL error " + error);
            error = glGetError();
        }

        while (!glfwWindowShouldClose(window.get()) && running) {

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

            glfwSwapBuffers(window.get());

            logger.trace("Polling for glfw events");

            glfwPollEvents();

        }
        attemptDestroy(programme);
    }
    
    /**
     * Switches from the current scene to the game scene.
     */
    public void switchToGameScene() {
        synchronized (eventQueue) {
            eventQueue.add(new SwitchScene(new GameScene()));
        }
    }

    /**
     * Switches from the current menu display to the selected scene.
     * @param display the display to switch to
     */
    public void switchToDisplay(GraphicsDisplay display) {
        synchronized (eventQueue) {
            logger.info("Scene switch queued");
            eventQueue.add(new MenuSwitch(display));
            eventQueue.add(new SwitchScene(menuScene));
        }
    }

    private void attemptDestroy() {
        logger.info("Closing window");
        keyListenerLatch = new CountDownLatch(1);
        running = false;
        window = Optional.empty();
        glfwTerminate();
    }

    private void attemptDestroy(Programme programme) {
        logger.info("Closing window");
        keyListenerLatch = new CountDownLatch(1);
        programme.destroy();
        running = false;
        window = Optional.empty();
        AudioControl.stopBackgroundMusic();
        AudioControl.stopSoundEffects();
        keyManager.close();
        keyManagerThread.interrupt();
        glfwTerminate();
        waitForExit();
    }
    
    private void waitForExit() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.exit(0);
        while (threadSet.size() > 5) {
            int i = 0;
            for (Thread thread : threadSet) {
                logger.info("Thread " + i++);
                for (StackTraceElement trace : thread.getStackTrace()) {
                    logger.info(trace.toString());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void completeEvent(ViewEvent event) {
        logger.debug("Completing view event of type " + event.getClass());
        if (event.getClass().equals(UpdateDisplayObjects.class) && currentScene.getClass().equals(GameScene.class)) {
            ArrayList<DisplayObject> disObj = new ArrayList<>();
            UpdateDisplayObjects updateEvent = ((UpdateDisplayObjects) event);
            for (int i = 0; i < updateEvent.getRectangleObjects().size(); i++) {
                disObj.add(new PlatformDisplayObject(programme, updateEvent.getRectangleObjects().get(i)));
            }
            for (int i = 0; i < updateEvent.getWallObjects().size(); i++) {
                disObj.add(new WallDisplayObject(programme, updateEvent.getWallObjects().get(i)));
            }
            for (int i = 0; i < updateEvent.getGameSessionData().getPowerUpSchedule().size(); i++) {
                disObj.add(new PowerUpDisplayObject(programme, updateEvent.getGameSessionData().getPowerUpSchedule().get(i)));
            }
            for (int i = 0; i < updateEvent.getPlayerObjects().size(); i++) {
                disObj.add(new PlayerDisplayObject(programme, updateEvent.getPlayerObjects().get(i), i == 0));
            }
            for (int i = 0; i < updateEvent.getBallObjects().size(); i++) {
                disObj.add(new BallDisplayObject(programme, updateEvent.getBallObjects().get(i)));
            }
            ((GameScene)currentScene).updateGameObjects(disObj);
            logger.debug("Adding Objects");
        } else if (event.getClass().equals(SwitchScene.class)) {
            SwitchScene sceneEvent = (SwitchScene) event;
            if (currentScene.getClass().equals(GameScene.class)) {
                ((GameScene)currentScene).destroyObjects();
            }
            currentScene = sceneEvent.getScene();
            logger.debug("Switching scene");
        } else if (event.getClass().equals(MenuSwitch.class)) {
            MenuSwitch menuSwitch = (MenuSwitch) event;
            menuScene.changeMenuDisplay(menuSwitch.getDisplay());
        } else if (event.getClass().equals(ChangeWindowTitle.class)) {
            ChangeWindowTitle windowTitle = (ChangeWindowTitle) event;
            glfwSetWindowTitle(window.get(), windowTitle.getTitle());
            logger.debug("Window title changed to " + windowTitle.getTitle());
        } else if (event.getClass().equals(ReloadWindow.class)) {
            loadWindow();
            logger.debug("Window reloaded");
        } else if (event.getClass().equals(SwitchBackground.class)) {
            SwitchBackground switchBackground = (SwitchBackground) event;
            currentScene.switchBackgroundImage(switchBackground.getBackground());
            logger.debug("Background switched");
        } else {
            logger.warn("Unexpected view event was created " + event.getClass());
        }
    }
    
    private void loadWindow() {
        this.height = ViewSettings.getHeight();
        this.width = ViewSettings.getWidth();
        this.displayType = ViewSettings.getDisplayType();
        this.refreshRate = ViewSettings.getRefreshRate();
        if (!window.isPresent()) {
            window = Optional.of(glfwCreateWindow(width, height, "Bullet Hell", NULL, NULL));
            glfwSetWindowAttrib(window.get(), GLFW_RESIZABLE, GLFW_FALSE);
            glfwSetWindowSizeCallback(window.get(), (window, width, height) -> {
                if (window == this.window.get()) {
                    this.width = width;
                    this.height = height;
                }
            });
        }
        PointerBuffer monitors = glfwGetMonitors();
        if (displayType.equals(DisplayType.WINDOWED)) {
            glfwSetWindowMonitor(window.get(), NULL, 0, 0, width, height, refreshRate);
            glfwSetWindowSize(window.get(), width, height);
        } else {
            glfwSetWindowMonitor(window.get(), monitors.get(), 0, 0, width, height, refreshRate);
            //glfwSetWindowSize(window.get(), width, height);
        }
        IntBuffer windowHeight = BufferUtils.createIntBuffer(1);
        IntBuffer windowWidth = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(window.get(), windowWidth, windowHeight);
        if (displayType.equals(DisplayType.FULLSCREEN)) {
            width = windowWidth.get();
            height = windowHeight.get();
        }
    }

    public boolean gameRunning() {
        return running && currentScene.getClass().equals(GameScene.class);
    }

    public void close() {
        running = false;
    }

    /**
     * Adds an event to the queue that changes the window title.
     * @param title The new title for the window
     */
    public void setTitle(String title) {
        synchronized (eventQueue) {
            eventQueue.add(new ChangeWindowTitle(title));
        }
    }
    
    /**
     * Switches the view background with the sprite from the selected one.
     * @param location the new background
     */
    public void switchBackground(SpriteLocation location) {
        synchronized (eventQueue) {
            switch (location) {
                case INSTRUCTIONS:
                    eventQueue.add(new SwitchBackground(new RectangleSpriteSheet(location, 1, 1000)));
                    break;
                default:
                    eventQueue.add(new SwitchBackground(new RectangleSpriteSheet(SpriteLocation.BACKGROUND, 10, 1000f / 12)));
            }
        }
    }

    /**
     * Returns a key listener that collects text field input.
     * @param start The initial text field text
     * @return the key listener
     * @throws KeyListenerNotFoundException If a keylistener couldn't be created for the window
     */
    public StringKeyListener getStringKeyListener(String start) throws KeyListenerNotFoundException {
        logger.info("Request for key listener objected");
        try {
            if (keyListenerLatch.await(10, TimeUnit.SECONDS)) {
                return new StringKeyListener(window.get(), start);
            }
        } catch (InterruptedException e) {
            logger.catching(e);
        }
        throw new KeyListenerNotFoundException("Timeout of 10 seconds, check if window was initialized");
    }

    public boolean windowOpen() {
        return running;
    }
    
    /**
     * Performs the given action using the next bindable key.
     * @param whatToDo the action to perform
     */
    public void getBindableKey(Action<Integer> whatToDo) {
        cancelWaitingKeys();
        keyManager.queue(whatToDo);
    }
    
    public void cancelWaitingKeys() {
        keyManager.clear();
    }

    /**
     * Adds an event to the queue to reload the window.
     */
    public void reloadWindow() {
        synchronized (eventQueue) {
            logger.info("Reload window queued");
            eventQueue.add(new ReloadWindow());
        }
    }

}
