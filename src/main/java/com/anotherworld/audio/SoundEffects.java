package com.anotherworld.audio;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SoundEffects {
    //https://freesound.org/people/qubodup/sounds/332060/
    private String ballCollidedWithWallSound =  "./res/audio/ball_collided_with_the_wall.au";
    //https://freesound.org/people/jeckkech/sounds/391658/
    private String playerCollidedWithBallSound = "./res/audio/player_collided_with_ball.au";

    public void ballCollidedWithWall() throws IOException {
        InputStream in = new FileInputStream(ballCollidedWithWallSound);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }

    public void playerCollidedWithBall() throws IOException {
        InputStream in = new FileInputStream(playerCollidedWithBallSound);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
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
