package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.*;
import com.anotherworld.model.ai.behaviour.player.*;
import com.anotherworld.model.ai.tools.Matrix;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * This class sets up all the jobs for AIs and takes care of running AI when told to do so.
 *
 * @author Roman P
 */
public class AI {

    private static Logger logger = LogManager.getLogger(AI.class);


    //private ArrayList<Job> jobs = new ArrayList<>();
    private ArrayList<Pair<Player,ArrayList<Player>>> aiPlayers = new ArrayList<>();
    private ArrayList<Player> allPlayers;
    private ArrayList<Ball> balls;
    private ArrayList<Job> jobs = new ArrayList<>();
    private Platform platform;

    private int tick = 0;

    private Matrix aiVector;
    private Matrix aiPosition;

    // is this supposed to be shared between ais or should they get one of their own?
    //###############################################################################
    private Job repeatJob = new RepeatSuccess((new WalkAbout()));


    /**
     * Used to set up the AI handler.
     *
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

            // Set up of the survival instincts
            ArrayList<Job> survival = new ArrayList<>();
            // TODO ai gets very jittery when getting close to the edge
//            survival.add(new AvoidEdge());
            survival.add(new AvoidBall());
            //survival.add(new AvoidPlayerCharge);


            // Set up of the domination skills
            ArrayList<Job> domination = new ArrayList<>();

            ArrayList<Job> aim = new ArrayList<>();
//            domination.add(new ChaseBall());
            ArrayList<Job> ballAim = new ArrayList<>();
            ballAim.add(new NeutralBallCheck());
            ballAim.add(new AimBall());

//            domination.add(new SequenceSuccess(ballAim));
            // when the ball was chased need to aim it as well

            // TODO chase the player gets the ai stuck
//            aim.add(new ChasePlayer());
//            aim.add(new Aim);
//            aim.add(new Charge);


//            domination.add(new SequenceSuccess(aim));

            // Set up of the Peaceful coexistence
//            ArrayList<Job> peaceful = new ArrayList<>();
//            peaceful.add(new WalkAbout());

            ArrayList<Job> routines = new ArrayList<>();
            routines.add(new SequenceSuccess(survival));
//            routines.add(new Selector(domination));
//            routines.add(new SequenceSuccess(ballAim));
            routines.add(new WalkAbout());

            Job tempj = new Repeat(new SequenceSuccess(routines));
            jobs.add(tempj);
            tempj.start();



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

        for (Player p : players) {
            if (!p.getCharacterID().equals(player.getCharacterID())) {
                newPlayers.add(p);
            }
        }

        return newPlayers;
    }

    /**
     * Is called when AI needs to make a decision based
     * on the current state of the game session.
     */
    public void action() {
        logger.info("AI action called.");

        if (tick == 0) {
            for (int i = 0; i < aiPlayers.size(); i++) {
                Pair<Player, ArrayList<Player>> pair = aiPlayers.get(i);
                logger.info(pair.getKey().getCharacterID() + " Starting AI");

                jobs.get(i).act(pair.getKey(), pair.getValue(), balls, platform);
            }
//            tick = tick + 1;
        } else if (tick == 4) {
            tick = 0;
        } else {
            tick = tick + 1;
        }
    }


}
