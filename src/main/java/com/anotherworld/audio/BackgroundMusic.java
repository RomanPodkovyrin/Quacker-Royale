package com.anotherworld.audio;

import com.anotherworld.control.GameSessionController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.*;


public class BackgroundMusic implements Runnable
{
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

    private static Logger logger = LogManager.getLogger(BackgroundMusic.class);

    public BackgroundMusic(){
        soundFile = new File(fileLocation);
    }

    public void playBackgroundMusic()
    {
        music = new Thread(this);
        music.start();
    }

    public void run()
    {
        while(running) {
//            System.out.println("Still alive background");
            try {
                createLine();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    private void createLine() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioFormat = audioInputStream.getFormat();
        information = new DataLine.Info(SourceDataLine.class, audioFormat);

        line = (SourceDataLine) AudioSystem.getLine(information);
        line.open(audioFormat);
        unMuteSound();
        line.start();
        numberOfBytesRead = 0;
        abData = new byte[254000];
        while (numberOfBytesRead != -1)
        {
            numberOfBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (numberOfBytesRead >= 0)
            {
                line.write(abData, 0, numberOfBytesRead);
            }
        }
        line.drain();
        line.close();
    }

    private void muteSound(){
        if( line.isControlSupported( FloatControl.Type.MASTER_GAIN)) {
            volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volume.getMinimum());
        } else{
            volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            volume.setValue(volume.getMinimum());
        }
    }

    private void unMuteSound(){
        if( line.isControlSupported( FloatControl.Type.MASTER_GAIN)) {
            volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volume.getMaximum());
        } else{
            volume = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            volume.setValue(volume.getMaximum());
        }

    }

    public void terminateMusic(){
        //TODO those 3 lines prevent linux from shutting down the thread
//        line.stop();
//        line.drain();
        music.stop();
        line.close();
        running = false;
//        music.interrupt();
//        System.exit(0);
    }

    public static void main(String[] args){
        BackgroundMusic ba = new BackgroundMusic();
        ba.playBackgroundMusic();
        while (true) {
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input == 1){
                ba.muteSound();
            }
            else if(input == 2)
                ba.unMuteSound();
            else if(input == 3)
                ba.terminateMusic();
        }
    }
}
