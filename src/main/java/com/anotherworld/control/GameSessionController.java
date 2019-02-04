package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.Player;
import com.anotherworld.view.View;
import com.anotherworld.view.input.KeyListener;

import java.util.ArrayList;

public class GameSessionController {

    private GameSession session;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;

    public GameSessionController(View view, GameSession session) {

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

        //GameSession.update
        if (this.keyListener.isUpPressed()) System.out.println("UP");
    }

    private static void render() {
        //View.update
    }

    public static void main(String[] args) {
        //Create the players
        Player ourPlayer = new Player("Us", 3,10,10,null, false);
        ArrayList<Player> players = new ArrayList<>();
        players.add(ourPlayer);
        ArrayList<Player> ais = new ArrayList<>();

        //Create the game view and models
        View gameView = new View();
        GameSession gameSession = new GameSession(ourPlayer, null, null);

        new GameSessionController(gameView, gameSession);
    }
}
