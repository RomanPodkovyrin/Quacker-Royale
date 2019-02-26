package com.anotherworld.network;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.tools.input.Input;
import com.anotherworld.tools.input.KeyListener;

import java.io.IOException;
import java.util.ArrayList;

public class NetworkController {
    private GameClient client;
    private Server server;
    private KeyListener keyListener;

    private GameSettings settings;

    public NetworkController(GameClient client, GameSettings settigs) {
        this.client = client;
        this.settings = settigs;

    }

    public NetworkController(Server server, GameSettings settings) {
        this.server = server;
        this.settings = settings;

    }

    public boolean isClient() {
        return client!=null;
    }

    public boolean isServer() {
        return server!=null;
    }


    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void sendKeyPress() {

    }

    public void clientControl() {

        if (isClient()) {

            // Get all the game objects from server and update all the game objects accordingly




            // TODO implement client side of the network

            // send the given key presses to the host
            ArrayList<Input> keyPresses = keyListener.getKeyPresses();
            if (keyPresses.contains(Input.CHARGE)) {
                //TODO: Implement charge action.
            } else {
                if (keyPresses.contains(Input.UP)) {
                    //
                } else if (keyPresses.contains(Input.DOWN)) {
                    //
                } else {
                    //
                }

                if (keyPresses.contains(Input.LEFT)) {

                } else if (keyPresses.contains(Input.RIGHT)) {
                    //
                } else {
                    //
                }
            }

        }
        //TODO check if there are any new objects to update
        // gets all the objects send from host and updates the current reference
    }

    public void hostControl() {
        if (isServer()) {
            // TODO implement host game state sender
            // Regardless of the situation send all the game object to clients



            // Get all the button pressed from clients and update game objects accordingly

            // Should i be done before sending the objects ?

        }
    }




}
