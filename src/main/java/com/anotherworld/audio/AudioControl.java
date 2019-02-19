package com.anotherworld.audio;

import java.io.IOException;

public class AudioControl {
    private static BackgroundMusic backgroundMusic ;
    private static SoundEffects soundEffects;

    public static void setUp() {
        backgroundMusic = new BackgroundMusic();
        soundEffects = new SoundEffects();


        // soundEffects set up
    }

    public static void playBackGroundMusic() {

        backgroundMusic.playBackgroundMusic();
    }

    public static void stopBackgroundMusic() {
        backgroundMusic.terminateMusic();
    }

    public static void playerCollidedWithBall() {
        try {

            soundEffects.playerCollidedWithBall();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ballCollidedWithWall() {
        try {
            soundEffects.ballCollidedWithWall();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopSoundEffects() {
        soundEffects.stopSoundEffects();
    }

}
