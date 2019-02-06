package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.AvoidBall;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * @author Roman P
 */
public class AI {
    private ArrayList<Pair<Player,ArrayList<Player>>> aiPlayers = new ArrayList<>();
    private ArrayList<Player> players;
    private ArrayList<Ball> balls;
    private Platform platform;

    private Matrix aiVector;
    private Matrix aiPosition;


    /**
     *
     * @param players the rest of the players on the map(user and ai controlled)
     * @param balls all the balls on the map
     * @param platform the current platform
     */
    public AI(ArrayList<Player> ais, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        this.players = players;
        this.balls = balls;
        this.platform = platform;

        for (Player ai : ais) {
            aiPlayers.add(new Pair<>(ai, removePlayer(players,ai)));
        }
    }

    private ArrayList<Player> removePlayer(ArrayList<Player> players, Player player) {
        ArrayList<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            Player currentPlayer = players.get(i);
            if (!currentPlayer.getCharacterID().equals(player.getCharacterID())) {
                newPlayers.add(currentPlayer);
            }
        }

        return newPlayers;
    }

    /**
     * Is called when AI needs to make a decision based
     * on the current state of the game session
     */
    public void action(){

    }


}
