package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.ai.behaviour.Job;
import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;

/**
 * Inverts fail into success and vise versa
 * @author roman
 */
public class Inverter extends Job {

    private Job job;

    public Inverter(Job job) {
        this.job = job;
    }
    @Override
    public void reset() {

    }

    @Override
    public void act(Player ai, ArrayList<Player> players, ArrayList<Ball> balls, Platform platform) {
        job.start();
        job.act(ai,players,balls,platform);
        if (job.isFailure()){
            succeed();
            return;
        } else if (job.isSuccess()) {
            fail();
            return;
        } else {
            return;
        }


    }
}
