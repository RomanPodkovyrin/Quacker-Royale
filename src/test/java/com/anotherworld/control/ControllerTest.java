package com.anotherworld.control;

import com.anotherworld.view.View;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class ControllerTest {

    @Test
    public void ControllerTest() {
        Controller control = new Controller(new View());
        assertTrue(!control.clientCancel());
        assertTrue(!control.getServerStarted());
        assertTrue(!control.hostStartTheGame());
        control.hostCancelTheGame();
        assertNotNull(control.getPlayersIPaddresses());
        control.startSinglePlayer();
        control.hostStartTheGame();

        Controller.musicSetting(false);
        Controller.musicSetting(true);
        Controller.sfxSetting(false);
        Controller.sfxSetting(true);

    }



}
