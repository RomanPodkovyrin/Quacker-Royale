package com.anotherworld.audio;

import org.junit.Test;

public class backgroundMusicTest {

    @Test
    public void testBackgroundMusic(){
        BackgroundMusic bMusic = new BackgroundMusic();
        bMusic.playBackgroundMusic();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bMusic.muteSound();
        bMusic.unMuteSound();
        bMusic.terminateMusic();
    }
}
