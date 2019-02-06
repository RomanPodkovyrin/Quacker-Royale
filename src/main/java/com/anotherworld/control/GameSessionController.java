package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.view.View;

import com.anotherworld.tools.input.KeyListener;
import com.anotherworld.tools.input.KeyListenerNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GameSessionController {
    
    private static Logger logger = LogManager.getLogger(GameSessionController.class);

    public static void main(String[] args) {
        try {
            new GameSessionController();
        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
        }
    }

    private GameSession session;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;

    public GameSessionController(View view, GameSession session) throws KeyListenerNotFoundException {

        this.session = session;
        this.view = view;

        // Starting the View thread
        this.viewThread = new Thread(view);
        viewThread.start();

        // Sleeping the main thread for 1 second to register the key inputs.
        try { Thread.sleep(1000); }
        catch (Exception e){ e.printStackTrace(); }

        // Obtain the key listener.
        this.keyListener = view.getKeyListener();
        mainLoop();

        //Clean up ie close connection if there are any and close the graphics window

    }

    private void mainLoop() {
        while(viewThread.isAlive()) {
            update();
            render();

            try{
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    private void update() {

        // Send the input key presses to the model.
        session.updatePlayer(keyListener.getKeyPresses());

    }

    private static void render() {
        //View.update
    }
}
