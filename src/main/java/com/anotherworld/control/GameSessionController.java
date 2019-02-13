package com.anotherworld.control;

import com.anotherworld.model.logic.GameSession;
import com.anotherworld.model.movable.Player;
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


public class GameSessionController {
    
    private static Logger logger = LogManager.getLogger(GameSessionController.class);

    public static void main(String[] args) {
        try {
            View view = new View(800, 450);
            new GameSessionController(view);
        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
    }

    private GameSession session;
    private View view;
    private Thread viewThread;
    private KeyListener keyListener;
    
    private PlayerData currentPlayer;
    private ArrayList<PlayerData> networkPlayers;
    private ArrayList<PlayerData> ais;
    private ArrayList<BallData> balls;
    private ArrayList<PlatformData> platforms;
    private ArrayList<WallData> walls;

    //TODO make a constructor for the real main (Main.java)
    public GameSessionController(View view) throws KeyListenerNotFoundException {

        initDataPool();
        
        this.session = new GameSession(currentPlayer,networkPlayers,ais, balls, platforms.get(0), walls.get(0));
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
    
    private void initDataPool() {
        networkPlayers = new ArrayList<>();
        currentPlayer = new PlayerData("1", 100, 40, 45, null, 0.5f, 2);

        ais = new ArrayList<>();
        ais.add(new PlayerData("1", 100, 120, 45, null, 0.1f, 2));
        ais.add(new PlayerData("1", 100, 115, 30, null, 0.1f, 2));
        ais.add(new PlayerData("1", 100, 105, 20, null, 0.1f, 2));

        balls = new ArrayList<>();
        balls.add(new BallData(false, 80, 45, null, 0.1f, 3));

        platforms = new ArrayList<>();
        platforms.add(new PlatformData(80, 45));
        
        walls = new ArrayList<>();
        walls.add(new WallData(80, 45));
    }

    private void mainLoop() {
        render();
        while(viewThread.isAlive()) {
            update();

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
        session.update();
    }

    private void render() {
        ArrayList<PlayerData> players = new ArrayList<>();
        players.addAll(ais);
        players.addAll(networkPlayers);
        players.add(currentPlayer);
        view.updateGameObjects(players, balls, platforms, walls);
    }
}
