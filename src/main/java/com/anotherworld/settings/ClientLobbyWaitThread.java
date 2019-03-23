package com.anotherworld.settings;

import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.input.ButtonListener;

public class ClientLobbyWaitThread implements Runnable {
    
    private boolean waitingForGame;
    private boolean gameStarted;
    private Supplier<Boolean> shouldStart;
    private ButtonListener gameStart;
    
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
