package com.anotherworld.control;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.settings.Difficulty;
import com.anotherworld.settings.DisplayType;
import com.anotherworld.settings.GameSettings;
import com.anotherworld.settings.KeySettings;
import com.anotherworld.settings.ViewSettings;
import com.anotherworld.tools.Wrapper;
import com.anotherworld.tools.exceptions.ConnectionClosed;
import com.anotherworld.tools.exceptions.NoHostFound;
import com.anotherworld.tools.input.KeyListenerNotFoundException;
import com.anotherworld.view.View;
import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.VictoryDisplay;
import com.anotherworld.view.graphics.layout.CreditLayout;
import com.anotherworld.view.graphics.layout.FixedSpaceLayout;
import com.anotherworld.view.graphics.layout.Layout;
import com.anotherworld.view.graphics.layout.LobbyLayout;
import com.anotherworld.view.graphics.layout.VictoryLayout;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.TextFieldData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuSystem {
    private Controller control;
    private View view;

    private static Logger logger = LogManager.getLogger(MenuSystem.class);
    
    private static final String BACK = "Back";

    public MenuSystem(View view, Controller control) {
        this.view = view;
        this.control = control;
    }

    /**
     * Creates and runs the menu using the view.
     * @throws KeyListenerNotFoundException If the view objects can't be created for the menu
     */
    public void start() throws KeyListenerNotFoundException {

        // TODO Change throw to menucouldnotbeinitialised or similar

        control = new Controller(view);

        GraphicsDisplay mainMenuDisplay = new GraphicsDisplay();
        VictoryDisplay singlePlayerSettings = new VictoryDisplay();
        VictoryDisplay hostSettings = new VictoryDisplay();
        GraphicsDisplay settingsMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay multiplayerMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay clientMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay hostLobbyMenuDisplay = new GraphicsDisplay();
        GraphicsDisplay connectionFailedDisplay = new GraphicsDisplay();
        VictoryDisplay victoryDisplay = new VictoryDisplay();
        GraphicsDisplay audioSettingsDisplay = new GraphicsDisplay();
        GraphicsDisplay videoSettingsDisplay = new GraphicsDisplay();
        GraphicsDisplay keyBindingDisplay = new GraphicsDisplay();
        GraphicsDisplay creditDisplay = new GraphicsDisplay();
        GraphicsDisplay clientLobbyDisplay = new GraphicsDisplay();
        ClientLobbyWaitThread thread = new ClientLobbyWaitThread(() -> control.getServerStarted(), () -> view.switchToGameScene());
        
        this.createMainMenu(singlePlayerSettings, creditDisplay, settingsMenuDisplay, multiplayerMenuDisplay).enactLayout(mainMenuDisplay);
        this.createSettingMenu(mainMenuDisplay, audioSettingsDisplay, videoSettingsDisplay, keyBindingDisplay).enactLayout(settingsMenuDisplay);
        this.createAudioSettings(settingsMenuDisplay).enactLayout(audioSettingsDisplay);
        this.createViewSettings(settingsMenuDisplay).enactLayout(videoSettingsDisplay);
        this.createKeybindingSettings(settingsMenuDisplay).enactLayout(keyBindingDisplay);
        this.createCreditDisplay(mainMenuDisplay).enactLayout(creditDisplay);
        this.createSinglePlayerGameSettings(mainMenuDisplay, victoryDisplay).enactLayout(singlePlayerSettings);
        this.createMultiplayerGameSettings(multiplayerMenuDisplay, hostLobbyMenuDisplay, connectionFailedDisplay, victoryDisplay).enactLayout(hostSettings);
        this.createClientLobbyMenuDisplay(clientMenuDisplay, thread).enactLayout(clientLobbyDisplay);
        this.createMultiplayerMenuDisplay(mainMenuDisplay, clientMenuDisplay, hostSettings).enactLayout(multiplayerMenuDisplay);
        this.createClientMenuDisplay(mainMenuDisplay, multiplayerMenuDisplay, connectionFailedDisplay, clientLobbyDisplay, victoryDisplay, thread).enactLayout(clientMenuDisplay);
        this.createHostLobbyMenuDisplay(multiplayerMenuDisplay).enactLayout(hostLobbyMenuDisplay);
        this.createConnectionFailedDisplay(mainMenuDisplay).enactLayout(connectionFailedDisplay);
        this.createVictoryDisplay(mainMenuDisplay).enactLayout(victoryDisplay);

        view.switchToDisplay(mainMenuDisplay);
        view.setTitle("Quacker Royale");
        
        while (view.windowOpen()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        thread.cancleWait();
        
    }

    private Layout createMainMenu(GraphicsDisplay singlePlayerLobby, GraphicsDisplay creditDisplay, GraphicsDisplay settingsDisplay, GraphicsDisplay multiplayerMenuDisplay) {
        logger.debug("Creating main menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData label1 = new ButtonData("Quacker Royale");
        layout.addButton(label1);

        ButtonData singlePlayer = new ButtonData("SinglePlayer");
        singlePlayer.setOnAction(() -> {
            logger.trace("Single player button pressed");
            view.switchToDisplay(singlePlayerLobby);
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

        ButtonData backToMenu = new ButtonData(BACK);
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
                AudioControl.setMusicOn(true);
            } else {
                AudioControl.setMusicOn(false);
            }
        });
        layout.addButton(musicButton);

        ButtonData sfxButton = new ButtonData("SFX: On");
        sfxButton.setOnAction(() -> {
            sfxButton.setText("SFX: " + (sfxButton.getText().split(" ")[1].equals("On") ? "Off" : "On"));
            if (sfxButton.getText().split(" ")[1].equals("ON")) {
                AudioControl.setEffectsOn(true);
            } else {
                AudioControl.setEffectsOn(false);
            }
        });
        layout.addButton(sfxButton);

        ButtonData backToSettings = new ButtonData(BACK);
        backToSettings.setOnAction(() -> view.switchToDisplay(settingsMenuDisplay));
        layout.addButton(backToSettings);
        
        return layout;
    }

    private Layout createKeybindingSettings(GraphicsDisplay settingsMenuDisplay) {
        logger.debug("Creating key bindings settings menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData keyBindingsTitle = new ButtonData("Key Bindings");
        layout.addButton(keyBindingsTitle);
        
        ButtonData muteButton = new ButtonData(() -> {
            return "MUTE: " + KeySettings.getKeyString(KeySettings.getMute());  
        }, false);
        
        muteButton.setOnAction(() -> {
            logger.info("charge key button pressed");
            KeySettings.setMute(view.getBindableKey());
        });
        layout.addButton(muteButton);
        
        ButtonData upButton = new ButtonData(() -> {
            return "UP: " + KeySettings.getKeyString(KeySettings.getUp());  
        }, false);
        
        upButton.setOnAction(() -> {
            logger.info("up key button pressed");
            KeySettings.setUp(view.getBindableKey());
        });
        layout.addButton(upButton);
        
        ButtonData downButton = new ButtonData(() -> {
            return "DOWN: " + KeySettings.getKeyString(KeySettings.getDown());  
        }, false);
        
        downButton.setOnAction(() -> {
            logger.info("down key button pressed");
            KeySettings.setDown(view.getBindableKey());
        });
        layout.addButton(downButton);
        
        ButtonData leftButton = new ButtonData(() -> {
            return "LEFT: " + KeySettings.getKeyString(KeySettings.getLeft());  
        }, false);
        
        leftButton.setOnAction(() -> {
            logger.info("left key button pressed");
            KeySettings.setLeft(view.getBindableKey());
        });
        layout.addButton(leftButton);
        
        ButtonData rightButton = new ButtonData(() -> {
            return "RIGHT: " + KeySettings.getKeyString(KeySettings.getRight());  
        }, false);
        
        rightButton.setOnAction(() -> {
            logger.info("key button pressed");
            KeySettings.setRight(view.getBindableKey());
        });
        layout.addButton(rightButton);
        
        ButtonData chargeButton = new ButtonData(() -> {
            return "CHARGE: " + KeySettings.getKeyString(KeySettings.getCharge());  
        }, false);
        
        chargeButton.setOnAction(() -> {
            logger.info("charge key button pressed");
            KeySettings.setCharge(view.getBindableKey());
        });
        layout.addButton(chargeButton);

        ButtonData backToSettings = new ButtonData(BACK);
        backToSettings.setOnAction(() -> view.switchToDisplay(settingsMenuDisplay));
        layout.addButton(backToSettings);
        
        return layout;
    }

    private Layout createViewSettings(GraphicsDisplay settingsMenuDisplay) {
        logger.debug("Creating key bindings settings menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData keyBindingsTitle = new ButtonData("Display Settings");
        layout.addButton(keyBindingsTitle);
        
        final Wrapper<DisplayType> displayType = new Wrapper<>(ViewSettings.getDisplayType());;
        
        ButtonData displayTypeButton = new ButtonData(() -> {
            switch (displayType.getValue()) {
                case FULLSCREEN:
                    return "FULLSCREEN";
                case WINDOWED:
                    return "WINDOWED";
                default:
                    return "UNKNOWN DISPLAY TYPE";
            }
        }, false);
        
        displayTypeButton.setOnAction(() -> {
            logger.info("Change display type button pressed");
            switch (displayType.getValue()) {
                case FULLSCREEN:
                    displayType.setValue(DisplayType.WINDOWED);
                    break;
                case WINDOWED:
                    displayType.setValue(DisplayType.FULLSCREEN);
                    break;
                default:
                    displayType.setValue(DisplayType.WINDOWED);
            }
        });
        layout.addButton(displayTypeButton);
        
        final Wrapper<Integer> height = new Wrapper<>(ViewSettings.getHeight());
        final Wrapper<Integer> width = new Wrapper<>(ViewSettings.getWidth());
        ButtonData resolutionButton = new ButtonData(() -> width.getValue() + "X" + height.getValue(), false);
        
        
        resolutionButton.setOnAction(() -> {
            logger.info("Change display type button pressed");
            switch (resolutionButton.getText()) {
                case "1920X1080":
                    width.setValue(960);
                    height.setValue(540);
                    break;
                case "960X540":
                    width.setValue(3200);
                    height.setValue(1800);
                    break;
                case "3200X1800":
                    width.setValue(1920);
                    height.setValue(1080);
                    break;
                default:
                    width.setValue(1920);
                    height.setValue(1080);
            }
        });
        
        layout.addButton(resolutionButton);
        final Wrapper<Integer> frameRate = new Wrapper<>(ViewSettings.getRefreshRate());
        ButtonData frameRateButton = new ButtonData(() -> "Refresh rate: " + frameRate.getValue(), false);
        
        
        frameRateButton.setOnAction(() -> {
            logger.info("Change display type button pressed");
            switch (frameRate.getValue()) {
                case 30:;
                    frameRate.setValue(60);
                    break;
                case 60:
                    frameRate.setValue(120);
                    break;
                case 120:
                    frameRate.setValue(30);
                    break;
                default:
                    frameRate.setValue(60);
            }
        });
        layout.addButton(frameRateButton);

        ButtonData applyChanges = new ButtonData("Apply Changes");
        applyChanges.setOnAction(() -> {
            ViewSettings.setWidth(width.getValue());
            ViewSettings.setHeight(height.getValue());
            ViewSettings.setDisplayType(displayType.getValue());
            ViewSettings.getSetRefreshRate(frameRate.getValue());
            view.reloadWindow();
        });
        layout.addButton(applyChanges);

        ButtonData backToSettings = new ButtonData(BACK);
        backToSettings.setOnAction(() -> {
            view.switchToDisplay(settingsMenuDisplay);
            width.setValue(ViewSettings.getWidth());
            height.setValue(ViewSettings.getHeight());
            displayType.setValue(ViewSettings.getDisplayType());
        });
        layout.addButton(backToSettings);
        
        return layout;
    }
    
    private Layout createMultiplayerMenuDisplay(GraphicsDisplay mainMenuDisplay, GraphicsDisplay clientMenuDisplay, GraphicsDisplay hostMenuDisplay) {
        logger.debug("Creating multiplayer menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData multi = new ButtonData("Multiplayer");
        layout.addButton(multi);

        ButtonData buttonHost = new ButtonData("Host");
        buttonHost.setOnAction(() -> view.switchToDisplay(hostMenuDisplay));
        layout.addButton(buttonHost);

        ButtonData buttonClient = new ButtonData("Connect");
        buttonClient.setOnAction(() -> view.switchToDisplay(clientMenuDisplay));
        layout.addButton(buttonClient);

        ButtonData backToMenu = new ButtonData(BACK);
        backToMenu.setOnAction(() -> view.switchToDisplay(mainMenuDisplay));
        layout.addButton(backToMenu);

        return layout;
        
    }
    
    private Layout createClientMenuDisplay(GraphicsDisplay mainMenuDisplay, GraphicsDisplay multiplayerMenuDisplay, GraphicsDisplay connectionFailedDisplay, GraphicsDisplay clientLobbyDisplay, VictoryDisplay victoryDisplay, ClientLobbyWaitThread thread) throws KeyListenerNotFoundException {
        logger.debug("Creating client menu display");
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData client = new ButtonData("Enter host's ip address");
        layout.addButton(client);

        TextFieldData ipAndPort = new TextFieldData("localhost", view.getStringKeyListener("LOCALHOST"));
        layout.addButton(ipAndPort);

        ButtonData buttonConnect = new ButtonData("Connect");
        buttonConnect.setOnAction(() -> {
            Thread x = new Thread(() -> {
                try {
                    view.switchToDisplay(clientLobbyDisplay);
                    control.connect(ipAndPort.getText());
                    victoryDisplay.updatePlayers();
                    view.switchToDisplay(victoryDisplay);
                } catch (NoHostFound | ConnectionClosed e) {
                    view.switchToDisplay(connectionFailedDisplay);
                }
            });
            x.start();
            thread.reset();
            Thread y = new Thread(thread);
            y.start();
        });
        layout.addButton(buttonConnect);

        ButtonData backToMulti = new ButtonData(BACK);
        backToMulti.setOnAction(() -> {
            thread.cancleWait();
            view.switchToDisplay(multiplayerMenuDisplay);
        });
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
    
    private Layout createClientLobbyMenuDisplay(GraphicsDisplay clientMenuDisplay, ClientLobbyWaitThread thread) {
        logger.debug("Creating client lobby display");
        
        LobbyLayout layout = new LobbyLayout(0.2f);
        
        ButtonData host = new ButtonData("Waiting for host to start...");
        layout.addButton(host);
        
        ButtonData backToMulti = new ButtonData(BACK);
        backToMulti.setOnAction(() -> {
            control.clientCancel();
            thread.cancleWait();
            view.switchToDisplay(clientMenuDisplay);
        });
        layout.addButton(backToMulti);

        return layout;
    }
    
    private Layout createVictoryDisplay(GraphicsDisplay mainMenuDisplay) {
        logger.debug("Creating victory display");
        
        VictoryLayout layout = new VictoryLayout(() -> control.getRanking(), () -> {
            view.switchToDisplay(mainMenuDisplay);
        });
        
        return layout;
        
    }
    
    private Layout createSinglePlayerGameSettings(GraphicsDisplay mainMenu, VictoryDisplay victoryDisplay) {
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData title = new ButtonData("Select Difficulty");
        layout.addButton(title);
        
        ButtonData difficultyButton = new ButtonData("Medium");
        
        difficultyButton.setOnAction(() -> {
            switch (difficultyButton.getText()) {
                case "Easy":
                    difficultyButton.setText("Medium");
                    break;
                case "Medium":
                    difficultyButton.setText("Hard");
                    break;
                case "Hard":
                    difficultyButton.setText("Easy");
                    break;
                default:
                    difficultyButton.setText("Medium");
            }
        });
        
        layout.addButton(difficultyButton);
        
        ButtonData start = new ButtonData("Start Game");
        
        start.setOnAction(() -> {
            Thread singlePlayerThread = new Thread(() -> {
                Difficulty diff;
                switch (difficultyButton.getText()) {
                    case "Easy":
                        diff = Difficulty.EASY;
                        break;
                    case "Medium":
                        diff = Difficulty.MEDIUM;
                        break;
                    case "Hard":
                        diff = Difficulty.HARD;
                        break;
                    default:
                        diff = Difficulty.MEDIUM;
                }
                GameSettings.changeDifficulty(diff);
                logger.trace("Starting singleplayer");
                control.startSinglePlayer();
                logger.trace("Queueing switch to victory scene");
                victoryDisplay.updatePlayers();
                view.switchToDisplay(victoryDisplay);
            });
            view.switchToGameScene();
            singlePlayerThread.start();
        });
        
        layout.addButton(start);
        
        ButtonData back = new ButtonData(BACK);
        
        back.setOnAction(() -> view.switchToDisplay(mainMenu));
        
        layout.addButton(back);
        
        return layout;
    }
  
    private Layout createMultiplayerGameSettings(GraphicsDisplay multiplayerMenuDisplay, GraphicsDisplay hostLobbyMenuDisplay, GraphicsDisplay connectionFailedDisplay, VictoryDisplay victoryDisplay) {
        
        FixedSpaceLayout layout = new FixedSpaceLayout(0.2f);
        
        ButtonData title = new ButtonData("Lobby Size");
        layout.addButton(title);
        
        ButtonData numberOfPlayers = new ButtonData("4");
        
        numberOfPlayers.setOnAction(() -> {
            switch (numberOfPlayers.getText()) {
                case "2":
                    numberOfPlayers.setText("3");
                    break;
                case "3":
                    numberOfPlayers.setText("4");
                    break;
                case "4":
                    numberOfPlayers.setText("2");
                    break;
                default:
                    numberOfPlayers.setText("4");
            }
        });
        
        layout.addButton(numberOfPlayers);
        
        ButtonData start = new ButtonData("Host");
        
        start.setOnAction(() -> {
            int numberOfClients = Integer.parseInt(numberOfPlayers.getText());
            Thread hostThread = new Thread(() -> {
                view.switchToDisplay(hostLobbyMenuDisplay);
                try {
                    control.host(numberOfClients);
                    victoryDisplay.updatePlayers();
                    view.switchToDisplay(victoryDisplay);
                } catch (Exception ex) {
                    view.switchToDisplay(connectionFailedDisplay);
                }
            });
            hostThread.start();
        });
        
        layout.addButton(start);
        
        ButtonData back = new ButtonData(BACK);
        
        back.setOnAction(() -> view.switchToDisplay(multiplayerMenuDisplay));
        
        layout.addButton(back);
        
        return layout;
    }
    
    private Layout createHostLobbyMenuDisplay(GraphicsDisplay multiplayerMenuDisplay) {
        logger.debug("Creating host lobby display");
        
        LobbyLayout layout = new LobbyLayout(0.2f);
        
        ButtonData host = new ButtonData("Hosting...");
        layout.addButton(host);

        TextListData playerList = new TextListData(4);
        
        for (int i = 0; i < 4; i++) { //TODO let the user change this from 4
            int j = i;
            playerList.addTextSource(() -> {
                ArrayList<String> ips = control.getPlayersIPaddresses();
                return (ips.size() > j) ? ips.get(j) : "";
            }, j);
        }
        layout.addList(playerList);
        
        ButtonData startGame = new ButtonData("Start game");
        startGame.setOnAction(() -> {
            if (control.hostStartTheGame()) {
                view.switchToGameScene();
            }
        });
        layout.addButton(startGame);
        
        ButtonData backToMulti = new ButtonData("Cancel");
        backToMulti.setOnAction(() -> {
            control.hostCancelTheGame();
            view.switchToDisplay(multiplayerMenuDisplay);
        });
        layout.addButton(backToMulti);

        return layout;
    }
    
    private Layout createCreditDisplay(GraphicsDisplay mainMenu) {
        CreditLayout layout = new CreditLayout();
        
        ButtonData returnButton = new ButtonData("Main Menu");
        returnButton.setOnAction(() -> view.switchToDisplay(mainMenu));
        
        layout.setReturn(returnButton);
        
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get("licence")));
        } catch (IOException ex) {
            logger.catching(ex);
            logger.warn("Couldn't load credits from file");
            text = "Couldn't load credits";
        }
        
        ButtonData credits = new ButtonData(text);
        
        layout.setCredits(credits);
        
        return layout;
    }
    
    /**
     * The main method to start the game menu.
     * @param args the arguments passed to run the game (not currently used)
     */
    public static void main(String[] args) {
        View view = new View();

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