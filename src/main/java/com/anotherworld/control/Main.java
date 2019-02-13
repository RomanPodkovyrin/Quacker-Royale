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

import java.util.ArrayList;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main (String args[]) {
        //create view ??

        // Ask multi player or single player

        //set up single player
        GameSettings settings = new GameSettings(4,3,6, true, true);
        settings.createGameFiles();

        ArrayList<PlayerData> ais = settings.getAi();
        ArrayList<PlayerData> players = settings.getPlayers();
        System.out.println(players.toString());
        ArrayList<BallData> balls = settings.getBalls();
        PlatformData platform = settings.getPlatform();
        WallData wall = settings.getWall();
        //GameSessionData session = settings.getGameSession();

        try {
            View view = new View();

            new GameSessionController(view,players.get(0), new ArrayList<PlayerData>(), ais,balls,platform,wall );
            //new GameSessionController(ais,players,balls,platform,wall, session);
        } catch (KeyListenerNotFoundException ex) {
            logger.fatal(ex);
        } catch (RuntimeException ex) {
            logger.fatal(ex);
            ex.printStackTrace();
        }    }
}
