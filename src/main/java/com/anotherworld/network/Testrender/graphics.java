package com.anotherworld.network.Testrender;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class graphics extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bullet Hell Ultimate");

        Group root = new Group();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        Canvas canvas = new Canvas(1000,1000);

        root.getChildren().add(canvas);

        Circle picture = new Circle(100,100,32);
        Sprite player = new Sprite(null,100,100, 0, 0);
        String workingDir = System.getProperty("user.dir");
        System.out.println(workingDir);
        player.setImage("./res/images/alien.png");

        ArrayList<String> keyStream = new ArrayList<String>();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyCode = keyEvent.getCode().toString();
                if(!keyStream.contains(keyCode)){
                    keyStream.add(keyCode);
                }

            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyCode = keyEvent.getCode().toString();
                keyStream.remove(keyCode);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        LongValue lastNanoTime = new LongValue(System.nanoTime());
        new AnimationTimer(){
            public void handle(long currentNanoTime){
                double elapsedTime = (currentNanoTime- lastNanoTime.getValue())/ 1000000000.0;
                lastNanoTime.setValue(currentNanoTime);


                //Logic
                if (keyStream.contains("UP")){
                    System.out.println("GO");
                    player.go();
                }else{
                    player.stop();
                }
                if (keyStream.contains("LEFT")){
                    System.out.println("LEFT");
                    player.rotate(-0.05f);
                }

                if (keyStream.contains("RIGHT")){
                    System.out.println("RIGHT");
                    player.rotate(0.05f);
                }

                gc.clearRect(0, 0, 1000,1000);
                player.update(elapsedTime);
                player.render(gc);


            }
        }.start();

        stage.show();


    }

    public static void main(String args[]){
        launch(args);
    }

}
