package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.Inverter;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.behaviour.Selector;
import com.anotherworld.model.ai.behaviour.Sequence;
import com.anotherworld.model.ai.behaviour.SequenceSuccess;
import com.anotherworld.model.ai.behaviour.player.*;

import com.anotherworld.model.ai.behaviour.player.domination.ChaseBall;
import com.anotherworld.model.ai.behaviour.player.domination.GetPowerUPs;
import com.anotherworld.model.ai.behaviour.player.peace.WalkAbout;
import com.anotherworld.model.ai.behaviour.player.survival.*;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.model.movable.Player;
import java.util.ArrayList;

import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;
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

    private ArrayList<Pair<PlayerData,ArrayList<PlayerData>>> aiPlayers = new ArrayList<>();
    private ArrayList<PlayerData> allPlayers;
    private ArrayList<BallData> balls;
    private ArrayList<Job> jobs = new ArrayList<>();
    private Platform platform;
    private GameSessionData session;

    private int tick = 0;

    /**
     * Used to set up the AI handler.
     *
     * @param ais All the ai players
     * @param allPlayers the rest of the allPlayers on the map(user and ai controlled)
     * @param balls all the balls on the map
     * @param platform the current platform
     */
    public AI(ArrayList<PlayerData> ais, ArrayList<PlayerData> allPlayers, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        this.allPlayers = allPlayers;
        this.balls = balls;
        this.platform = platform;
        this.session = session;

        // Gives all AIs their individual jobs
        for (PlayerData ai : ais) {
            // Gives the AI representation of other players(AIs and human players)
            aiPlayers.add(new Pair<>(ai, removePlayer(allPlayers,ai)));

            // Setting up domination and peace combined list
            ArrayList<Job> dominationAndPeace = new ArrayList<>();
            dominationAndPeace.add(new SequenceSuccess(getDomination()));
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

        ArrayList<Job> powerCheck = new ArrayList<>();
        powerCheck.add(new CheckShieldandTimePowerUP());
        powerCheck.add(new AvoidBall());
        survival.add(new Selector(powerCheck));

        ArrayList<Job> healPowerUP = new ArrayList<>();
        healPowerUP.add(new CheckHealth());
        healPowerUP.add(new Inverter(new GetHealth()));
        survival.add(new Selector(healPowerUP));
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
        domination.add(new Inverter(new GetPowerUPs()));
        domination.add(new Inverter(new ChaseBall()));
//        domination.add((new ChasePlayer()));

//        ArrayList<Job> ballAim = new ArrayList<>();
//        ballAim.add(new NeutralBallCheck());
//        ballAim.add(new AimBall());

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
    private ArrayList<PlayerData> removePlayer(ArrayList<PlayerData> players, PlayerData player) {
        ArrayList<PlayerData> newPlayers = new ArrayList<>();

        for (PlayerData p : players) {
            if (!p.getObjectID().equals(player.getObjectID())) {
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

        if (tick == 0) {
            for (int i = 0; i < aiPlayers.size(); i++) {
                Pair<PlayerData, ArrayList<PlayerData>> pair = aiPlayers.get(i);
                if (pair.getKey().getState() == ObjectState.DEAD) {

                    logger.debug(pair.getKey().getObjectID() + " is dead");
                    pair.getKey().setVelocity(0,0);
                } else {
                    logger.debug(pair.getKey().getObjectID() + " Starting AI");

                    jobs.get(i).act(pair.getKey(), pair.getValue(), balls, platform, session);
                }
            }
        tick = tick + 1;
        } else if (tick == 3) {
            tick = 0;
        } else {
            tick = tick + 1;
        }
    }


}
