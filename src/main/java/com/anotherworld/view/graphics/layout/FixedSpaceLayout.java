package com.anotherworld.view.graphics.layout;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.view.data.primatives.Supplier;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.spritesheet.SpriteLocation;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.texture.TextureMap;

import java.util.ArrayList;

public class FixedSpaceLayout extends Layout {

    private ArrayList<ButtonData> buttons;
    
    private float buttonHeight;
    
    /**
     * Creates a layout with a fixed space between each button.
     * @param buttonHeight The height of each button
     */
    public FixedSpaceLayout(float buttonHeight) {
        super();
        buttons = new ArrayList<>();
        this.buttonHeight = buttonHeight;
    }
    
    @Override
    public void enactLayout(GraphicsDisplay display) {
        float ySpacing = 2f / (buttons.size() + 1);
        Supplier<Float> maxWidth = () -> {
            float buttonWidth;
            try {
                Matrix dimensions = TextureMap.getSpriteDimensions(SpriteLocation.TEXT);
                buttonWidth = buttonHeight * (dimensions.getX() / dimensions.getY());
            } catch (Exception ex) {
                buttonWidth = buttonHeight;
            }
            float max = 0f;
            for (ButtonData button : buttons) {
                max = Math.max((float)button.getText().length() * buttonWidth / 2, max);
            }
            return max + buttonWidth / 2;
        };
        
        for (int i = 0; i < buttons.size(); i++) {
            ButtonData button = buttons.get(i);
            button.setHeight(buttonHeight);
            button.setWidth(maxWidth);
            button.setPosition(this.getX(), this.getY() + (-this.getHeight() / 2) + ySpacing * (i + 1));
            display.addButton(button);
        }
        super.enactLayout(display);
    }
    
    public void addButton(ButtonData button) {
        buttons.add(button);
    }
    
}
