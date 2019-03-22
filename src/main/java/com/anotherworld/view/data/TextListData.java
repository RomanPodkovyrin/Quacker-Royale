package com.anotherworld.view.data;

import com.anotherworld.view.input.ButtonData;

import java.util.ArrayList;

public class TextListData {

    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;
    private float backgroundR = 1;
    private float backgroundG = 1;
    private float backgroundB = 1;
    private float textR = 1;
    private float textG = 1;
    private float textB = 1;
    private final ArrayList<Supplier<String>> textSources;

    public TextListData (int size) {
        textSources = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            textSources.add(i, () -> {
                return "";
            });
        }
    }
    
    public ArrayList<ButtonData> getButtons() {
        ArrayList<ButtonData> buttons = new ArrayList<>();
        int i = 0;
        for (Supplier<String> ts : textSources) {
            ButtonData button = new ButtonData(ts.get());
            button.setWidth(width);
            button.setHeight(height / textSources.size());
            button.setPosition(x, (height / (2 * textSources.size())) + (y - height) + (height * i) / textSources.size());
            button.setBackgroundColour(backgroundR, backgroundG, backgroundB);
            button.setTextColour(textR, textG, textB);
            buttons.add(button);
            i++;
        }
        
        return buttons;
    }
    
    public void addTextSource(Supplier<String> ts, int index) {
        textSources.remove(index);
        textSources.add(index, ts);
    }
    
    public void setBackgroundColour(float r, float g, float b) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }
    
    public void setTextColour(float r, float g, float b) {
        textR = r;
        textG = g;
        textB = b;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
}
