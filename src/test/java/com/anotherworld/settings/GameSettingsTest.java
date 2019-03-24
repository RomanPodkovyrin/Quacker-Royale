package com.anotherworld.settings;

import com.anotherworld.network.Server;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.*;

@RunWith(Parameterized.class)
public class GameSettingsTest {
    private final int numberOfPlayers;
    private final int numberOfAI;
    private final int numberOfBalls;
    private final boolean isServer;
    private final boolean isClient;
    private final int expectedNumberOfPlayers;
    private final int expectedNumberOfAI;
    private final int expectedNumberOfBalls;
    private final int expectedNumberOfPlatforms;
    private final int expectedNumberOfWalls;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1,0,2,true,false},
                {1,0,2,false,true},
                {1,0,2,false,false},
                {1,0,2,true,true},
                {0,0,0,false,false},
                {1,0,0,false,false},
                {3,2,0,false,false},
                {3,2,1,false,false},
        });
    }
    public GameSettingsTest(int numberOfPlayers,int numberOfAI, int numberOfBalls,
                            boolean isServer, boolean isClient){
        this.numberOfPlayers =numberOfPlayers;
        this.numberOfAI = numberOfAI;
        this.numberOfBalls = numberOfBalls;
        this.isServer = isServer;
        this.isClient = isClient;

        int players = numberOfPlayers - numberOfAI - 1; // 1 is the current player
        if (players < 0) {
            players = 0;
        }
        this.expectedNumberOfPlayers = players;
        this.expectedNumberOfAI = numberOfAI;
        this.expectedNumberOfBalls = numberOfBalls;
        this.expectedNumberOfPlatforms = 1;
        this.expectedNumberOfWalls = 1;

    }

    @Test
    public void settingTest() {

        GameSettings settings = new GameSettings(numberOfPlayers,numberOfAI,numberOfBalls);


//        if (isClient) {
//            try {
//                settings.setClient(new GameClient("127.0.0.1"));
//                assertNotNull(settings.getClient());
//                settings.getClient().stopClient();
//            } catch (SocketException e) {
//                e.printStackTrace();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            }
//
//        }

        assertEquals(expectedNumberOfPlayers, settings.getPlayers().size());
        assertEquals(expectedNumberOfAI, settings.getAi().size());
        assertEquals(expectedNumberOfBalls,settings.getBalls().size());
        assertEquals(expectedNumberOfPlatforms,settings.getPlatform().size());
        assertNotNull(settings.getGameSessionData());
        assertEquals(expectedNumberOfWalls,settings.getWall().size());
        if (numberOfPlayers > 0) {
            assertNotNull(settings.getCurrentPlayer());
        }
        assertNotNull(settings.getDefaultPlayerSpeed());
        assertNotNull(settings.getDefaultPlayerMaxCharge());
        assertNotNull(settings.getBallMaxTimer());
        assertNotNull(settings.getBallTimerDecrement());
        assertNotNull(settings.getDefaultBallSpeed());
        assertNotNull(settings.getDefaultBallDamage());

    }
}
