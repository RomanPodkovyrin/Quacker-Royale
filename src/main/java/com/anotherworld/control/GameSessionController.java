package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.view.View;
import com.anotherworld.view.input.KeyListener;
import com.anotherworld.view.input.KeyListenerNotFoundException;

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

    private static boolean isRunning;
    private GameSession session;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;

    public GameSessionController() throws KeyListenerNotFoundException {

        isRunning = true;
        //this.session = new GameSession(null, null);
        this.view = new View();
        this.viewThread = new Thread(view);
        viewThread.start();
        System.out.println("Thread started");
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

        //GameSession.update
        if (this.keyListener.isUpPressed()) System.out.println("UP");
    }

    private static void render() {

    }
}
