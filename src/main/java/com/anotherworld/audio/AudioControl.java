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
        // Creates new music object and starts
        backgroundMusic = new BackgroundMusic();
        backgroundMusic.playBackgroundMusic();

        if (!musicOn) {
            // Mute the music
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            backgroundMusic.muteSound();
        }

        // Reset the sound effect if needed or make new
        if (soundEffects != null) {
            soundEffects.stopSoundEffects();
        }
        soundEffects = new SoundEffects();


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
    public static void muteUnmute() {

        try {
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
        } catch (IllegalArgumentException e) {
            logger.error("Tried to mute the music while closing the game");
        }

    }


    /**
     * Stops playing background music.
     */
    public static void stopBackgroundMusic() {
        try {
            backgroundMusic.terminateMusic();
        } catch (NullPointerException e) {
            logger.error("Stopping music that isn't running");
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
     * Plays the hover sound effect.
     */
    public static void playButtonHover() {
        if (effectsOn) {
            if (soundEffects == null) {
                soundEffects = new SoundEffects();
            }
            soundEffects.playButtonHover();
        }
    }

    /**
     * Plays the win sound.
     */
    public static void win() {
        if (effectsOn) {
            soundEffects.win();
        }
    }


    /**
     * Plays shield breaks.
     */
    public static void shieldBreak() {
        if (effectsOn) {
            soundEffects.shieldBreak();
        }
    }

    /**
     * Plays time pick up sound effect.
     */
    public static void time() {
        if (effectsOn) {
            soundEffects.time();
        }
    }

    /**
     * Plays health pick up sound effect.
     */
    public static void health() {
        if (effectsOn) {
            soundEffects.health();
        }
    }

    /**
     * Plays shield pick up sound effect.
     */
    public static void shield() {
        if (effectsOn) {
            soundEffects.shield();
        }
    }

    /**
     * Plays the lose sound.
     */
    public static void lose() {
        if (effectsOn) {
            soundEffects.loseSound();
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
        try {
            soundEffects.stopSoundEffects();
        } catch (NullPointerException e) {
            logger.error("Stopping music which wasn't created");
        }
    }

}
