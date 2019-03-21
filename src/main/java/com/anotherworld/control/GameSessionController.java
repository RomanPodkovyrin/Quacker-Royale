package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.network.NetworkController;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.GameKeyListener;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Controller object that connects the View and the Model of the game.
 * @author Alfi S
 */
public class GameSessionController {

    private static Logger logger = LogManager.getLogger(GameSessionController.class);

    // Game Loop variables

    // FPS here means the number of game logic computation as second
    // desired FPS
    private final static int    MAX_FPS = 60;
    // maximum number of frames which are allowed to be dropped
    private final static int MAX_FRAME_DROP = 5;
    // the time between frames
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS; // 1000ms = 1s



    private GameSession session;
    private GameSettings settings;
    private View view;
    private GameKeyListener keyListener;
    private NetworkController network;


    /**
     *
     * @param view - The view for the current game
     * @param settings - GameSettings which represents the current game
     * @param network - Networking for the current game
     * @throws KeyListenerNotFoundException
     */
    public GameSessionController(View view, GameSettings settings, NetworkController network) throws KeyListenerNotFoundException {

        this.settings = settings;

        this.session = settings.createSession();
        this.view = view;

        // Starting the background music and effects threads
        AudioControl.setUp();
        AudioControl.playBackGroundMusic();

        // Sleeping the main thread for 1 second to register the key inputs.
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the key listener.
        this.keyListener = view.getKeyListener();

        // Setting up the network

        this.network = network;

        if (network != null) {
            network.setKeyListener(keyListener);
        }

        // Starting the main loop
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



        while (session.isRunning()) {

            // if client check if there are game objects to update
            network.clientControl(keyListener);


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
                logger.trace("Frames lost " + framesDropped);
                // updates the Game logic
                session.updatePlayer(keyListener.getKeyPresses());
                session.update();

                // updates the sleep time
                sleepTime += FRAME_PERIOD;
                framesDropped++;
            }

            // Reset dropped frames
            framesDropped = 0;

            // send game object to client
            network.hostControl();

        }

        shutDownSequence();
    }



    private void shutDownSequence() {

        logger.info("Initialising Shut down sequence");
        // TODO if network game then close down the connection

        //stop the music
        AudioControl.stopBackgroundMusic();
        logger.trace("Music stopped");
        AudioControl.stopSoundEffects();
        logger.trace("Stopped SoundEffects");

        network.stopNetworking();
        logger.trace("Stopped networking");
        //send out the message saying that either host or client have disconnected
        //if a client has disconnected should we just give control to the ai ?
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

    // TODO need key listener which would be sending the client key preses to host
}
