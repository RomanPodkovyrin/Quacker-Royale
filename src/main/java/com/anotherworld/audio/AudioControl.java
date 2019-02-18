package com.anotherworld.audio;

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

}
