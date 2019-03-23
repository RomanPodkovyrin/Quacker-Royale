package com.anotherworld.settings;

import com.anotherworld.control.Controller;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.FixedSpaceLayout;
import com.anotherworld.view.View;
import com.anotherworld.view.data.Supplier;
import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Scene;

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

        ButtonData backToMenu = new ButtonData("Menu");
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
            musicButton.setText("MUSIC: " + (musicButton.getText().split(" ")[1].equals("ON") ? "OFF" : "ON"));
            if (musicButton.getText().split(" ")[1].equals("On")) {
                Controller.musicSetting(true);
            } else {
                Controller.musicSetting(false);
            }
        });
        layout.addButton(musicButton);

        ButtonData sfxButton = new ButtonData("SFX: On");
        sfxButton.setOnAction(() -> {
            sfxButton.setText("SFX: " + (sfxButton.getText().split(" ")[1].equals("ON") ? "OFF" : "ON"));
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
        
        ButtonData multi = new ButtonData("Multiplayer");
        multi.setWidth(0.5f);
        multi.setHeight(0.1f);

        ButtonData buttonHost = new ButtonData("Host");
        buttonHost.setWidth(0.5f);
        buttonHost.setHeight(0.1f);
        buttonHost.setBackgroundColour(0.09f, 1f, 0.06f);

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

        ButtonData buttonClient = new ButtonData("Connect");
        buttonClient.setWidth(0.5f);
        buttonClient.setHeight(0.1f);
        buttonClient.setOnAction(() -> view.switchToScene(clientMenuScene));
        buttonClient.setBackgroundColour(0.09f, 1f, 0.06f);

        ButtonData backToMenu2 = new ButtonData("Go to main page");
        backToMenu2.setWidth(0.5f);
        backToMenu2.setHeight(0.1f);
        backToMenu2.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMenu2.setOnAction(() -> view.switchToScene(mainMenuScene));

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay = new GraphicsDisplay();
        multi.setPosition(0f, -0.6f);
        graphicsDisplay.addButton(multi);
        buttonHost.setPosition(0f, -0.2f);
        graphicsDisplay.addButton(buttonHost);
        buttonClient.setPosition(0f, 0.2f);
        graphicsDisplay.addButton(buttonClient);
        backToMenu2.setPosition(0f, 0.6f);
        graphicsDisplay.addButton(backToMenu2);

        return (graphicsDisplay);
        
    }
    
    private GraphicsDisplay createClientMenuDisplay(Scene mainMenuScene, Scene multiplayerMenuScene) throws KeyListenerNotFoundException {
        logger.debug("Creating client menu display");
        ButtonData client = new ButtonData("Please type in the IP and Port of the host and press connect");
        client.setWidth(0.5f);
        client.setHeight(0.1f);

        TextFieldData ipAndPort = new TextFieldData("localhost", view.getAllKeyListener("LOCALHOST"));
        ipAndPort.setWidth(0.5f);
        ipAndPort.setHeight(0.1f);

        ButtonData buttonConnect = new ButtonData("Connect");
        buttonConnect.setWidth(0.5f);
        buttonConnect.setHeight(0.1f);
        buttonConnect.setBackgroundColour(0.09f, 1f, 0.06f);
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

        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setWidth(0.5f);
        backToMulti.setHeight(0.1f);
        backToMulti.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMulti.setOnAction(() -> view.switchToScene(multiplayerMenuScene));

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay4 = new GraphicsDisplay();
        client.setPosition(0f, -0.6f);
        graphicsDisplay4.addButton(client);
        ipAndPort.setPosition(0f, -0.2f);
        graphicsDisplay4.addButton(ipAndPort);
        buttonConnect.setPosition(0f, 0.2f);
        graphicsDisplay4.addButton(buttonConnect);
        backToMulti.setPosition(0f, 0.6f);
        graphicsDisplay4.addButton(backToMulti);

        return (graphicsDisplay4);
    }
    
    private GraphicsDisplay createConnectionFailedDisplay(Scene mainMenuScene) {
        logger.debug("Creating connection failure display");
        ButtonData failureMessage = new ButtonData("Connection to host failed");
        failureMessage.setWidth(0.5f);
        failureMessage.setHeight(0.1f);
        
        ButtonData returnButton = new ButtonData("Return");
        returnButton.setWidth(0.5f);
        returnButton.setHeight(0.1f);
        
        returnButton.setOnAction(() -> {
            view.switchToScene(mainMenuScene);
        });
        
        GraphicsDisplay connectionFailedDisplay = new GraphicsDisplay();
        failureMessage.setPosition(0f, 0.3f);
        connectionFailedDisplay.addButton(failureMessage);
        returnButton.setPosition(0f, -0.3f);
        connectionFailedDisplay.addButton(returnButton);
        
        return connectionFailedDisplay;
    }
    
    private GraphicsDisplay createClientLobbyMenuDisplay(Scene clientMenuScene) {
        logger.debug("Creating client lobby display");
        ButtonData host = new ButtonData("Clienting...");
        host.setWidth(0.5f);
        host.setHeight(0.2f);

        TextListData playerList = new TextListData(4);
        playerList.setWidth(0.5f);
        playerList.setHeight(0.2f);
        
        playerList.addTextSource(() -> {
            return "Yes";
        }, 0);
        playerList.addTextSource(() -> {
            return "No";
        }, 1);
        
        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setWidth(0.5f);
        backToMulti.setHeight(0.1f);
        backToMulti.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMulti.setOnAction(() -> {
            control.disconnect();
            view.switchToScene(clientMenuScene);
        });

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay5 = new GraphicsDisplay();
        playerList.setPosition(0f, -0.6f);
        graphicsDisplay5.addButton(playerList);
        host.setPosition(0f, -0.2f);
        graphicsDisplay5.addButton(host);
        backToMulti.setPosition(0f, 0.2f);
        graphicsDisplay5.addButton(backToMulti);

        return (graphicsDisplay5);
    }
    
    private GraphicsDisplay createVictoryDisplay(Scene mainMenuScene) {
        logger.debug("Creating victory display");
        ButtonData gameOver = new ButtonData("Game Over");
        gameOver.setWidth(0.5f);
        gameOver.setHeight(0.1f);
        
        ButtonData menuReturn = new ButtonData("Return to menu");
        menuReturn.setWidth(0.5f);
        menuReturn.setHeight(0.1f);
        
        menuReturn.setOnAction(() -> {
            view.switchToScene(mainMenuScene);
        });
        
        GraphicsDisplay victoryDisplay = new GraphicsDisplay();
        
        gameOver.setPosition(0, -0.3f);
        victoryDisplay.addButton(gameOver);
        
        menuReturn.setPosition(0, 0.3f);
        victoryDisplay.addButton(menuReturn);
        
        return victoryDisplay;
        
    }
    
    private GraphicsDisplay createHostLobbyMenuDisplay(Scene multiplayerMenuScene) {
        logger.debug("Creating host lobby display");
        ButtonData host = new ButtonData("Hosting...");
        host.setWidth(0.5f);
        host.setHeight(0.1f);

        TextListData playerList = new TextListData(4);
        playerList.setWidth(0.5f);
        playerList.setHeight(0.2f);
        
        //TODO implement actual data
        
        //TODO implement start button?
        
        playerList.addTextSource(() -> {
            return "Yes";
        }, 0);
        playerList.addTextSource(() -> {
            return "No";
        }, 1);
        
        ButtonData backToMulti = new ButtonData("Go back");
        backToMulti.setWidth(0.5f);
        backToMulti.setHeight(0.1f);
        backToMulti.setBackgroundColour(0.09f, 1f, 0.06f);
        backToMulti.setOnAction(() -> {
            control.disconnect();
            view.switchToScene(multiplayerMenuScene);
        });

        // Layout 1 - children are laid out in vertical column
        GraphicsDisplay graphicsDisplay5 = new GraphicsDisplay();
        playerList.setPosition(0f, -0.2f);
        graphicsDisplay5.addButton(playerList);
        host.setPosition(0f, -0.6f);
        graphicsDisplay5.addButton(host);
        backToMulti.setPosition(0f, 0.2f);
        graphicsDisplay5.addButton(backToMulti);

        return (graphicsDisplay5);
    }
    
    public static void main(String[] args) {
        View view = new View(1920 / 2, 1080 / 2);

        // Starting the View thread
        Thread viewThread = new Thread(view);
        viewThread.start();
        logger.info("Starting menu");
        MenuSystem ms = new MenuSystem(view, new Controller(view));
        try {
            ms.start();
        } catch (KeyListenerNotFoundException e) {
            e.printStackTrace();
        }
    }

}