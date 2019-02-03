package com.anotherworld.control;

public class GameSessionController {

    private static boolean isRunning;

    public GameSessionController(){

        mainLoop();

        //Clean up ie close connection if there are any and close the graphics window

    }

    private static void mainLoop() {
        while(isRunning)
            update();
    }

    private static void update() {

        //GameSession.update

    }
}
