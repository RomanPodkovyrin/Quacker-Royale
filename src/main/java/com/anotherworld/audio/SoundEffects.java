package com.anotherworld.audio;

import com.anotherworld.model.logic.GameSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SoundEffects implements Runnable{
    //https://freesound.org/people/qubodup/sounds/332060/
    private String ballCollidedWithWallSound =  "./res/audio/ball_collided_with_the_wall.au";
    //https://freesound.org/people/jeckkech/sounds/391658/
    private String playerCollidedWithBallSound = "./res/audio/player_collided_with_ball.au";
    //https://freesound.org/people/Autistic%20Lucario/sounds/142608/
    private String error ="./res/audio/error.wav";
            //https://freesound.org/people/ProjectsU012/sounds/341695/
    private String beep = "./res/audio/beep.wav";
    //https://freesound.org/people/Ekokubza123/sounds/104183/
    private String punch = "./res/audio/punch.wav";
    //https://freesound.org/people/Leszek_Szary/sounds/146726/
    private String jump = "./res/audio/jump.wav";
    private File ballCollidedWithWallFile;
    private File playerCollidedWithBallFile;
    private File errorFile;
    private File beepFile;
    private File punchFile;
    private File jumpFile;

    private SourceDataLine line;
    private AudioInputStream audioInputStream;
    private int numberOfBytesRead;
    private byte[] abData;
    private AudioFormat audioFormat;
    private DataLine.Info information;
    private Thread effect;
    private boolean running = true;
    private File currentFile;

    private static Logger logger = LogManager.getLogger(SoundEffects.class);

    public SoundEffects(){
        ballCollidedWithWallFile = new File(ballCollidedWithWallSound);
        playerCollidedWithBallFile = new File(playerCollidedWithBallSound);
        errorFile = new File(error);
        beepFile = new File(beep);
        punchFile = new File(punch);
        jumpFile = new File(jump);
        effect = new Thread(this);
        effect.start();
    }

    public void run()
    {
        while(running) {
//            System.out.println("Still alive sound effect");
            try{
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (currentFile == null) {
                // nothing plays
            } else {
                logger.trace("Playing " + currentFile);

                try {
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
        abData = new byte[2540000];
        while (numberOfBytesRead != -1)
        {
            numberOfBytesRead = audioInputStream.read(abData, 0, abData.length);
            if (numberOfBytesRead >= 0)
            {
                line.write(abData, 0, numberOfBytesRead);
            }
        }
    }

    public void ballCollidedWithWall() {
        logger.trace("Play ball sound");
        currentFile = jumpFile;
    }


    public void playerCollidedWithBall() {
        currentFile = playerCollidedWithBallFile;
    }

    public static void main(String[] args) {
        // The code below and a main method itself is for demonstration purposes, so it shows how we can use it in a future
        SoundEffects sound = new SoundEffects();
        while(true){
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            if(i == 1){
                sound.ballCollidedWithWall();
                System.out.println("BAll");
            }
            else if(i ==2){
                sound.playerCollidedWithBall();
                System.out.println("Player");
            }
        }
    }

    public void stopSoundEffects() {
        effect.stop();
        line.close();
        running = false;
    }

}
