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
    
    public VictoryLayout(Supplier<List<String>> playerDataSupplier, ButtonListener exitAction) {
        playerSupplier = () -> {
            int i = 0;
            ArrayList<PlayerData> players = new ArrayList<>();
            List<String> playerData = playerDataSupplier.get();
            for (String name : playerData) {
                PlayerData playerObject = new PlayerData(name, 0, 160 * (i + 1) / (playerData.size() + 1), 45, ObjectState.IDLE, 0, 5);
                players.add(playerObject);
                i++;
            }
            return players;
        };
        this.returnButton = new ButtonData("");
        this.returnButton.setOnAction(exitAction);
    }
    
    @Override
    public void enactLayout(GraphicsDisplay display) {
        display.changeCamera(new Static2dCamera(80, 45, 160, 90));
        ((VictoryDisplay)display).updatePlayers(playerSupplier);
        returnButton.setHeight(10);
        returnButton.setWidth(50);
        returnButton.setText("Main Menu");
        returnButton.setPosition(140, 80);
        display.addButton(returnButton);
    }
    
}
