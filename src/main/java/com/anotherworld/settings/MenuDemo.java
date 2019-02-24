package com.anotherworld.settings;

import com.anotherworld.control.Main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JTextField;

import static javafx.application.Application.launch;

public class MenuDemo extends Application {
    Stage window;
    Scene scene1, scene2;
    private static Main control;

    // public MenuDemo(Main control) {
    // this.control = control;
    //
    // }

    // launch the application
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        final Font font = new Font("Arial", height / 27);

        Label label1 = new Label("Welcome to the main page");
        label1.setFont(font);
        Button button1 = new Button("Go to settings");
        button1.setOnAction(e -> window.setScene(scene2));
        button1.setMinWidth(width * 0.5);
        button1.setMinHeight(height * 0.1);
        button1.setBackground(new Background(new BackgroundFill(Color.rgb(9,
                100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setFont(font);
        Button buttonSinglePlayer = new Button("Play SinglePlayer");
        buttonSinglePlayer.setOnAction(e -> {
            // start the game

                control.startSinglePlayer();
                System.out.println("Finished the game");
                // window.close();
            });
        buttonSinglePlayer.setMinWidth(width * 0.5);
        buttonSinglePlayer.setMinHeight(height * 0.1);
        buttonSinglePlayer.setBackground(new Background(new BackgroundFill(
                Color.rgb(9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonSinglePlayer.setFont(font);

        Button buttonMultiPlayer = new Button("Play MultiPlayer");
        buttonMultiPlayer.setMinWidth(width * 0.5);
        buttonMultiPlayer.setMinHeight(height * 0.1);
        buttonMultiPlayer.setBackground(new Background(new BackgroundFill(Color
                .rgb(9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonMultiPlayer.setFont(font);

        // Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.setPadding(new Insets(10, 50, 50, 50));
        layout1.setSpacing(10);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label1, buttonSinglePlayer,
                buttonMultiPlayer, button1);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(layout1);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK,
                CornerRadii.EMPTY, Insets.EMPTY)));
        scene1 = new Scene(borderPane, screenSize.getWidth(),
                screenSize.getHeight(), Color.BLACK);

        Label setting = new Label("Welcome to settings");
        setting.setFont(font);
        Button backToMenu = new Button("Go to main page");
        backToMenu.setMinWidth(width * 0.5);
        backToMenu.setMinHeight(height * 0.1);
        backToMenu.setBackground(new Background(new BackgroundFill(Color.rgb(9,
                100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        ;
        backToMenu.setOnAction(e -> window.setScene(scene1));
        backToMenu.setFont(font);
        Button musicButton = new Button("Music: On");
        musicButton.setMinWidth(width * 0.5);
        musicButton.setMinHeight(height * 0.1);
        musicButton.setBackground(new Background(new BackgroundFill(Color.rgb(
                9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        musicButton.setOnAction(e -> {
            musicButton.setText("Music: "
                    + (musicButton.getText().split(" ")[1].equals("On") ? "Off"
                            : "On"));
            if (musicButton.getText().split(" ")[1].equals("On")) {
                Main.musicSetting(true);
            } else {
                Main.musicSetting(false);
            }
        });
        musicButton.setFont(font);
        Button sfxButton = new Button("SFX: On");
        sfxButton.setMinWidth(width * 0.5);
        sfxButton.setMinHeight(height * 0.1);
        sfxButton.setBackground(new Background(new BackgroundFill(Color.rgb(9,
                100, 6), CornerRadii.EMPTY, Insets.EMPTY)));

        sfxButton.setOnAction(e -> {
            sfxButton.setText("SFX: "
                    + (sfxButton.getText().split(" ")[1].equals("On") ? "Off"
                            : "On"));
            if (sfxButton.getText().split(" ")[1].equals("On")) {
                Main.sfxSetting(true);
            } else {
                Main.sfxSetting(false);
            }
        });
        sfxButton.setFont(font);
        // Layout 2 - children are laid out in vertical column
        VBox layout2 = new VBox(20);
        layout2.setPadding(new Insets(10, 50, 50, 50));
        layout2.setSpacing(10);
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(setting, musicButton, sfxButton,
                backToMenu);
        BorderPane borderPane2 = new BorderPane();
        borderPane2.setCenter(layout2);
        borderPane2.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        scene2 = new Scene(borderPane2, screenSize.getWidth(),
                screenSize.getHeight(), Color.BLACK);

        Label multi = new Label("Multiplayer");
        multi.setFont(font);
        Button buttonHost = new Button("Host");
        buttonHost.setMinWidth(width * 0.5);
        buttonHost.setMinHeight(height * 0.1);
        buttonHost.setBackground(new Background(new BackgroundFill(Color.rgb(9,
                100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonHost.setFont(font);

        Button buttonClient = new Button("Client");
        buttonClient.setMinWidth(width * 0.5);
        buttonClient.setMinHeight(height * 0.1);
        buttonClient.setBackground(new Background(new BackgroundFill(Color.rgb(
                9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonClient.setFont(font);

        Button backToMenu2 = new Button("Go to main page");
        backToMenu2.setMinWidth(width * 0.5);
        backToMenu2.setMinHeight(height * 0.1);
        backToMenu2.setBackground(new Background(new BackgroundFill(Color.rgb(
                9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        ;
        backToMenu2.setOnAction(e -> window.setScene(scene1));
        backToMenu2.setFont(font);

        // Layout 1 - children are laid out in vertical column
        VBox layout3 = new VBox(20);
        layout3.setPadding(new Insets(10, 50, 50, 50));
        layout3.setSpacing(10);
        layout3.setAlignment(Pos.CENTER);
        layout3.getChildren().addAll(multi, buttonHost, buttonClient,
                backToMenu2);
        BorderPane borderPane3 = new BorderPane();
        borderPane3.setCenter(layout3);
        borderPane3.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene3 = new Scene(borderPane3, screenSize.getWidth(),
                screenSize.getHeight(), Color.BLACK);
        buttonMultiPlayer.setOnAction(e -> window.setScene(scene3));
        
        Label client = new Label(
                "Please type in the IP and Port of the host and press connect");
        multi.setFont(font);
        TextField ipAndPort = new TextField("");
        ipAndPort.setMinWidth(width * 0.5);
        ipAndPort.setMinHeight(height * 0.1);
        ipAndPort.setFont(font);
        Button buttonConnect = new Button("Connect");
        buttonConnect.setMinWidth(width * 0.5);
        buttonConnect.setMinHeight(height * 0.1);
        buttonConnect.setBackground(new Background(new BackgroundFill(Color
                .rgb(9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        buttonConnect.setFont(font);
        buttonConnect.setOnAction(e ->{
            control.connect(ipAndPort.getText());
        });

        Button backToMulti = new Button("Go back");
        backToMulti.setMinWidth(width * 0.5);
        backToMulti.setMinHeight(height * 0.1);
        backToMulti.setBackground(new Background(new BackgroundFill(Color.rgb(
                9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        ;
        backToMulti.setOnAction(e -> window.setScene(scene3));
        backToMulti.setFont(font);

        // Layout 1 - children are laid out in vertical column
        VBox layout4 = new VBox(20);
        layout4.setPadding(new Insets(10, 50, 50, 50));
        layout4.setSpacing(10);
        layout4.setAlignment(Pos.CENTER);
        layout4.getChildren().addAll(client, ipAndPort, buttonConnect,
                backToMulti);
        BorderPane borderPane4 = new BorderPane();
        borderPane4.setCenter(layout4);
        borderPane4.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene4 = new Scene(borderPane4, screenSize.getWidth(),
                screenSize.getHeight(), Color.BLACK);
        buttonClient.setOnAction(e -> window.setScene(scene4));
        
        Label host = new Label("Hosting...");
        host.setFont(font);

        Button backToMulti2 = new Button("Go back");
        backToMulti2.setMinWidth(width * 0.5);
        backToMulti2.setMinHeight(height * 0.1);
        backToMulti2.setBackground(new Background(new BackgroundFill(Color.rgb(
                9, 100, 6), CornerRadii.EMPTY, Insets.EMPTY)));
        ;
        backToMulti2.setOnAction(e -> window.setScene(scene3));
        backToMulti2.setFont(font);

        // Layout 1 - children are laid out in vertical column
        VBox layout5 = new VBox(20);
        layout5.setPadding(new Insets(10, 50, 50, 50));
        layout5.setSpacing(10);
        layout5.setAlignment(Pos.CENTER);
        layout5.getChildren().addAll(host, backToMulti2);
        BorderPane borderPane5 = new BorderPane();
        borderPane5.setCenter(layout5);
        borderPane5.setBackground(new Background(new BackgroundFill(
                Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene5 = new Scene(borderPane5, screenSize.getWidth(),
                screenSize.getHeight(), Color.BLACK);

        buttonHost.setOnAction(e -> {
            window.setScene(scene5);
            System.out.println("Pressed host");
            control.host();

        });

        window.setScene(scene1);
        window.setTitle("application");
        window.show();
    }

    public static void main(String args[]) {
        control = new Main();
        // launch the application
        launch(args);
    }
}