package com.anotherworld.settings;

import com.anotherworld.control.Controller;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Scene;
import com.anotherworld.view.graphics.layout.FixedSpaceLayout;
import com.anotherworld.view.graphics.layout.LobbyLayout;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.TextFieldData;

import java.util.ArrayList;

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

        Scene mainMenuScene = new Scene();
        Scene settingScene = new Scene();
        Scene multiplayerMenuScene = new Scene();
        Scene clientMenuScene = new Scene();
        Scene hostLobbyMenuScene = new Scene();
        Scene connectionFailedScene = new Scene();
        Scene victoryScene = new Scene();
        Scene audioSettingsScene = new Scene();
        Scene videoSettingsScene = new Scene();
        Scene keyBindingScene = new Scene();
        Scene creditScene = new Scene();
        mainMenuScene.addDisplay(this.createMainMenu(victoryScene, creditScene, settingScene, multiplayerMenuScene));
        settingScene.addDisplay(this.createSettingMenu(mainMenuScene, audioSettingsScene, videoSettingsScene, keyBindingScene));
        audioSettingsScene.addDisplay(this.createAudioSettings(settingScene));
        //TODO set video settings scene
        //TODO set key binding scene
        //TODO set credit scene and load from file
        multiplayerMenuScene.addDisplay(this.createMultiplayerMenuDisplay(mainMenuScene, clientMenuScene, hostLobbyMenuScene, connectionFailedScene));
        clientMenuScene.addDisplay(this.createClientMenuDisplay(mainMenuScene, multiplayerMenuScene));
        hostLobbyMenuScene.addDisplay(this.createHostLobbyMenuDisplay(multiplayerMenuScene));
        connectionFailedScene.addDisplay(this.createConnectionFailedDisplay(mainMenuScene));
        victoryScene.addDisplay(this.createVictoryDisplay(mainMenuScene));

        view.switchToScene(mainMenuScene);
        view.setTitle("Quacker Royal");
    }

    private GraphicsDisplay createMainMenu(Scene victoryScene, Scene creditScene, Scene settingsScene, Scene multiplayerMenuScene) {
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
                view.switchToScene(victoryScene);
            });
            logger.debug("Queueing switch to game view");
            view.switchToGameScene();
            x.start();
        });
        layout.addButton(singlePlayer);

        ButtonData multiPlayer = new ButtonData("Multiplayer");
        multiPlayer.setOnAction(() -> view.switchToScene(multiplayerMenuScene));
        layout.addButton(multiPlayer);
        
        ButtonData settings = new ButtonData("Settings");
        settings.setOnAction(() -> view.switchToScene(settingsScene));
        layout.addButton(settings);

        ButtonData credits = new ButtonData("Credits");
        credits.setOnAction(() -> view.switchToScene(creditScene));
        layout.addButton(credits);
        
        ButtonData quit = new ButtonData("Exit");
        quit.setOnAction(() -> view.close());
        layout.addButton(quit);
        
        GraphicsDisplay graphicsDisplay = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay);

        return (graphicsDisplay);

    }

    private GraphicsDisplay createSettingMenu(Scene mainMenuScene, Scene audioSettingsScene, Scene videoSettingsScene, Scene keyBindingScene) {
        logger.debug("Creating settings menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData settings = new ButtonData("Settings");
        layout.addButton(settings);

        ButtonData audioSettings = new ButtonData("Audio Settings");
        audioSettings.setOnAction(() -> view.switchToScene(audioSettingsScene));
        layout.addButton(audioSettings);

        ButtonData videoSettings = new ButtonData("Video Settings");
        videoSettings.setOnAction(() -> view.switchToScene(videoSettingsScene));
        layout.addButton(videoSettings);

        ButtonData keyBindings = new ButtonData("Key Bindings");
        keyBindings.setOnAction(() -> view.switchToScene(keyBindingScene));
        layout.addButton(keyBindings);

        ButtonData backToMenu = new ButtonData("Main Menu");
        backToMenu.setOnAction(() -> view.switchToScene(mainMenuScene));
        layout.addButton(backToMenu);
        
        GraphicsDisplay graphicsDisplay = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay);
        
        return graphicsDisplay;
    }
    
    private GraphicsDisplay createAudioSettings(Scene settingsMenuScene) {
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
        backToSettings.setOnAction(() -> view.switchToScene(settingsMenuScene));
        layout.addButton(backToSettings);
        
        
        GraphicsDisplay audioSettings = new GraphicsDisplay();
        
        layout.enactLayout(audioSettings);
        
        return audioSettings;
    }
    
    private GraphicsDisplay createMultiplayerMenuDisplay(Scene mainMenuScene, Scene clientMenuScene, Scene hostMenuScene, Scene connectionFailedScene) {
        logger.debug("Creating multiplayer menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData multi = new ButtonData("Multiplayer");
        layout.addButton(multi);

        ButtonData buttonHost = new ButtonData("Host");
        buttonHost.setOnAction(() -> {
            // start the game
            Thread x = new Thread(() -> {
                view.switchToScene(hostMenuScene);
                try {
                    control.host();
                } catch (Exception ex) {
                    view.switchToScene(connectionFailedScene);
                }
                
                
            });
            x.start();
        });
        layout.addButton(buttonHost);

        ButtonData buttonClient = new ButtonData("Connect");
        buttonClient.setOnAction(() -> view.switchToScene(clientMenuScene));
        layout.addButton(buttonClient);

        ButtonData backToMenu = new ButtonData("Main menu");
        backToMenu.setOnAction(() -> view.switchToScene(mainMenuScene));
        layout.addButton(backToMenu);

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay);

        return graphicsDisplay;
        
    }
    
    private GraphicsDisplay createClientMenuDisplay(Scene mainMenuScene, Scene multiplayerMenuScene) throws KeyListenerNotFoundException {
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
                control.connect(ipAndPort.getText());
                view.switchToScene(mainMenuScene);
                logger.info("Finished the game");
            });
            x.start();
        });
        layout.addButton(buttonConnect);

        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setOnAction(() -> view.switchToScene(multiplayerMenuScene));
        layout.addButton(backToMulti);

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay4 = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay4);

        return graphicsDisplay4;
    }
    
    private GraphicsDisplay createConnectionFailedDisplay(Scene mainMenuScene) {
        logger.debug("Creating connection failure display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData failureMessage = new ButtonData("Connection to host failed");
        layout.addButton(failureMessage);
        
        ButtonData returnButton = new ButtonData("Return");
        returnButton.setOnAction(() -> {
            view.switchToScene(mainMenuScene);
        });
        layout.addButton(returnButton);
        
        GraphicsDisplay connectionFailedDisplay = new GraphicsDisplay();
        
        layout.enactLayout(connectionFailedDisplay);
        
        return connectionFailedDisplay;
    }
    
    private GraphicsDisplay createClientLobbyMenuDisplay(Scene clientMenuScene) {
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
            view.switchToScene(clientMenuScene);
        });
        layout.addButton(backToMulti);

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay5 = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay5);

        return graphicsDisplay5;
    }
    
    private GraphicsDisplay createVictoryDisplay(Scene mainMenuScene) {
        logger.debug("Creating victory display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData gameOver = new ButtonData("Game Over");
        layout.addButton(gameOver);
        
        ButtonData menuReturn = new ButtonData("Return to menu");
        menuReturn.setOnAction(() -> {
            view.switchToScene(mainMenuScene);
        });
        layout.addButton(menuReturn);
        
        GraphicsDisplay victoryDisplay = new GraphicsDisplay();
        
        layout.enactLayout(victoryDisplay);
        
        return victoryDisplay;
        
    }
    
    private GraphicsDisplay createHostLobbyMenuDisplay(Scene multiplayerMenuScene) {
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
            view.switchToScene(multiplayerMenuScene);
        });
        layout.addButton(backToMulti);

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay5 = new GraphicsDisplay();
        
        layout.enactLayout(graphicsDisplay5);

        return graphicsDisplay5;
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