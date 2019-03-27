package com.anotherworld.view.input;

import com.anotherworld.view.data.RectangleDisplayObject;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;
import java.util.List;

public class Button extends RectangleDisplayObject implements Clickable {
    
    private final ButtonData buttonData;

    private List<ButtonListener> listeners;

    /**
     * Creates a new button.
     * @param programme The programme to use for rendering
     * @param buttonData The data to use for rendering
     */
    public Button(Programme programme, ButtonData buttonData) {
        super(new SpriteSheet(), programme, buttonData);
        this.listeners = new ArrayList<>();
        this.buttonData = buttonData;
        this.setColour(buttonData.getBackgroundR(), buttonData.getBackgroundG(), buttonData.getBackgroundB());
    }

    @Override
    public void click() {
        for (ButtonListener l : listeners) {
            l.clicked();
        }
        buttonData.preformAction();
    }

    @Override
    public void release() {
        //TODO implement this
    }

    public void addButtonListener(ButtonListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public boolean shouldDraw() {
        return !buttonData.hasTransparentBackground();
    }

    @Override
    public float getHeight() {
        return this.buttonData.getHeight();
    }

    @Override
    public float getWidth() {
        return this.buttonData.getWidth();
    }

}
