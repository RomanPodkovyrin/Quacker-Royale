package com.anotherworld.audio;

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

    public BackgroundMusic(){
        soundFile = new File(fileLocation);
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioFormat = audioInputStream.getFormat();
        information = new DataLine.Info(SourceDataLine.class, audioFormat);
    }

    public void playBackgroundMusic()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run()
    {
        while(true) {
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
            //found another way to change volume for openjdk
        }
    }

    private void unMuteSound(){
        if( line.isControlSupported( FloatControl.Type.MASTER_GAIN)) {
            volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(volume.getMaximum());
            System.out.println("correct java version");
        } else{
            //found another way to change volume for openjdk
        }

    }

    public static void main(String[] args){
        BackgroundMusic ba = new BackgroundMusic();
        ba.playBackgroundMusic();
        while (true) {
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input == 1){
                System.out.println("1");
                ba.muteSound();
            }
            else if(input == 2)
                System.out.println("2");
                ba.unMuteSound();
        }
    }
}
