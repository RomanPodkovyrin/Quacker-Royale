package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.model.logic.GameSession;
import com.anotherworld.network.AbstractNetworkController;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.GameKeyListener;
import com.anotherworld.tools.input.Input;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller object that connects the View and the Model of the game.
 * @author Alfi S.
 * @author roman
 */
public class GameSessionController {


    private static Logger logger = LogManager.getLogger(GameSessionController.class);


    // FPS here means the number of game logic computation per second
    // desired FPS
    private static final int MAX_FPS = 60;
    // maximum number of frames which are allowed to be dropped
    private static final int MAX_FRAME_DROP = 5;
    // the time between frames
    private static final int FRAME_PERIOD = 1000 / MAX_FPS; // 1000ms = 1s

    // Game Loop variables
    private GameSession session;
    private GameSettings settings;
    private View view;
    private GameKeyListener keyListener;
    private AbstractNetworkController network;
    boolean keyDown;


    /**
     * Used to Start the game session.
     * @param view - The view for the current game
     * @param settings - GameSettings which represents the current game
     * @param network - Networking for the current game
     * @throws KeyListenerNotFoundException - if key listener could not be found
     */
    public GameSessionController(View view, GameSettings settings, AbstractNetworkController network) throws KeyListenerNotFoundException {

        this.settings = settings;

        this.session = settings.createSession();
        this.view = view;

        // Starting the background music and effects threads
        AudioControl.setUp();

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
        this.network.setGameSession(session);



        // Starting the main loop
        mainLoop();
        
    }


    /**
     * The main game loop.
     */
    private void mainLoop() {
        render();
        
        int framesDropped = 0;
        // Time at the start of the loop
        long startTime;
        // Time it took for gameLogic
        long delta;
        // Time in ms to sleep
        int sleepTime = 0;



        keyDown = false;

        while (view.gameRunning() && session.isRunning()) {

            if (userControl()) {
                break;
            }

            // if client check if there are game objects to update
            network.clientControl(keyListener);


            // Get time before computation
            startTime = System.currentTimeMillis();

            // Update Game Logic
            session.updateCurrentPlayer(keyListener.getKeyPresses());
            session.update();

            // Work out the time it took for logic
            delta = System.currentTimeMillis() - startTime;

            // Calculate the time the system can sleep for
            sleepTime = (int)(FRAME_PERIOD - delta);

            //Check if logic took too long
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
                session.updateCurrentPlayer(keyListener.getKeyPresses());
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

    /**
     * Reads used key presses to perform not character related controls.
     * @return true break the loop and quit the game , false continue loop
     */
    private boolean userControl() {
        //checks if player quit the game
        if (keyListener.getKeyPresses().contains(Input.QUIT)) {
            network.quitTheGame();
            return true;
        }

        // music and effect mute unmute control
        if (keyListener.getKeyPresses().contains(Input.MUTE)) {
            if (!keyDown) {
                System.out.println("Muting unmuting");
                AudioControl.muteUnmute();
                keyDown = true;
            }
        } else {
            keyDown = false;
        }
        return false;
    }

    /**
     * Plays a win sound or lose sound or nothing if the game was quit.
     */
    private void winOrLoseSound() {

        LinkedList<String> rankings = settings.getGameSessionData().getRankings();

        boolean allRanked = rankings.size() == settings.getPlayers().size() + 1 + settings.getAi().size();

        if (rankings.isEmpty() | !allRanked) {
            rankings.clear();
            logger.info("No winner");
        } else {

            String firstPlace = rankings.get(0);
            AudioControl.stopBackgroundMusic();

            if (firstPlace.equals(settings.getCurrentPlayer().getObjectID())) {
                logger.info("You won");
                AudioControl.win();


            } else {
                logger.info("You lost");
                AudioControl.lose();
            }

            // Sleeping the main thread to allow sound to play
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    
    public List<String> getRanking() {
        return settings.getGameSessionData().getRankings();
    }


    /**
     * Safely shuts down the system.
     */
    private void shutDownSequence() {

        logger.info("Initialising Shut down sequence");

        //stop audio
        AudioControl.stopBackgroundMusic();
        logger.info("Music stopped");

        //Stop networking
        network.stopNetworking();
        logger.info("Stopped networking");


        winOrLoseSound();
    }


    private void render() {

        ArrayList<PlayerData> players = new ArrayList<>();
        players.add(settings.getCurrentPlayer());
        players.addAll(settings.getAi());
        players.addAll(settings.getPlayers());
        view.updateGameObjects(players,
                               settings.getBalls(),
                               settings.getPlatform(),
                               settings.getWall(),
                               settings.getGameSessionData());
    }
}
