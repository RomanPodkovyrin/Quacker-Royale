package com.anotherworld.view;

import com.anotherworld.view.data.Supplier;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.input.ButtonData;

import java.util.ArrayList;

public class FixedSpaceLayout extends Layout {

    private ArrayList<ButtonData> buttons;
    
    private float buttonHeight;
    
    public FixedSpaceLayout(float buttonHeight) {
        super();
        buttons = new ArrayList<>();
        this.buttonHeight = buttonHeight;
    }
    
    @Override
    public void enactLayout(GraphicsDisplay display) {
        float ySpacing = 2f / (buttons.size() + 1);
        Supplier<Float> maxWidth = () -> {
            float max = 0f;
            for (ButtonData button : buttons) {
                max = Math.max((float)button.getText().length() * buttonHeight * X_SCALE_ADJUSTMENT, max);
            }
            return max + buttonHeight * X_SCALE_ADJUSTMENT;
        };
        
        for (int i = 0; i < buttons.size(); i++) {
            ButtonData button = buttons.get(i);
            button.setText(button.getText().toUpperCase());
            button.setHeight(buttonHeight);
            button.setWidth(maxWidth);
            button.setPosition(this.getX() * X_SCALE_ADJUSTMENT, this.getY() + (-this.getHeight() / 2) + ySpacing * (i + 1));
            display.addButton(button);
        }
    }
    
    public void addButton(ButtonData button) {
        buttons.add(button);
    }
    
}
