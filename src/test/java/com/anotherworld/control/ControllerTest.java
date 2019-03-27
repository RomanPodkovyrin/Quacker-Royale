package com.anotherworld.control;

import com.anotherworld.tools.exceptions.ConnectionClosed;
import com.anotherworld.tools.exceptions.NoHostFound;
import com.anotherworld.view.View;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class ControllerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void ControllerTest() {
        Controller control = new Controller(new View());
        assertTrue(!control.clientCancel());
        assertTrue(!control.getServerStarted());
        assertTrue(!control.hostStartTheGame());
        control.hostCancelTheGame();
        assertNotNull(control.getPlayersIPaddresses());
        control.startSinglePlayer();
        assertTrue(!control.hostStartTheGame());


    }

    @Test//(expectedExceptions = {NoHostFound.class ,ConnectionClosed.class})
    public void ConnectionTest() throws ConnectionClosed, NoHostFound {
        thrown.expect(ConnectionClosed.class);
        Controller control = new Controller(new View());
        control.connect("1");
    }



}
