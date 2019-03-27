package com.anotherworld.tools.exceptions;

import org.junit.Test;


public class ExceptionsTests {

    @Test(expected = ConnectionClosed.class)
    public void ConnectionClosedExceptionTest() throws ConnectionClosed {
        Exception test = new ConnectionClosed();
        System.out.println(test);
        throw new ConnectionClosed();

    }

    @Test(expected = NoHostFound.class)
    public void NoHostFoundTest() throws NoHostFound {
        Exception test = new NoHostFound();
        System.out.println(test);
        throw new NoHostFound();
    }
}
