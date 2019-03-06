package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.Inverter;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.behaviour.Selector;
import com.anotherworld.model.ai.behaviour.Sequence;
import com.anotherworld.model.ai.behaviour.SequenceSuccess;
import com.anotherworld.model.ai.behaviour.player.AimBall;
import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.ai.behaviour.player.AvoidEdge;
import com.anotherworld.model.ai.behaviour.player.AvoidNeutralPlayer;
import com.anotherworld.model.ai.behaviour.player.ChaseBall;
import com.anotherworld.model.ai.behaviour.player.ChasePlayer;
import com.anotherworld.model.ai.behaviour.player.CheckIfSaveToGo;
import com.anotherworld.model.ai.behaviour.player.NeutralBallCheck;
import com.anotherworld.model.ai.behaviour.player.WalkAbout;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class sets up all the jobs for AIs and takes care of running AI when told to do so.
 *
 * @author Roman P
 */
public class AI {

    private static Logger logger = LogManager.getLogger(AI.class);

    private ArrayList<Pair<Player,ArrayList<Player>>> aiPlayers = new ArrayList<>();
    private ArrayList<Player> allPlayers;
    private ArrayList<Ball> balls;
    private ArrayList<Job> jobs = new ArrayList<>();
    private Platform platform;

    private int tick = 0;

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

        // Gives all AIs their individual jobs
        for (Player ai : ais) {
            // Gives the AI representation of other players(AIs and human players)
            aiPlayers.add(new Pair<>(ai, removePlayer(allPlayers,ai)));

            // Setting up domination and peace combined list
            ArrayList<Job> dominationAndPeace = new ArrayList<>();
            dominationAndPeace.add(new Selector(getDomination()));
            dominationAndPeace.add(new SequenceSuccess(getPeace()));

            // Setting up the extra check if the given action can be done to avoid the ball
            ArrayList<Job> extra = new ArrayList<>();
            extra.add(new SequenceSuccess(dominationAndPeace));
            extra.add(new CheckIfSaveToGo());


            // Setting up the main routine
            ArrayList<Job> routines = new ArrayList<>();
            routines.add(new SequenceSuccess(getSurvival()));
            routines.add(new Sequence(extra));

            Job job = new Repeat(new SequenceSuccess(routines));
            jobs.add(job);
            job.start();

        }
        logger.debug("AI initialisation is done");
    }

    /**
     * Sets up the jobs which keep AI alive.
     *
     * @return ArrayList of survival jobs
     */
    private ArrayList<Job> getSurvival() {
        // Set up of the survival instincts
        ArrayList<Job> survival = new ArrayList<>();
        survival.add(new AvoidEdge());
        survival.add(new AvoidNeutralPlayer());
        survival.add(new AvoidBall());
        //survival.add(new AvoidPlayerCharge);
        return survival;
    }

    /**
     * Sets up the jobs which make AI aggressive towards other players.
     *
     * @return ArrayList of domination jobs
     */
    private ArrayList<Job> getDomination() {
        // Set up of the domination skills
        ArrayList<Job> domination = new ArrayList<>();
        domination.add(new Inverter(new ChaseBall()));
//        domination.add((new ChasePlayer()));

        ArrayList<Job> ballAim = new ArrayList<>();
        ballAim.add(new NeutralBallCheck());
        ballAim.add(new AimBall());

//        domination.add(new SequenceSuccess(ballAim));
        return  domination;

    }

    /**
     * Sets up the jobs which makes AI do peaceful jobs.
     *
     * @return ArrayList of peace jobs
     */
    private ArrayList<Job> getPeace() {
        // Setting up peace
        ArrayList<Job> peace = new ArrayList<>();
        peace.add(new WalkAbout());

        return peace;
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
        logger.debug("AI action called.");

        //TODO make then work in the order, not all at the same tick
        //TODO make a black board so that ai can chose characters and balls which are not targeted by other ai
        if (tick == 0) {
            for (int i = 0; i < aiPlayers.size(); i++) {
                Pair<Player, ArrayList<Player>> pair = aiPlayers.get(i);
                if (pair.getKey().getState() == ObjectState.DEAD) {

                    logger.debug(pair.getKey().getCharacterID() + " is dead");
                    pair.getKey().setVelocity(0,0);
                } else {
                    logger.debug(pair.getKey().getCharacterID() + " Starting AI");

                    jobs.get(i).act(pair.getKey(), pair.getValue(), balls, platform);
                }
            }
        //tick = tick + 1;
        } else if (tick == 3) {
            tick = 0;
        } else {
            tick = tick + 1;
        }
    }


}
