package com.anotherworld.view.graphics.layout;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.Static2dCamera;
import com.anotherworld.view.graphics.VictoryDisplay;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.ButtonListener;

import java.util.ArrayList;
import java.util.List;

public class VictoryLayout extends Layout {

    private ButtonData returnButton;
    private Supplier<ArrayList<PlayerData>> playerSupplier;
    private Supplier<ArrayList<ButtonData>> buttonSupplier;
    
    /**
     * Creates a layout for the victory scene.
     * @param playerDataSupplier A supplier that returns a list of player names in order of victory
     * @param exitAction An action to perform when exit is pressed
     */
    public VictoryLayout(Supplier<List<String>> playerDataSupplier, ButtonListener exitAction) {
        playerSupplier = () -> {
            int i = 0;
            ArrayList<PlayerData> players = new ArrayList<>();
            List<String> playerData = playerDataSupplier.get();
            for (String name : playerData) {
                PlayerData playerObject = new PlayerData(name, 0, 160 * (i + 1) / (playerData.size() + 1), 50, ObjectState.IDLE, 0, 5);
                players.add(playerObject);
                i++;
            }
            return players;
        };
        buttonSupplier = () -> {
            ArrayList<ButtonData> buttons = new ArrayList<>();
            List<String> playerData = playerDataSupplier.get();
            for (int i = 0; i < playerData.size(); i++) {
                String position;
                switch (i) {
                    case 0:
                        position = "1st";
                        break;
                    case 1:
                        position = "2nd";
                        break;
                    case 2:
                        position = "3rd";
                        break;
                    case 3:
                        position = "4th";
                        break;
                    default:
                        position = "dnf";
                }
                ButtonData place = new ButtonData(() -> position, 0);
                place.setPosition(160 * (i + 1) / (playerData.size() + 1), 30);
                place.setHeight(20);
                place.setTextColour(1, 1, 1);
                buttons.add(place);
            }
            return buttons;
        };
        this.returnButton = new ButtonData("");
        this.returnButton.setOnAction(exitAction);
    }
    
    @Override
    public void enactLayout(GraphicsDisplay display) {
        display.changeCamera(new Static2dCamera(80, 45, 160, 90));
        ((VictoryDisplay)display).updatePlayers(playerSupplier);
        ((VictoryDisplay)display).updateButtons(buttonSupplier);
        returnButton.setHeight(10);
        returnButton.setWidth(50);
        returnButton.setText("Main Menu");
        returnButton.setPosition(140, 80);
        display.addButton(returnButton);
    }
    
}
