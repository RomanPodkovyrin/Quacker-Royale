package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @author Roman P
 */
public class AI {

    private static Logger logger = LogManager.getLogger(AI.class);


    //private ArrayList<Job> jobs = new ArrayList<>();
    private ArrayList<Pair<Player,ArrayList<Player>>> aiPlayers = new ArrayList<>();
    private ArrayList<Player> allPlayers;
    private ArrayList<Ball> balls;
    private Platform platform;

    private Matrix aiVector;
    private Matrix aiPosition;

    // is this supposed to be shared between ais or should they get one of their own?
    //###############################################################################
    private Job repeatJob = new Repeat((new AvoidBall()));


    /**
     * @param ais All the ai players
     * @param allPlayers the rest of the allPlayers on the map(user and ai controlled)
     * @param balls all the balls on the map
     * @param platform the current platform
     */
    public AI(ArrayList<Player> ais, ArrayList<Player> allPlayers, ArrayList<Ball> balls, Platform platform) {
        this.allPlayers = allPlayers;
        this.balls = balls;
        this.platform = platform;

        for (Player ai : ais) {
            aiPlayers.add(new Pair<>(ai, removePlayer(allPlayers,ai)));
        }

        repeatJob.start();
        logger.debug("AI initialisation is done");
    }

    /**
     * Takes a list of players and removes the specified player object from that list.
     *
     * @param players list of all players
     * @param player the player to be removed
     * @return returns the new list of players with the removed player
     */
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
     * on the current state of the game session.
     */
    public void action(){


        for (Pair<Player,ArrayList<Player>> pair: aiPlayers) {
            logger.info(pair.getKey().getCharacterID() + " Starting AI");

            repeatJob.act(pair.getKey(), pair.getValue(),balls,platform);
        }

    }


}
