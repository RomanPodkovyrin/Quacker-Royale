package com.anotherworld.audio;

import java.io.IOException;

public class AudioControl {
    private static BackgroundMusic backgroundMusic ;
    private static SoundEffects soundEffects;
    private static boolean musicOn = true;
    private static boolean effectsOn = true;

    public static void setUp() {
        if (musicOn) {
            backgroundMusic = new BackgroundMusic();
        }
        if (effectsOn) {
            soundEffects = new SoundEffects();
        }


        // soundEffects set up
    }

    public static void setMusicOn(boolean musicOn) {
        AudioControl.musicOn = musicOn;
    }

    public static void setEffectsOn(boolean effectsOn) {
        AudioControl.effectsOn = effectsOn;
    }

    public static void playBackGroundMusic() {
        if (musicOn) {
            System.out.println("Playing");
            backgroundMusic.playBackgroundMusic();
        }
    }

    public static void stopBackgroundMusic() {
        if (musicOn) {
            System.out.println("Stoping");
            backgroundMusic.terminateMusic();
        }
    }

    public static void playerCollidedWithBall() {
        if (effectsOn) {

            soundEffects.playerCollidedWithBall();
        }
    }

    public static void ballCollidedWithWall() {
        if (effectsOn) {
            soundEffects.ballCollidedWithWall();
        }
    }

    public static void stopSoundEffects() {
        if (effectsOn) {
            soundEffects.stopSoundEffects();
        }
    }

}
