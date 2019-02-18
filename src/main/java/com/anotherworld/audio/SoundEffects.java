package com.anotherworld.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SoundEffects {
    //https://freesound.org/people/qubodup/sounds/332060/
    private String ballCollidedWithWallSound =  "./res/audio/ball_collided_with_the_wall.au";
    //https://freesound.org/people/jeckkech/sounds/391658/
    private String playerCollidedWithBallSound = "./res/audio/player_collided_with_ball.au";
    private File ballCollidedWithWallFile;
    private File playerCollidedWithBallFile;
    private SourceDataLine line;
    private AudioInputStream audioInputStream;
    private int numberOfBytesRead;
    private byte[] abData;
    private AudioFormat audioFormat;
    private DataLine.Info information;

    public SoundEffects(){
        ballCollidedWithWallFile = new File(ballCollidedWithWallSound);
        playerCollidedWithBallFile = new File(playerCollidedWithBallSound);
    }

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
        line = (SourceDataLine) AudioSystem.getLine(information);
        line.open(audioFormat);
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
//        line.drain();
//        line.close();
    }

    public void ballCollidedWithWall() throws IOException {
        try {
            createLine(ballCollidedWithWallFile);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playerCollidedWithBall() throws IOException {
        try {
            createLine(playerCollidedWithBallFile);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // The code below and a main method itself is for demonstration purposes, so it shows how we can use it in a future
        SoundEffects sound = new SoundEffects();
        while(true){
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            if(i == 1){
                sound.ballCollidedWithWall();
            }
            else if(i ==2){
                sound.playerCollidedWithBall();
            }
        }
    }

}
