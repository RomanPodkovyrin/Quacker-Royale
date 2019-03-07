package com.anotherworld.model.ai.behaviour;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class InverterTest {

    @Test
    public void inverterTest() {


        // Tests if inverts the success
        JobSuccessTest jobSuccess = new JobSuccessTest();
        Inverter invertedJob = new Inverter(jobSuccess);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isFailure());

        // Tests if inverts the fail
        JobFailTest jobFail = new JobFailTest();
        invertedJob = new Inverter(jobFail);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isSuccess());

        // Tests if keeps the job running
        JobRunningTest jobrunning = new JobRunningTest();
        invertedJob = new Inverter(jobrunning);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isRunning());



    }

}
