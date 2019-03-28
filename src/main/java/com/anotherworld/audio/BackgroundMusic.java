package com.anotherworld.audio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is in charge of playing a background music.
 *
 * @author Antons Lebedjko
 */
public class BackgroundMusic implements Runnable {
    private String fileLocation = "./res/audio/background_music.wav";
    private SourceDataLine line;
    private FloatControl volume;
    private File soundFile;
    private AudioInputStream audioInputStream;
    private int numberOfBytesRead;
    private byte[] abData;
    private AudioFormat audioFormat;
    private DataLine.Info information;
    private boolean running = true;
    private Thread music;
    private boolean isOn = true;
    private static Logger logger = LogManager.getLogger(BackgroundMusic.class);

    /**
     * Opens a background music file.
     */
    public BackgroundMusic() {
        soundFile = new File(fileLocation);
    }

    /**
     * Plays a background music.
     */
    public void playBackgroundMusic() {
        music = new Thread(this);
        music.setDaemon(true);
        music.start();
    }

    /**
     * A run method for the thread which creates a line and starts playing background music.
     */
    public void run() {
        while (running) {
            try {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (isOn) {
                    createLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to create a line for the background music file.
     */
    private void createLine() throws IOException, LineUnavailableException {

        audioFormat = audioInputStream.getFormat();
        information = new DataLine.Info(SourceDataLine.class, audioFormat);
        line = (SourceDataLine) AudioSystem.getLine(information);
        line.open(audioFormat);
        unMuteSound();
        line.start();
        logger.trace("Started to play background music line");
        numberOfBytesRead = 0;
        abData = new byte[2540000];
        while (numberOfBytesRead != -1) {
            numberOfBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (numberOfBytesRead >= 0) {
                line.write(abData, 0, numberOfBytesRead);
            }
        }
        audioInputStream.close();
        line.drain();
        line.close();
    }

    /**
     * Used to mute the background music.
     */
    public void muteSound() {
        isOn = false;
        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volume.getMinimum());
        } else {
            volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            volume.setValue(volume.getMinimum());
        }
    }

    /**
     * Used to unmute the background music.
     */
    public void unMuteSound() {
        isOn = true;
        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volume.getMaximum());
        } else {
            volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            volume.setValue(volume.getMaximum());
        }
    }

    /**
     * Used to terminate background music.
     */
    public void terminateMusic() {
        music.stop();
        line.close();
        running = false;
    }
}
