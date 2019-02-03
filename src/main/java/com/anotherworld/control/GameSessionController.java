package com.anotherworld.control;

public class GameSessionController {

    private static boolean isRunning;

    public GameSessionController() {

        isRunning = true;
        mainLoop();

        //Clean up ie close connection if there are any and close the graphics window

    }

    private static void mainLoop() {

        while(isRunning) {

            update();
            //render();
            try{
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private static void update() {

        //GameSession.update

    }
}
