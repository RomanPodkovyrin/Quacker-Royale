package com.anotherworld.model.ai;

import com.anotherworld.model.ai.behaviour.Inverter;
import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.ai.behaviour.Repeat;
import com.anotherworld.model.ai.behaviour.Selector;
import com.anotherworld.model.ai.behaviour.Sequence;
import com.anotherworld.model.ai.behaviour.SequenceSuccess;

import com.anotherworld.model.ai.behaviour.player.CheckIfSaveToGo;

import com.anotherworld.model.ai.behaviour.player.domination.ChaseBall;
import com.anotherworld.model.ai.behaviour.player.domination.GetPowerUPs;

import com.anotherworld.model.ai.behaviour.player.domination.PointAndDash;
import com.anotherworld.model.ai.behaviour.player.domination.StartCharging;
import com.anotherworld.model.ai.behaviour.player.peace.WalkAbout;

import com.anotherworld.model.ai.behaviour.player.survival.*;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.PropertyReader;
import com.anotherworld.tools.datapool.BallData;
import com.anotherworld.tools.datapool.GameSessionData;
import com.anotherworld.tools.datapool.PlayerData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.util.Pair;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * This class sets up all the jobs for AIs and takes care of running AIController when told to do so.
 *
 * @author Roman P
 */
public class AIController {

    private static Logger logger = LogManager.getLogger(AIController.class);

    private ArrayList<Pair<PlayerData,ArrayList<PlayerData>>> aiPlayers = new ArrayList<>();
    private ArrayList<BallData> balls;
    private ArrayList<Job> jobs = new ArrayList<>();
    private Platform platform;
    private GameSessionData session;

    private int tick = 0;
    private int maxTick = 2;
    private int step = 1;

    /**
     * Used to set up the AIController handler.
     *
     * @param ais All the ai players
     * @param allPlayers the rest of the allPlayers on the map(user and ai controlled)
     * @param balls all the balls on the map
     * @param platform the current platform
     */
    public AIController(ArrayList<PlayerData> ais, ArrayList<PlayerData> allPlayers, ArrayList<BallData> balls, Platform platform, GameSessionData session) {
        ArrayList<PlayerData> allPlayers1 = allPlayers;
        this.balls = balls;
        this.platform = platform;
        this.session = session;
        try {
            PropertyReader  aiProperties = new PropertyReader("ai.properties");
            maxTick = Integer.parseInt(aiProperties.getValue("AI_RATE"));
            if (maxTick == 0) {
                step = 0;
            }
        } catch (IOException e) {
            logger.error("Could not read the ai properties file, relying on default values");
        }



        // Gives all AIs their individual jobs
        for (PlayerData ai : ais) {
            // Gives the AIController representation of other players(AIs and human players)
            aiPlayers.add(new Pair<>(ai, removePlayer(allPlayers,ai)));

            // Setting up domination and peace combined list
            ArrayList<Job> dominationAndPeace = new ArrayList<>();
            dominationAndPeace.add(new SequenceSuccess(getDomination()));
            dominationAndPeace.add(getPeace());

            // Setting up the extra check if the given makeDecision can be done to avoid the ball
            ArrayList<Job> extra = new ArrayList<>();
            extra.add(new SequenceSuccess(dominationAndPeace));
            extra.add(new CheckIfSaveToGo());


            // Setting up the main routine
            ArrayList<Job> routines = new ArrayList<>();
            routines.add(new Selector(new ArrayList<>(Arrays.asList(new SequenceSuccess(getSurvival()), new StopCharging()))));
            routines.add(new Sequence(extra));

            Job job = new Repeat(new SequenceSuccess(routines));
            jobs.add(job);
            job.start();

        }
        logger.debug("AIController initialisation is done");
    }

    /**
     * Sets up the jobs which keep AIController alive.
     *
     * @return ArrayList of survival jobs
     */
    private ArrayList<Job> getSurvival() {
        // Set up of the survival instincts
        ArrayList<Job> survival = new ArrayList<>();
        survival.add(new AvoidEdge());
//        survival.add(new AvoidNeutralPlayer());

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
     * Sets up the jobs which make AIController aggressive towards other players.
     *
     * @return ArrayList of domination jobs
     */
    private ArrayList<Job> getDomination() {
        // Set up of the domination skills
        ArrayList<Job> domination = new ArrayList<>();
        domination.add(new PointAndDash());
        domination.add(new Inverter(new GetPowerUPs()));
        domination.add(new Inverter(new ChaseBall()));
        domination.add(new StartCharging());
        return  domination;

    }

    /**
     * Sets up the jobs which makes AIController do peaceful jobs.
     *
     * @return ArrayList of peace jobs
     */
    private Job getPeace() {
        // Setting up peace
//        ArrayList<Job> peace = new ArrayList<>();
//        peace.add(new WalkAbout());

        return new WalkAbout();
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
     * Is called when AIController needs to make a decision based
     * on the current state of the game session.
     */
    public void makeDecision() {
        logger.debug("AIController makeDecision called.");

        if (tick == 0) {
            for (int i = 0; i < aiPlayers.size(); i++) {
                Pair<PlayerData, ArrayList<PlayerData>> pair = aiPlayers.get(i);
                if (pair.getKey().getState() == ObjectState.DEAD) {

                    logger.debug(pair.getKey().getObjectID() + " is dead");
                    pair.getKey().setVelocity(0,0);
                } else {
                    logger.debug(pair.getKey().getObjectID() + " Starting AIController");

                    jobs.get(i).act(pair.getKey(), pair.getValue(), balls, platform, session);
                }

            }
            tick = tick + step;
        } else if (tick == maxTick) {
            tick = 0;
        } else {
            tick = tick + step;
        }
    }


}
