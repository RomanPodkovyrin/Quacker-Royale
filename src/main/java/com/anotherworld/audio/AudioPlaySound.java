package com.anotherworld.audio;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlaySound {

    public AudioPlaySound() throws IOException {
        playBackgroundMusic();
    }

    public void playBackgroundMusic() throws IOException {
        String backgroundMusicFile = "./res/audio/background_music.au";
        InputStream in = new FileInputStream(backgroundMusicFile);
        // create an audiostream from the inputstream
        AudioStream audioStream = new AudioStream(in);
        // play the audio clip with the audioplayer class
        AudioPlayer.player.start(audioStream);
    }

    public static void main(String[] args) throws IOException {
        AudioPlaySound audio = new AudioPlaySound();
    }
}