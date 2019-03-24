package com.anotherworld.control.exceptions;

import org.junit.Test;


public class ExceptionsTests {

    @Test(expected = ConnectionClosed.class)
    public void ConnectionClosedExceptionTest() throws ConnectionClosed {
        throw new ConnectionClosed();

    }

    @Test(expected = NoHostFound.class)
    public void NoHostFoundTest() throws NoHostFound {
        throw new NoHostFound();
    }
}
