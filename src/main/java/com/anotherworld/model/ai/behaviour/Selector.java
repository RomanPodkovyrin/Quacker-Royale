package com.anotherworld.model.ai.behaviour;

import com.anotherworld.model.logic.Platform;
import com.anotherworld.model.movable.Ball;
import com.anotherworld.model.movable.Player;

import java.util.ArrayList;
import java.util.Queue;

public class Selector extends Job {

    private final Queue<Job> jobs;
    private Job currentJob;

    public Selector(Queue<Job> jobs) {
        this.jobs = jobs;
        if (jobs.isEmpty()){
            isSuccess();
            return;
        }
    }


    @Override
    public void reset() {

        // get next job from the queue?

    }

    @Override
    public void act(Player ai, Player[] players, Ball[] balls, Platform platform ) {

        if (jobs.isEmpty()) {
            fail();
            //return ?
        } else {
            currentJob = jobs.poll();
        }

        if( currentJob.isSuccess()){
            succeed();
            return;
        } else if ( currentJob.isFailure());



    }
}
