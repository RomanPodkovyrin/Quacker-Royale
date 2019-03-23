package com.anotherworld.settings;

import com.anotherworld.control.Controller;
import com.anotherworld.control.exceptions.ConnectionClosed;
import com.anotherworld.control.exceptions.NoHostFound;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Scene;
import com.anotherworld.view.graphics.layout.FixedSpaceLayout;
import com.anotherworld.view.graphics.layout.Layout;
import com.anotherworld.view.graphics.layout.LobbyLayout;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.TextFieldData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuSystem {
    private Controller control;
    private View view;

    private static Logger logger = LogManager.getLogger(MenuSystem.class);

    public MenuSystem(View view, Controller control) {
        this.view = view;
        this.control = control;
    }

    public void start() throws KeyListenerNotFoundException {

        // TODO Change throw to menucouldnotbeinitialised or similar

        control = new Controller(view);

        GraphicsDisplay mainMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay settingsDisplay = new GraphicsDisplay();
        GraphicsDisplay multiplayerMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay clientMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay hostLobbyMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay connectionFailedDisplay = new GraphicsDisplay();
        GraphicsDisplay victoryDisplay = new GraphicsDisplay();
        GraphicsDisplay audioSettingsDisplay = new GraphicsDisplay();
        GraphicsDisplay videoSettingsDisplay = new GraphicsDisplay();
        GraphicsDisplay keyBindingDisplay = new GraphicsDisplay();
        GraphicsDisplay creditDisplay = new GraphicsDisplay();
        this.createMainMenu(victoryDisplay, creditDisplay, settingsDisplay, multiplayerMenuDisplay).enactLayout(mainMenuDisplay);
        this.createSettingMenu(mainMenuDisplay, audioSettingsDisplay, videoSettingsDisplay, keyBindingDisplay).enactLayout(settingsDisplay);
        this.createAudioSettings(settingsDisplay).enactLayout(audioSettingsDisplay);
        //TODO set video settings scene
        //TODO set key binding scene
        //TODO set credit scene and load from file
        this.createMultiplayerMenuDisplay(mainMenuDisplay, clientMenuDisplay, hostLobbyMenuDisplay, connectionFailedDisplay).enactLayout(multiplayerMenuDisplay);
        this.createClientMenuDisplay(mainMenuDisplay, multiplayerMenuDisplay, connectionFailedDisplay).enactLayout(clientMenuDisplay);
        this.createHostLobbyMenuDisplay(multiplayerMenuDisplay).enactLayout(hostLobbyMenuDisplay);
        this.createConnectionFailedDisplay(mainMenuDisplay).enactLayout(connectionFailedDisplay);
        this.createVictoryDisplay(mainMenuDisplay).enactLayout(victoryDisplay);

        view.switchToDisplay(mainMenuDisplay);
        view.setTitle("Quacker Royal");
    }

    private Layout createMainMenu(GraphicsDisplay victoryDisplay, GraphicsDisplay creditDisplay, GraphicsDisplay settingsDisplay, GraphicsDisplay multiplayerMenuDisplay) {
        logger.debug("Creating main menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData label1 = new ButtonData("Quacker Royal");
        layout.addButton(label1);

        ButtonData singlePlayer = new ButtonData("SinglePlayer");
        singlePlayer.setOnAction(() -> {
            logger.trace("Single player button pressed");
            Thread x = new Thread(() -> {
                logger.trace("Starting singleplayer");
                control.startSinglePlayer();
                logger.trace("Queueing switch to victory scene");
                view.switchToDisplay(victoryDisplay);
            });
            logger.debug("Queueing switch to game view");
            view.switchToGameScene();
            x.start();
        });
        layout.addButton(singlePlayer);

        ButtonData multiPlayer = new ButtonData("Multiplayer");
        multiPlayer.setOnAction(() -> view.switchToDisplay(multiplayerMenuDisplay));
        layout.addButton(multiPlayer);
        
        ButtonData settings = new ButtonData("Settings");
        settings.setOnAction(() -> view.switchToDisplay(settingsDisplay));
        layout.addButton(settings);

        ButtonData credits = new ButtonData("Credits");
        credits.setOnAction(() -> view.switchToDisplay(creditDisplay));
        layout.addButton(credits);
        
        ButtonData quit = new ButtonData("Exit");
        quit.setOnAction(() -> view.close());
        layout.addButton(quit);

        return layout;

    }

    private Layout createSettingMenu(GraphicsDisplay mainMenuDisplay, GraphicsDisplay audioSettingsDisplay, GraphicsDisplay videoSettingsDisplay, GraphicsDisplay keyBindingDisplay) {
        logger.debug("Creating settings menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData settings = new ButtonData("Settings");
        layout.addButton(settings);

        ButtonData audioSettings = new ButtonData("Audio Settings");
        audioSettings.setOnAction(() -> view.switchToDisplay(audioSettingsDisplay));
        layout.addButton(audioSettings);

        ButtonData videoSettings = new ButtonData("Video Settings");
        videoSettings.setOnAction(() -> view.switchToDisplay(videoSettingsDisplay));
        layout.addButton(videoSettings);

        ButtonData keyBindings = new ButtonData("Key Bindings");
        keyBindings.setOnAction(() -> view.switchToDisplay(keyBindingDisplay));
        layout.addButton(keyBindings);

        ButtonData backToMenu = new ButtonData("Main Menu");
        backToMenu.setOnAction(() -> view.switchToDisplay(mainMenuDisplay));
        layout.addButton(backToMenu);
        
        return layout;
    }
    
    private Layout createAudioSettings(GraphicsDisplay settingsMenuDisplay) {
        logger.debug("Creating audio settings menu display");

        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData audioTitle = new ButtonData("Audio Settings");
        layout.addButton(audioTitle);

        ButtonData musicButton = new ButtonData("Music: On");
        musicButton.setOnAction(() -> {
            logger.info("Music button pressed");
            musicButton.setText("Music: " + (musicButton.getText().split(" ")[1].equals("On") ? "Off" : "On"));
            if (musicButton.getText().split(" ")[1].equals("ON")) {
                Controller.musicSetting(true);
            } else {
                Controller.musicSetting(false);
            }
        });
        layout.addButton(musicButton);

        ButtonData sfxButton = new ButtonData("SFX: On");
        sfxButton.setOnAction(() -> {
            sfxButton.setText("SFX: " + (sfxButton.getText().split(" ")[1].equals("On") ? "Off" : "On"));
            if (sfxButton.getText().split(" ")[1].equals("ON")) {
                Controller.sfxSetting(true);
            } else {
                Controller.sfxSetting(false);
            }
        });
        layout.addButton(sfxButton);

        ButtonData backToSettings = new ButtonData("Settings");
        backToSettings.setOnAction(() -> view.switchToDisplay(settingsMenuDisplay));
        layout.addButton(backToSettings);
        
        return layout;
    }
    
    private Layout createMultiplayerMenuDisplay(GraphicsDisplay mainMenuDisplay, GraphicsDisplay clientMenuDisplay, GraphicsDisplay hostMenuDisplay, GraphicsDisplay connectionFailedDisplay) {
        logger.debug("Creating multiplayer menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData multi = new ButtonData("Multiplayer");
        layout.addButton(multi);

        ButtonData buttonHost = new ButtonData("Host");
        buttonHost.setOnAction(() -> {
            // start the game
            Thread x = new Thread(() -> {
                view.switchToDisplay(hostMenuDisplay);
                try {
                    control.host();
                } catch (Exception ex) { //TODO custom exception
                    //TODO switch to better display
                    view.switchToDisplay(connectionFailedDisplay);
                }
                
                
            });
            x.start();
        });
        layout.addButton(buttonHost);

        ButtonData buttonClient = new ButtonData("Connect");
        buttonClient.setOnAction(() -> view.switchToDisplay(clientMenuDisplay));
        layout.addButton(buttonClient);

        ButtonData backToMenu = new ButtonData("Main menu");
        backToMenu.setOnAction(() -> view.switchToDisplay(mainMenuDisplay));
        layout.addButton(backToMenu);

        return layout;
        
    }
    
    private Layout createClientMenuDisplay(GraphicsDisplay mainMenuDisplay, GraphicsDisplay multiplayerMenuDisplay, GraphicsDisplay connectionFailedDisplay) throws KeyListenerNotFoundException {
        logger.debug("Creating client menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData client = new ButtonData("Please type in the IP and Port of the host and press connect");
        layout.addButton(client);

        TextFieldData ipAndPort = new TextFieldData("localhost", view.getStringKeyListener("LOCALHOST"));
        layout.addButton(ipAndPort);

        ButtonData buttonConnect = new ButtonData("Connect");
        buttonConnect.setOnAction(() -> {
            Thread x = new Thread(() -> {
                logger.info("Starting game");
                view.switchToGameScene();
                try {
                    control.connect(ipAndPort.getText());
                    view.switchToDisplay(mainMenuDisplay);
                } catch (NoHostFound | ConnectionClosed e) {
                    view.switchToDisplay(connectionFailedDisplay);
                }
                logger.info("Finished the game");
            });
            x.start();
        });
        layout.addButton(buttonConnect);

        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setOnAction(() -> view.switchToDisplay(multiplayerMenuDisplay));
        layout.addButton(backToMulti);

        return layout;
    }
    
    private Layout createConnectionFailedDisplay(GraphicsDisplay mainMenuDisplay) {
        logger.debug("Creating connection failure display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData failureMessage = new ButtonData("Connection to host failed");
        layout.addButton(failureMessage);
        
        ButtonData returnButton = new ButtonData("Return");
        returnButton.setOnAction(() -> {
            view.switchToDisplay(mainMenuDisplay);
        });
        layout.addButton(returnButton);
        
        return layout;
    }
    
    private Layout createClientLobbyMenuDisplay(GraphicsDisplay clientMenuDisplay) {
        logger.debug("Creating client lobby display");
        
        LobbyLayout layout = new LobbyLayout(0.2f);
        
        ButtonData host = new ButtonData("Clienting...");
        layout.addButton(host);

        TextListData playerList = new TextListData(4);
        playerList.setWidth(0.5f);
        playerList.setHeight(0.4f);
        
        playerList.addTextSource(() -> {
            return "Yes";
        }, 0);
        playerList.addTextSource(() -> {
            return "No";
        }, 1);
        layout.addList(playerList);
        
        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setOnAction(() -> {
            control.disconnect();
            view.switchToDisplay(clientMenuDisplay);
        });
        layout.addButton(backToMulti);

        return layout;
    }
    
    private Layout createVictoryDisplay(GraphicsDisplay mainMenuDisplay) {
        logger.debug("Creating victory display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData gameOver = new ButtonData("Game Over");
        layout.addButton(gameOver);
        
        ButtonData menuReturn = new ButtonData("Return to menu");
        menuReturn.setOnAction(() -> {
            view.switchToDisplay(mainMenuDisplay);
        });
        layout.addButton(menuReturn);
        
        return layout;
        
    }
    
    private Layout createHostLobbyMenuDisplay(GraphicsDisplay multiplayerMenuDisplay) {
        logger.debug("Creating host lobby display");
        
        LobbyLayout layout = new LobbyLayout(0.2f);
        
        ButtonData host = new ButtonData("Hosting...");
        layout.addButton(host);

        TextListData playerList = new TextListData(4);
        //TODO implement actual data
        
        //TODO implement start button?
        
        playerList.addTextSource(() -> {
            return "Yes";
        }, 0);
        playerList.addTextSource(() -> {
            return "No";
        }, 1);
        layout.addList(playerList);
        
        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setOnAction(() -> {
            control.disconnect();
            view.switchToDisplay(multiplayerMenuDisplay);
        });
        layout.addButton(backToMulti);

        return layout;
    }
    
    public static void main(String[] args) {
        View view = new View(1920 / 2, 1080 / 2);

        // Starting the View thread
        Thread viewThread = new Thread(view);
        viewThread.start();
        logger.info("Starting menu");
        MenuSystem ms = new MenuSystem(view, new Controller(view));
        try { //TODO Change this to logging
            ms.start();
        } catch (KeyListenerNotFoundException e) {
            e.printStackTrace();
        }
    }

}