package com.anotherworld.control;

import com.anotherworld.model.movable.Ball;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.PlatformData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.datapool.WallData;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main (String args[]) {
        //create view ??

        // Ask multi player or single player

        //set up single player
        GameSettings settings = new GameSettings(2,1,1, true, true);

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
