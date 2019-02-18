package com.anotherworld.settings;

import com.anotherworld.control.Main;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



import java.awt.Dimension;
import java.awt.Toolkit;

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




        double width = screenSize.getWidth();
        double height = screenSize.getHeight();


        Label label1 = new Label("Welcome to the main page");
        Button button1 = new Button("Go to settings");
        button1.setOnAction(e -> window.setScene(scene2));
        button1.setMinWidth(width * 0.5);
        button1.setMinHeight(height * 0.1);
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(9,100,6), CornerRadii.EMPTY, Insets.EMPTY)));

        Button buttonSinglePlayer = new Button("Play SinglePlayer");
        buttonSinglePlayer.setOnAction(e -> {
            // start the game
            control.startSinglePlayer();
//            window.close();
        });
        buttonSinglePlayer.setMinWidth(width * 0.5);
        buttonSinglePlayer.setMinHeight(height * 0.1);
        buttonSinglePlayer.setBackground(new Background(new BackgroundFill(Color.rgb(9,100,6), CornerRadii.EMPTY, Insets.EMPTY)));

        Button buttonMultiPlayer = new Button("Play MultiPlayer");
        buttonMultiPlayer.setMinWidth(width * 0.5);
        buttonMultiPlayer.setMinHeight(height * 0.1);
        buttonMultiPlayer.setBackground(new Background(new BackgroundFill(Color.rgb(9,100,6), CornerRadii.EMPTY, Insets.EMPTY)));



        // Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.setPadding(new Insets(10, 50, 50, 50));
        layout1.setSpacing(10);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label1, button1,buttonSinglePlayer,buttonMultiPlayer);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout1);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        scene1 = new Scene(borderPane, screenSize.getWidth(), screenSize.getHeight(), Color.BLACK);



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