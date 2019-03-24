package com.anotherworld.control;

import com.anotherworld.network.NetworkControllerSinglePlayer;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import org.junit.Test;

public class GameSessesionControllerTest {

    @Test
    public void testControler() {
        GameSettings settings = new GameSettings(1,0,3);
        try {
            GameSessionController controller = new GameSessionController(new View(100,100),settings,new NetworkControllerSinglePlayer());
        } catch (KeyListenerNotFoundException e) {
            e.printStackTrace();
        }

    }
}
