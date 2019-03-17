package com.anotherworld.audio;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is in charge of playing the sound effects.
 *
 * @author Antons Lebedjko
 * @author Roman P
 */
public class SoundEffects implements Runnable {
    //File paths and the references from where they have been taken
    //https://freesound.org/people/qubodup/sounds/332060/
    private String ballCollidedWithWallSound =  "./res/audio/ball_collided_with_the_wall.au";
    //https://freesound.org/people/jeckkech/sounds/391658/
    private String playerCollidedWithBallSound = "./res/audio/player_collided_with_ball.au";
    //https://freesound.org/people/Autistic%20Lucario/sounds/142608/
    private String error = "./res/audio/error.wav";
    //https://freesound.org/people/ProjectsU012/sounds/341695/
    private String beep = "./res/audio/beep.wav";
    //https://freesound.org/people/Ekokubza123/sounds/104183/
    private String punch = "./res/audio/punch.wav";
    //https://freesound.org/people/Leszek_Szary/sounds/146726/
    private String jump = "./res/audio/jump.wav";
    //https://freesound.org/people/zmobie/sounds/319783/
    private String ball = "./res/audio/basketball-8.wav";
    //https://freesound.org/people/plagasul/sounds/85/
    private String scream = "./res/audio/jeEH.wav";

    //All the files of sound effects
    private File ballCollidedWithWallFile;
    private File playerCollidedWithBallFile;
    private File errorFile;
    private File beepFile;
    private File punchFile;
    private File ballFile;
    private File screamFile;
    private File jumpFile;

    private Optional<SourceDataLine> line;
    private AudioInputStream audioInputStream;
    private int numberOfBytesRead;
    private byte[] abData;
    private AudioFormat audioFormat;
    private DataLine.Info information;
    private Thread effect;
    private boolean running = true;
    private File currentFile;
    private static Logger logger = LogManager.getLogger(SoundEffects.class);

    /**
     * Used to load the sound files and initialize the thread.
     */
    public SoundEffects() {
        ballCollidedWithWallFile = new File(ballCollidedWithWallSound);
        playerCollidedWithBallFile = new File(playerCollidedWithBallSound);
        errorFile = new File(error);
        beepFile = new File(beep);
        punchFile = new File(punch);
        jumpFile = new File(jump);
        ballFile = new File(ball);
        screamFile = new File(scream);
        effect = new Thread(this);
        line = Optional.empty();
        effect.start();
    }

    /**
     * A run method for the thread that plays the current sound effect.
     */
    public void run() {
        while (running) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (currentFile == null) {
                // nothing plays
            } else {
                logger.trace("Playing " + currentFile);

                try {
                    if (line.isPresent()) {
                        line.get().close();
                    }
                    createLine(currentFile);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } finally {
                    currentFile = null;
                }
            }
        }
    }

    /**
     * Used to create a line for the current sound effect.
     *
     * @param filename a sound file of the current sound effect
     */
    private void createLine(File filename) throws IOException, LineUnavailableException {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(filename);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioFormat = audioInputStream.getFormat();
        information = new DataLine.Info(SourceDataLine.class, audioFormat);
        line = Optional.of((SourceDataLine) AudioSystem.getLine(information));
        line.get().open(audioFormat);
        line.get().start();
        numberOfBytesRead = 0;
        abData = new byte[254000];
        while (numberOfBytesRead != -1) {
            numberOfBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (numberOfBytesRead >= 0) {
                line.get().write(abData, 0, numberOfBytesRead);
            }
        }
        audioInputStream.close();
    }

    /**
     * Plays a sound of ball collision with the wall.
     */
    public void ballCollidedWithWall() {
        logger.trace("Play ball sound");
        currentFile = ballFile;
    }

    /**
     * Plays a sound of ball collision with the another ball.
     */
    public void playerCollidedWithBall() {
        currentFile = screamFile;
    }

    /**
     * Stops the sound effects.
     */
    public void stopSoundEffects() {
        logger.info("STOPPING SOUND EFFECTS");
        effect.stop();
        if (line.isPresent()) {
            line.get().close();
        }
        running = false;
    }
}
