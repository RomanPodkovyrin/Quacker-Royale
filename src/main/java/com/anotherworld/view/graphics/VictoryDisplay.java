package com.anotherworld.view.graphics;

import com.anotherworld.tools.datapool.PlayerData;
import com.anotherworld.view.data.PlayerDisplayData;
import com.anotherworld.view.data.PlayerDisplayObject;
import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;
import java.util.List;

public class VictoryDisplay extends GraphicsDisplay {
    
    private ArrayList<PlayerDisplayData> playersToAdd;
    
    private Supplier<ArrayList<PlayerData>> playerSupplier;
    
    private ArrayList<ButtonData> buttons;
    
    public VictoryDisplay() {
        super();
        playersToAdd = new ArrayList<>();
    }

    public void updatePlayers(Supplier<ArrayList<PlayerData>> playerSupplier) {
        this.playerSupplier = playerSupplier;
    }

    /**
     * Updates the display to use the most recent game session data.
     */
    public void updatePlayers() {
        objects.clear();
        for (ButtonData button: buttons) {
            super.addButton(button);
        }
        synchronized (playersToAdd) {
            List<PlayerData> players = playerSupplier.get();
            for (PlayerData player : players) {
                playersToAdd.add(player);
            }
        }
    }
    
    @Override
    public void draw(Programme programme, boolean mouseDown) {
        synchronized (playersToAdd) {
            for (PlayerDisplayData data : playersToAdd) {
                objects.add(new PlayerDisplayObject(programme, data));
            }
            playersToAdd.clear();
        }
        super.draw(programme, mouseDown);
    }
    
    @Override
    public void addButton(ButtonData object) {
        super.addButton(object);
        buttons.add(object);
    }

}
