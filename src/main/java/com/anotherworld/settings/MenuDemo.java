package com.anotherworld.settings;

import com.anotherworld.control.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

import static javafx.application.Application.launch;

public class MenuDemo extends Application {
    Stage window;
    Scene scene1, scene2;
    private static Main control;

//    public MenuDemo(Main control) {
//        this.control = control;
//
//    }

    // launch the application
    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Label label1 = new Label("Welcome to the main page");
        Button button1 = new Button("Go to settings");
        button1.setOnAction(e -> window.setScene(scene2));
        Button buttonSinglePlayer = new Button("Play SinglePlayer");
        buttonSinglePlayer.setOnAction(e -> {
            // start the game
            control.startSinglePlayer();
            window.close();
        });

        Button buttonMultiPlayer = new Button("Play MultiPlayer");



        // Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1,buttonSinglePlayer,buttonMultiPlayer);
        scene1 = new Scene(layout1, screenSize.getWidth(), screenSize.getHeight());



        Label label2 = new Label("Welcome to settings");
        Button button2 = new Button("Go to main page");
        button2.setOnAction(e -> window.setScene(scene1));


        // Layout 2 - children are laid out in vertical column
        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(label2, button2);
        scene2 = new Scene(layout2, screenSize.getWidth(), screenSize.getHeight());


        window.setScene(scene1);
        window.setTitle("application");
        window.show();
    }

    public static void main(String args[])
    {
        control = new Main();
        // launch the application
        launch(args);
    }
}