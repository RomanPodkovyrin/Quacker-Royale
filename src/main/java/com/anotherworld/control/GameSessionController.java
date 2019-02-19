package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.KeyListener;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller object that connects the View and the Model of the game.
 * @author Alfi S
 */
public class GameSessionController {
    // Game Loop variables

    // FPS here means the number of game logic computation as second
    // desired FPS
    private final static int    MAX_FPS = 60;
    // maximum number of frames which are allowed to be dropped
    private final static int MAX_FRAME_DROP = 5;
    // the time between frames
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS; // 1000ms = 1s

    private static Logger logger = LogManager.getLogger(GameSessionController.class);

    private GameSession session;
    private GameSettings settings;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;




    //TODO make a constructor for the real main (Main.java)
    public GameSessionController(View view, GameSettings settings) throws KeyListenerNotFoundException {

        this.settings = settings;

        this.session = settings.createSession();
        this.view = view;

        AudioControl.setUp();
        Boolean startMusic = true;
        try {
            PropertyReader sound = new PropertyReader("gamesession.properties");
            if (sound.getValue("backgroundMusic").equals("off")){
                startMusic = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (startMusic) {
            AudioControl.playBackGroundMusic();
        }


        // Starting the View thread
        this.viewThread = new Thread(view);
        viewThread.start();

        // Sleeping the main thread for 1 second to register the key inputs.
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the key listener.
        this.keyListener = view.getKeyListener();
        mainLoop();

    }

    private void mainLoop() {
        render();

        int framesDropped = 0;

        // Time at the start of the loop
        long startTime;
        // Time it took for gameLogic
        long delta;
        // Time in ms to sleep
        int sleepTime = 0;



        while (viewThread.isAlive()) {

            // Get time before computation
            startTime = System.currentTimeMillis();

            // Update Game Logic
            session.updatePlayer(keyListener.getKeyPresses());
            session.update();

            // Work out the time it took for logic
            delta = System.currentTimeMillis() - startTime;

            // Calculate the time the system can sleep for
            sleepTime = (int)(FRAME_PERIOD - delta);

            //Check if logic tool too long
            if (sleepTime > 0) {
                // Tell system to sleep
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
                logger.trace("No frames lost");
            }
            // Otherwise render straight away


            // Fixes dropped frameRate
            while ((sleepTime < 0) && (framesDropped < MAX_FRAME_DROP)) {
                logger.trace("Frames lost");
                // updates the Game logic
                session.updatePlayer(keyListener.getKeyPresses());
                session.update();

                // updates the sleep time
                sleepTime += FRAME_PERIOD;
                framesDropped++;
            }

            // Reset dropped frames
            framesDropped = 0;
        }

        shutDownSequence();
    }

    private void shutDownSequence() {
        logger.debug("Initialising Shut down sequence");
        //stop the music
        AudioControl.stopBackgroundMusic();
        logger.trace("Music stopped");
        AudioControl.stopSoundEffects();
        logger.trace("Stopped SoundEffects");
        //send out the message saying that either host or client have disconnected
        //if a client has disconnected should we just give control to the ai ?
    }

    private void networking() {

        // if client
        //check if got any new packages
        // implements queue from which it updates the current game state

        // if server
        // check for the key pressed and update the appropriate player object
    }

    private void render() {
        ArrayList<PlayerData> players = new ArrayList<>();
        players.addAll(settings.getAi());
        players.add(settings.getCurrentPlayer());
        players.addAll(settings.getPlayers());
        view.updateGameObjects(players,
                               settings.getBalls(),
                               settings.getPlatform(),
                               settings.getWall());
    }
}
