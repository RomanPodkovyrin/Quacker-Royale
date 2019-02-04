package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.view.View;
import com.anotherworld.view.input.KeyListener;

public class GameSessionController {

    public static void main(String[] args) {
        new GameSessionController();
    }

    private static boolean isRunning;
    private GameSession session;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;

    public GameSessionController() {

        isRunning = true;
        //this.session = new GameSession(null, null);
        this.view = new View();
        this.viewThread = new Thread(view);
        viewThread.start();
        System.out.println("Thread started");
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
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
