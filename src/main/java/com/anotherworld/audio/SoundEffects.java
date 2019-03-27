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
    //https://freesound.org/people/zmobie/sounds/319783/
    private String ball = "./res/audio/basketball-8.wav";
    //https://freesound.org/people/Reitanna/sounds/242664/
    private String quack = "./res/audio/quack.wav";
    //https://freesound.org/people/FoolBoyMedia/sounds/397434/
    private String win = "./res/audio/crowd-cheer.wav";
    //https://freesound.org/people/JPolito/sounds/391697/
    private String hover = "./res/audio/hover.wav";
    //https://freesound.org/people/Rocotilos/sounds/178875/
    private String lose = "./res/audio/lose.wav";
    //All the files of sound effects
    private File ballFile;
    private File quackFile;
    private File winFile;
    private File hoverFile;
    private File loseFile;

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
        ballFile = new File(ball);
        quackFile = new File(quack);
        winFile = new File(win);
        hoverFile = new File(hover);
        loseFile = new File(lose);
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
     * Plays the button hover sound effect.
     */
    public void playButtonHover() {
        currentFile = hoverFile;
    }

    /**
     * Plays lose sound.
     */
    public void loseSound() {
        currentFile = loseFile;
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
//        currentFile = ballFile;
    }

    public void win() {
        System.out.println("Hello");
        currentFile = winFile;
    }

    /**
     * Plays a sound of ball collision with the another ball.
     */
    public void playerCollidedWithBall() {
        currentFile = quackFile;
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
