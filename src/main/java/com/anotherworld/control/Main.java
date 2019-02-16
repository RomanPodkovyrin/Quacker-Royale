package com.anotherworld.control;

import com.anotherworld.audio.BackgroundMusic;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main (String args[]) {
//        BackgroundMusic bm = new BackgroundMusic();
//        bm.playBackgroundMusic();
        //create view ??

        // Ask multi player or single player

        //set up single player
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
        }    }
}
