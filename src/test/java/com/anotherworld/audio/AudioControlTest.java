package com.anotherworld.audio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.sound.sampled.AudioSystem;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AudioControlTest {
    private final boolean expectedMusicState;
    private final boolean expectedEfFectsState;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {true,true},
                {false,true},
                {false,false}
        });
    }

    public AudioControlTest(boolean expectedMusicState, boolean expectedEfFectsState) {
        this.expectedMusicState = expectedMusicState;
        this.expectedEfFectsState = expectedEfFectsState;
    }

    @Test
    public void audioTest() {

        AudioControl.setMusicOn(expectedMusicState);
        AudioControl.setEffectsOn(expectedEfFectsState);
        //Todo finish the tests
//        AudioControl.setUp();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("hello");
//        AudioControl.playerCollidedWithBall();
//        AudioControl.ballCollidedWithWall();
//        AudioControl.stopSoundEffects();
//        System.out.println("m");
//        AudioControl.stopBackgroundMusic();
//        System.out.println("d");

    }
}
