package com.anotherworld.control;

import com.anotherworld.audio.BackgroundMusic;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.MenuDemo;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Main {
    private MenuDemo view;

    private static Logger logger = LogManager.getLogger(Main.class);

//    public static void main (String args[]) {
//        Main main = new Main();
//        main.startTheGame();
//        MenuDemo viewMenu = new MenuDemo(this);
//
//    }

    public void add(MenuDemo view) {
        this.view = view;
    }

    public Main() {

    }

    
    public void startTheGame() {
        BackgroundMusic bm = new BackgroundMusic();
        bm.playBackgroundMusic();

        GameSettings settings = new GameSettings(4,3,4, true, true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(screenSize.getWidth() + " * " + screenSize.getHeight());

        try {
            View view = new View((int )screenSize.getWidth(),(int) screenSize.getHeight());

            new GameSessionController(view, settings);

        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }
    }

    public void startMultiplayer(boolean host) {
        if (host) {
            // establish the connection
            // wait for people to connect
            // tell when to start the game
            // finds how many people connected
            startTheGame();
            // when all players have been created
            // allocate the player id to the ip address so network knows who to send it to
        } else {
            // find the host and connect
        }
    }

    public void startSinglePlayer() {
        startTheGame();
    }

    public void sfxSetting(boolean on) {
        //TODO: this function is to enable the sound effect before the game start.
    }
    
    public void musicSetting(boolean on) {
        //TODO: this function is to enable the music before the game start.
    }


}
