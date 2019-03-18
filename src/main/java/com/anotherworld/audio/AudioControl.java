package com.anotherworld.audio;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static Logger logger = LogManager.getLogger(AudioControl.class);

    /**
     * To be used at the beginning of the game to start background and effects depending on the previous settings.
     */
    public static void setUp() {
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();
        if (!musicOn) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            backgroundMusic.muteSound();
        }

        soundEffects = new SoundEffects();



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
        if (musicOn | effectsOn) {

            logger.info("Muted sound effects and Music");
            backgroundMusic.muteSound();
            effectsOn = false;
            musicOn = false;
        } else {
            logger.info("Unmuted sound effects and Music");
            backgroundMusic.unMuteSound();
            effectsOn = true;
            musicOn = true;
        }

    }


    /**
     * Stops playing background music.
     */
    public static void stopBackgroundMusic() {
        backgroundMusic.terminateMusic();
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
