package com.anotherworld.audio;

import org.junit.Test;

public class SoundEffectsTest {
    @Test
    public void testSoundEffects() {
        SoundEffects soundEffects = new SoundEffects();
        soundEffects.playerCollidedWithBall();
        soundEffects.ballCollidedWithWall();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        soundEffects.stopSoundEffects();
    }
}
