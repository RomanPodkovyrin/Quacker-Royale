package com.anotherworld.audio;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BackgroundMusic {
    private boolean released = false;
    private AudioInputStream stream;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean playing = false;
    private String backgroundSongPath = "./res/audio/background_music.wav";

    public BackgroundMusic() {
        File file = new File(backgroundSongPath);
        try {
            stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volumeControl.getMaximum());
            released = true;
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        clip.setFramePosition(0);
        clip.start();
        playing = true;
        clip.loop(100);
    }

    public void muteSound(){
        volumeControl.setValue(volumeControl.getMinimum());
    }

    public void unMuteSound(){
        volumeControl.setValue(volumeControl.getMaximum());
    }

    public void stop() {
        if (playing) {
            playing = false;
            clip.stop();
        }
    }

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        //The code below and a main method itself is for demonstration purposes, so it shows how we can use it in a future
        BackgroundMusic bMusic = new BackgroundMusic();
        bMusic.playBackgroundMusic();
        while (true) {
            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if(input == 1){
                bMusic.muteSound();
            }
            else if( input == 2){
                bMusic.unMuteSound();
            }
//        }
        }
    }
}
