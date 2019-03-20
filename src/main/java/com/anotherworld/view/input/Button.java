package com.anotherworld.view.input;

import com.anotherworld.view.Programme;
import com.anotherworld.view.data.RectangleDisplayObject;

import java.util.ArrayList;
import java.util.List;

public class Button extends RectangleDisplayObject implements Clickable {
    private final ButtonData buttonData;

    private List<ButtonListener> listeners;

    public Button(Programme programme, ButtonData buttonData) {
        super(programme, buttonData);
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
        
    }

    public void addButtonListener(ButtonListener listener) {
        listeners.add(listener);
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
