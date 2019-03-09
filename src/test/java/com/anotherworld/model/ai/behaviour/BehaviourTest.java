package com.anotherworld.model.ai.behaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BehaviourTest {
    private JobSuccessTest jobSuccess;
    private JobFailTest jobFail;
    private JobRunningTest jobRunning;

    @Before
    public void setup() {
        jobSuccess = new JobSuccessTest();
        jobFail = new JobFailTest();
        jobRunning = new JobRunningTest();

    }


    @Test
    public void inverterTest() {

        // Tests if inverts the success
        Inverter invertedJob = new Inverter(jobSuccess);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isFailure());

        // Tests if inverts the fail
        invertedJob = new Inverter(jobFail);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isSuccess());

        // Tests if keeps the job running
        invertedJob = new Inverter(jobRunning);
        invertedJob.start();
        invertedJob.act(null,null,null,null);
        assertTrue(invertedJob.isRunning());
    }

    @Test
    public void repeatTest() {

        // test if continues running with success job
        Repeat repeatJob = new Repeat(jobSuccess);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());

        // test if continues running with fail job
        repeatJob = new Repeat(jobFail);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());

        // test if continues running with running job
        repeatJob = new Repeat(jobRunning);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());

        // test if finishing running after one run with success job
        repeatJob = new Repeat(jobSuccess,1);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isSuccess());

        // test if finishing running after one run with fail job
        repeatJob = new Repeat(jobFail,1);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isSuccess());

        // test if still running after one run with running job
        repeatJob = new Repeat(jobRunning,1);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());


        // test if finishing running after two runs with success job
        repeatJob = new Repeat(jobSuccess,2);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isSuccess());

        // test if finishing running after two runs with fail job
        repeatJob = new Repeat(jobFail,2);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isSuccess());

        // test if still running  running after two runs with running job
        repeatJob = new Repeat(jobRunning,2);
        repeatJob.start();
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());
        repeatJob.act(null,null,null,null);
        assertTrue(repeatJob.isRunning());

    }

    @Test
    public void selectorTest() {

        // Test if fails after running jobs which all fail
        ArrayList<Job> failJobs = new ArrayList<>(Arrays.asList(
                new JobFailTest(), new JobFailTest(), new JobFailTest()
        ));
        Selector selectorJob = new Selector(failJobs);
        selectorJob.act(null,null,null,null);
        assertTrue(selectorJob.isFailure());

        // Test if succeeds after running success job
        ArrayList<Job> jobs = new ArrayList<>(Arrays.asList(
            new JobFailTest(),jobSuccess,jobFail
        ));
        selectorJob = new Selector(jobs);
        selectorJob.act(null,null,null,null);
        assertTrue(selectorJob.isSuccess());
        assertTrue(jobSuccess.isSuccess());
        assertNull(jobFail.state);


        // Test if succeeds after running success job at the end of the list
        jobSuccess = new JobSuccessTest();
        jobFail = new JobFailTest();
        jobs = new ArrayList<>(Arrays.asList(
            new JobFailTest(),jobFail, jobSuccess
        ));
        selectorJob = new Selector(jobs);
        selectorJob.act(null,null,null,null);
        assertTrue(selectorJob.isSuccess());
        assertTrue(jobSuccess.isSuccess());
        assertTrue(jobFail.isFailure());
    }

    @Test
    public void sequenceSeccuessTest() {

        // Check if fails after running a fail jobs
        ArrayList<Job> failJobs = new ArrayList<>(Arrays.asList(
                new JobFailTest(), new JobFailTest(), new JobFailTest()
        ));
        SequenceSuccess sequenceSuccessJob = new SequenceSuccess(failJobs);
        sequenceSuccessJob.act(null,null,null,null);
        assertTrue(sequenceSuccessJob.isFailure());

        // Test if fails after running a mixture of success and fail jobs
        ArrayList<Job> jobs = new ArrayList<>(Arrays.asList(
                jobSuccess,jobFail
        ));
        sequenceSuccessJob = new SequenceSuccess(jobs);
        sequenceSuccessJob.act(null,null,null,null);
        assertTrue(sequenceSuccessJob.isFailure());
        assertTrue(jobSuccess.isSuccess());
        assertTrue(jobFail.isFailure());

        // Test if fails after running mixture of fail and success job in reverse order
        jobSuccess = new JobSuccessTest();
        jobFail = new JobFailTest();
        jobs = new ArrayList<>(Arrays.asList(
                jobFail, jobSuccess
        ));
        sequenceSuccessJob = new SequenceSuccess(jobs);
        sequenceSuccessJob.act(null,null,null,null);
        assertTrue(sequenceSuccessJob.isFailure());
        assertNull(jobSuccess.state);
        assertTrue(jobFail.isFailure());

        // Test if succeeds after running success jobs
        jobSuccess = new JobSuccessTest();
        jobs = new ArrayList<>(Arrays.asList(
                new JobSuccessTest(), jobSuccess
        ));
        sequenceSuccessJob = new SequenceSuccess(jobs);
        sequenceSuccessJob.act(null,null,null,null);
        assertTrue(sequenceSuccessJob.isSuccess());
        assertTrue(jobSuccess.isSuccess());
    }

    @Test
    public void sequenceTest() {

        // Test if succeeds after running all fail jobs
        ArrayList<Job> failJobs = new ArrayList<>(Arrays.asList(
                new JobFailTest(), new JobFailTest(), new JobFailTest()
        ));
        Sequence sequenceJob = new Sequence(failJobs);
        sequenceJob.act(null,null,null,null);
        assertTrue(sequenceJob.isSuccess());

        // Test if succeeds after running mixture of success and fail jobs
        ArrayList<Job> jobs = new ArrayList<>(Arrays.asList(
                jobSuccess,jobFail
        ));
        sequenceJob = new Sequence(jobs);
        sequenceJob.act(null,null,null,null);
        assertTrue(sequenceJob.isSuccess());
        assertTrue(jobSuccess.isSuccess());
        assertTrue(jobFail.isFailure());

        // Test if succeeds after running mixture of success and fail jobs in reverse
        jobSuccess = new JobSuccessTest();
        jobFail = new JobFailTest();
        jobs = new ArrayList<>(Arrays.asList(
                jobFail, jobSuccess
        ));
        sequenceJob = new Sequence(jobs);
        sequenceJob.act(null,null,null,null);
        assertTrue(sequenceJob.isSuccess());
        assertTrue(jobSuccess.isSuccess());
        assertTrue(jobFail.isFailure());


        // Check if succeeds after running no jobs
        sequenceJob = new Sequence(new ArrayList<>());
        sequenceJob.act(null,null,null,null);
        assertTrue(sequenceJob.isSuccess());

        // Test if succeeds after running running jobs
        jobRunning = new JobRunningTest();
        jobs = new ArrayList<>(Arrays.asList(
                new JobRunningTest(), jobRunning
        ));
        sequenceJob = new Sequence(jobs);
        sequenceJob.act(null,null,null,null);
        assertTrue(sequenceJob.isSuccess());
        assertTrue(jobRunning.isRunning());
    }

    @Test
    public void repeatSuccessTest() {

        // Check if still running after running success job
        RepeatSuccess repeatSuccessJob = new RepeatSuccess(jobSuccess);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isRunning());
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isRunning());


        // Check if fails after running fail job
        repeatSuccessJob = new RepeatSuccess(jobFail);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isFailure());


        // Test if is still running when running running job
        repeatSuccessJob = new RepeatSuccess(jobRunning);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isRunning());

        // Check if succeeds when running success once
        repeatSuccessJob = new RepeatSuccess(jobSuccess,1);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isSuccess());

        // Check if succeeds when running success twice
        repeatSuccessJob = new RepeatSuccess(jobSuccess,2);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isRunning());
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isSuccess());

        // Test if fails after running fail once
        repeatSuccessJob = new RepeatSuccess(jobFail,1);
        repeatSuccessJob.start();
        repeatSuccessJob.act(null,null,null,null);
        assertTrue(repeatSuccessJob.isFailure());
    }

    @Test(expected = RuntimeException.class)
    public void repeatExceptionTest() {
        // only positive value for times are allowed
        Repeat repeatJob= new Repeat(null,0);
    }

    @Test(expected = RuntimeException.class)
    public void repeatSuccessExceptionTest() {
        // only positive value for times are allowed
        RepeatSuccess repeatJob= new RepeatSuccess(null,0);
    }

    @Test
    public void SucceederTest() {

        // Tests if succeeder succeeds with success job
        Succeeder succeederJob = new Succeeder(jobSuccess);
        succeederJob.act(null,null,null,null);
        assertTrue(succeederJob.isSuccess());

        // Test if succeeder succeeds with fail job
        succeederJob = new Succeeder(jobFail);
        succeederJob.act(null,null,null,null);
        assertTrue(succeederJob.isSuccess());

        // Test if succeeds with running job
        succeederJob = new Succeeder(jobRunning);
        succeederJob.act(null,null,null,null);
        assertTrue(succeederJob.isSuccess());




    }


}
