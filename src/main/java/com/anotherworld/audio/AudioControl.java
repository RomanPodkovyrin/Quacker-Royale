package com.anotherworld.audio;

import java.io.IOException;

/**
 * This class controls background and sound effects.
 *
 * @author roman
 * @author anton
 */
public class AudioControl {
    private static BackgroundMusic backgroundMusic;
    private static SoundEffects soundEffects;
    private static boolean musicOn = true;
    private static boolean effectsOn = true;
    private static boolean mutedSoundEffects = false;
    private static boolean mutedBackgroundMusic = false;

    /**
     * To be used at the beginning of the game to start background and effects depending on the previous settings.
     */
    public static void setUp() {
        if (musicOn) {
            backgroundMusic = new BackgroundMusic();
        }
        if (effectsOn) {
            soundEffects = new SoundEffects();
        }


        // soundEffects set up
    }

    /**
     * Sets background music to either on or off.
     * @param musicOn true - on, false - off
     */
    public static void setMusicOn(boolean musicOn) {
        AudioControl.musicOn = musicOn;
    }

    /**
     * Sets sound effects to either on or off.
     * @param effectsOn true - on, false - off
     */
    public static void setEffectsOn(boolean effectsOn) {
        AudioControl.effectsOn = effectsOn;
    }

    /**
     * Mutes of unmutes background music and sound effects.
     */
    public static void muteUnmute(){

    }

    /**
     * Starts playing background music.
     */
    public static void playBackGroundMusic() {
        if (musicOn) {
            backgroundMusic.playBackgroundMusic();
        }
    }

    /**
     * Stops playing background music.
     */
    public static void stopBackgroundMusic() {
        if (musicOn) {
            backgroundMusic.terminateMusic();
        }
    }

    /**
     * Plays player collision with the ball.
     */
    public static void playerCollidedWithBall() {
        if (effectsOn) {

            soundEffects.playerCollidedWithBall();
        }
    }

    /**
     * Plays ball collision with the wall.
     */
    public static void ballCollidedWithWall() {
        if (effectsOn) {
            soundEffects.ballCollidedWithWall();
        }
    }

    /**
     * Stops all sound effects.
     */
    public static void stopSoundEffects() {
        if (effectsOn) {
            soundEffects.stopSoundEffects();
        }
    }

}
