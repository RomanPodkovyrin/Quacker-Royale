package com.anotherworld.settings;

import com.anotherworld.control.Controller;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.Programme;
import com.anotherworld.view.View;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Scene;

import com.anotherworld.view.input.Button;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.TextFieldData;

public class MenuSystem {
    private Scene scene1;
    private Scene scene2;
    private Controller control;
    private View view;
    
    public MenuSystem(View view, Controller control) {
        this.view = view;
        this.control = control;
    }
    
    public void start() {
        
        //TODO i think all the menus are rendered upside down
        
        control = new Controller(view);
        
        // launch the application

        //final Font font = new Font("Arial", height / 27);

        ButtonData label1 = new ButtonData("Welcome to the main page");
        label1.setWidth(0.5f);
        label1.setHeight(0.1f);
        ButtonData button1 = new ButtonData("Go to settings");
        button1.setOnAction(e -> view.switchToScene(scene2));
        button1.setWidth(0.5f);
        button1.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        
        ButtonData buttonSinglePlayer = new ButtonData("Play SinglePlayer");
        buttonSinglePlayer.setOnAction(e -> {
            // start the game
            view.switchToGameScene();
            control.startSinglePlayer();
            //TODO switch this to logging
            System.out.println("Finished the game");
        });
        buttonSinglePlayer.setWidth(0.5f);
        buttonSinglePlayer.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);

        ButtonData buttonMultiPlayer = new ButtonData("Play MultiPlayer");
        buttonMultiPlayer.setWidth(0.5f);
        buttonMultiPlayer.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);

        // Layout 1 - children are laid out in vertical column
        scene1 = new Scene();
        GraphicsDisplay graphicsDisplay1 = new GraphicsDisplay();
        label1.setPosition(0f, -0.6f);
        graphicsDisplay1.addButton(label1);
        buttonSinglePlayer.setPosition(0f, -0.2f);
        graphicsDisplay1.addButton(buttonSinglePlayer);
        buttonMultiPlayer.setPosition(0f, 0.2f);
        graphicsDisplay1.addButton(buttonMultiPlayer);
        button1.setPosition(0f, 0.6f);
        graphicsDisplay1.addButton(button1);
        
        scene1.addDisplay(graphicsDisplay1);

        ButtonData setting = new ButtonData("Welcome to settings");
        
        ButtonData backToMenu = new ButtonData("Go to main page");
        backToMenu.setWidth(0.5f);
        backToMenu.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMenu.setOnAction(e -> view.switchToScene(scene1));
        
        ButtonData musicButton = new ButtonData("Music: On");
        musicButton.setWidth(0.5f);
        musicButton.setHeight( 0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        musicButton.setOnAction(e -> {
            musicButton.setText("Music: "
                    + (musicButton.getText().split(" ")[1].equals("On") ? "Off"
                            : "On"));
            if (musicButton.getText().split(" ")[1].equals("On")) {
                Controller.musicSetting(true);
            } else {
                Controller.musicSetting(false);
            }
        });
        
        ButtonData sfxButton = new ButtonData("SFX: On");
        sfxButton.setWidth(0.5f);
        sfxButton.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);

        sfxButton.setOnAction(e -> {
            sfxButton.setText("SFX: "
                    + (sfxButton.getText().split(" ")[1].equals("On") ? "Off"
                            : "On"));
            if (sfxButton.getText().split(" ")[1].equals("On")) {
                Controller.sfxSetting(true);
            } else {
                Controller.sfxSetting(false);
            }
        });
        
        // Layout 2 - children are laid out in vertical column
        scene2 = new Scene();
        GraphicsDisplay graphicsDisplay2 = new GraphicsDisplay();
        setting.setPosition(0f, -0.6f);
        graphicsDisplay2.addButton(setting);
        musicButton.setPosition(0f, -0.2f);
        graphicsDisplay2.addButton(musicButton);
        sfxButton.setPosition(0f, 0.2f);
        graphicsDisplay2.addButton(sfxButton);
        backToMenu.setPosition(0f, 0.6f);
        graphicsDisplay2.addButton(backToMenu);
        
        scene2.addDisplay(graphicsDisplay2);

        ButtonData multi = new ButtonData("Multiplayer");
        
        ButtonData buttonHost = new ButtonData("Host");
        buttonHost.setWidth(0.5f);
        buttonHost.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);

        ButtonData buttonClient = new ButtonData("Client");
        buttonClient.setWidth(0.5f);
        buttonClient.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);

        ButtonData backToMenu2 = new ButtonData("Go to main page");
        backToMenu2.setWidth(0.5f);
        backToMenu2.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMenu2.setOnAction(e -> view.switchToScene(scene1));

        // Layout 1 - children are laid out in vertical column
        Scene scene3 = new Scene();
        GraphicsDisplay graphicsDisplay3 = new GraphicsDisplay();
        multi.setPosition(0f, -0.6f);
        graphicsDisplay3.addButton(multi);
        buttonHost.setPosition(0f, -0.2f);
        graphicsDisplay3.addButton(buttonHost);
        buttonClient.setPosition(0f, 0.2f);
        graphicsDisplay3.addButton(buttonClient);
        backToMenu2.setPosition(0f, 0.6f);
        graphicsDisplay3.addButton(backToMenu2);
        
        scene3.addDisplay(graphicsDisplay3);
        
        buttonMultiPlayer.setOnAction(e -> view.switchToScene(scene3));
        
        ButtonData client = new ButtonData("Please type in the IP and Port of the host and press connect");
        
        TextFieldData ipAndPort = new TextFieldData("localhost", view.getAllKeyListener()); //TODO oof
        ipAndPort.setWidth(0.5f);
        ipAndPort.setHeight(0.1f);
        
        ButtonData buttonConnect = new ButtonData("Connect");
        buttonConnect.setWidth(0.5f);
        buttonConnect.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        buttonConnect.setOnAction(e ->{
            control.connect(ipAndPort.getText());
        });

        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setWidth(0.5f);
        backToMulti.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMulti.setOnAction(e -> view.switchToScene(scene3));

        // Layout 1 - children are laid out in vertical column
        Scene scene4 = new Scene();
        GraphicsDisplay graphicsDisplay4 = new GraphicsDisplay();
        client.setPosition(0f, -0.6f);
        graphicsDisplay4.addButton(client);
        ipAndPort.setPosition(0f, -0.2f);
        graphicsDisplay4.addButton(ipAndPort);
        buttonConnect.setPosition(0f, 0.2f);
        graphicsDisplay4.addButton(buttonConnect);
        backToMulti.setPosition(0f, 0.6f);
        graphicsDisplay4.addButton(backToMulti);
        
        scene4.addDisplay(graphicsDisplay4);
        
        buttonClient.setOnAction(e -> view.switchToScene(scene4));
        
        ButtonData host = new ButtonData("Hosting...");

        ButtonData backToMulti2 = new ButtonData("Go back");
        backToMulti2.setWidth(0.5f);
        backToMulti2.setHeight(0.1f);
        button1.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMulti2.setOnAction(e -> view.switchToScene(scene3));

        // Layout 1 - children are laid out in vertical column
        Scene scene5 = new Scene();
        GraphicsDisplay graphicsDisplay5 = new GraphicsDisplay();
        host.setPosition(0f, -0.2f);
        graphicsDisplay5.addButton(host);
        backToMulti2.setPosition(0f, 0.2f);
        graphicsDisplay5.addButton(backToMulti2);
        
        scene5.addDisplay(graphicsDisplay5);
        
        buttonHost.setOnAction(e -> {
            control.host();
            view.switchToScene(scene5);
        });

        view.switchToScene(scene1);
        view.setTitle("Bullet Hell");
    }

    public static void main(String[] args) {
        View view = new View(1920 / 2, 1080 / 2);


        // Starting the View thread
        Thread viewThread = new Thread(view);
        viewThread.start();
        System.out.println("Starting menu");
        MenuSystem ms = new MenuSystem(view, new Controller(view));
        ms.start();
    }
    
}