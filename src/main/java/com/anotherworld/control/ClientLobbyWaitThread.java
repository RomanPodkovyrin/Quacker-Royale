package com.anotherworld.control;

import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.input.ButtonListener;

public class ClientLobbyWaitThread implements Runnable {
    
    private boolean waitingForGame;
    private boolean gameStarted;
    private Supplier<Boolean> shouldStart;
    private ButtonListener gameStart;
    
    /**
     * Creates a runnable object that manages when a client should start the game.
     * @param shouldStart Returns if the client should start
     * @param gameStart Performs the action on game start
     */
    public ClientLobbyWaitThread(Supplier<Boolean> shouldStart, ButtonListener gameStart) {
        waitingForGame = true;
        gameStarted = false;
        this.shouldStart = shouldStart;
        this.gameStart = gameStart;
    }

    @Override
    public void run() {
        while (waitingForGame) {
            try {
                Thread.sleep(100);
                if (shouldStart.get()) {
                    gameStarted = true;
                    waitingForGame = false;
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (gameStarted) {
            gameStart.clicked();
        }
    }

    public void reset() {
        waitingForGame = true;
        gameStarted = false;
    }
    
    public void cancleWait() {
        waitingForGame = false;
    }

}
