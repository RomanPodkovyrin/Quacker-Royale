package com.anotherworld.settings;

import com.anotherworld.model.ai.tools.Line;
import com.anotherworld.model.ai.tools.Matrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.*;

@RunWith(Parameterized.class)
public class GameSettingsTest {
    private final int numberOfPlayers;
//    private final int numberOfAI;
//    private final int numberOfBalls;
//    private final int expectedNumberOfPlayers;
//    private final int expectedNumberOfAI;
//    private final int expectedNumberOfPlatforms;
//    private final int expectedNumberOfWalls;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new Line(new Matrix(-4.0f, -4.0f),new Matrix(3.0f, 2.0f)), new Matrix(5,6), -3.328f}
        });
    }
    public GameSettingsTest(int numberOfPlayers, Matrix point, float expected){
        this.numberOfPlayers =numberOfPlayers;

    }

    @Test
    public void settingTest() {
        GameSettings settings = new GameSettings(2,1,2);
        assertEquals(0,settings.getPlayers().size());
        assertNotNull(settings.getCurrentPlayer());
        assertEquals(2,settings.getBalls().size());

    }
}
