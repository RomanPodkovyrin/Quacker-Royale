package com.anotherworld.network;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.logic.Wall;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import com.anotherworld.tools.datapool.GameSessionData;

import java.util.ArrayList;

public class NetworkController {
    private GameClient client;
    private Server server;

    private Player currentPlayer;
    private ArrayList<Player> allPlayers;
    private ArrayList<Ball> balls;
    private Platform platform;
    private Wall wall;
    private GameSessionData gameSessionData;

    public NetworkController(GameClient client, Server server){
        //
    }

    public void sendKeyPress() {

    }





}
