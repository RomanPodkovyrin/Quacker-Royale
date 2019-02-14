package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.view.View;

import com.anotherworld.tools.input.KeyListener;
import com.anotherworld.tools.input.KeyListenerNotFoundException;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Controller object that connects the View and the Model of the game.
 * @author Alfi S
 */
public class GameSessionController {
    
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

        // Starting the View thread
        this.viewThread = new Thread(view);
        viewThread.start();

        // Sleeping the main thread for 1 second to register the key inputs.
        try { Thread.sleep(1000); }
        catch (Exception e){ e.printStackTrace(); }

        // Obtain the key listener.
        this.keyListener = view.getKeyListener();
        mainLoop();

    }

    private void mainLoop() {
        render();

        while(viewThread.isAlive()) {
            session.updatePlayer(keyListener.getKeyPresses());
            session.update();

            try{
                Thread.sleep(0);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
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
