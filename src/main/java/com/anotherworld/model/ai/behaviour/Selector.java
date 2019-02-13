package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.behaviour.player.AvoidBall;
import com.anotherworld.model.ai.behaviour.player.EmptyJobQueueException;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Executes the given jobs in order until one of them succeeds.
 * @author  Roman
 *
 */
public class Selector extends Job {

    private static Logger logger = LogManager.getLogger(Selector.class);


    private  ArrayList<Job> jobs;
//    private Queue<Job> originalJobs;
//    private Job currentJob;

    /**
     * Initialise the Selector Job.
     *
     * @param jobs The Queue of Jobs to be executed in the given order
     */
    public Selector(ArrayList<Job> jobs) {
        this.jobs = jobs;
//        this.originalJobs = new LinkedList<>(jobs);
//        System.out.println(jobs.toString());
//        if (jobs.isEmpty()) {
//            //throw new EmptyJobQueueException ("Empty queue was passed into a Job");
//            succeed();
//            return;
//        }
//        this.currentJob = jobs.poll();
    }


    @Override
    public void reset() {
//        this.jobs = new LinkedList<>(originalJobs);
//        this.currentJob = jobs.poll();

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {

        

        logger.debug("Starting Selector Job");
        for (Job currentJob: jobs) {
            currentJob.start();


            if (currentJob.isRunning()) {
                currentJob.act(ai, players, balls, platform);
            }

            if (currentJob.isSuccess()) {
                succeed();
                logger.debug("Finishing Selector Job with success");
                return;
            } else if (jobs.isEmpty()) {
                fail();
                logger.debug("Finishing Selector Job with fail");
                return;
            } else if (currentJob.isFailure()) {
//                currentJob = jobs.poll();
//                currentJob.start();
            } else {
                logger.error("I DONT KNOW WHAT TO DO");
            }
        }

        fail();

    }

    @Override
    public void start() {
        super.start();
//        this.currentJob.start();
    }
}
