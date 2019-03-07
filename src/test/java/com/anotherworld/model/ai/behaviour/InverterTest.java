package com.anotherworld.model.ai.behaviour;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class InverterTest {

    @Test
    public void inverterTest() {

        JobTest job = new JobTest();
        job.succeed();

        Inverter invertedJob = new Inverter(job);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isFailure());

        job.fail();
        invertedJob = new Inverter(job);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isSuccess());


        job.start();
        invertedJob = new Inverter(job);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isRunning());



    }

}
