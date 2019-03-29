package com.anotherworld.view.graphics.layout;

import com.anotherworld.tools.maths.Matrix;
import com.anotherworld.view.graphics.GraphicsDisplay;
import com.anotherworld.view.graphics.spritesheet.SpriteLocation;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.texture.TextureMap;

public class CreditLayout extends Layout {

    private ButtonData credits;
    private ButtonData returnButton;

    public CreditLayout() {
        super();
    }

    @Override
    public void enactLayout(GraphicsDisplay graphicsDisplay) {
        float buttonWidth;
        try {
            Matrix dimensions = TextureMap.getSpriteDimensions(SpriteLocation.TEXT);
            buttonWidth = (returnButton.getText().length() + 1) * 0.2f * (dimensions.getX() / dimensions.getY());
        } catch (Exception ex) {
            buttonWidth = 0.4f;
        }
        credits.setWidth(1.8f * 16f / 9f);
        credits.setHeight(1f);
        credits.setTextHeight(0.08f);
        graphicsDisplay.addText(credits);
        returnButton.setPosition(0.95f * 16f / 9f - buttonWidth / 2, 0.85f);
        returnButton.setWidth(buttonWidth);
        returnButton.setHeight(0.2f);
        returnButton.setOnHover(() -> {
            returnButton.setBackgroundColour(1, 1, 1, 0.8f);
            returnButton.setTextColour(0, 0, 0);
        });
        returnButton.setOnRelease(() -> {
            returnButton.setBackgroundColour(0, 0, 0, 0);
            returnButton.setTextColour(1, 1, 1);
        });
        returnButton.setBackgroundColour(0, 0, 0, 0);
        returnButton.setTextColour(1, 1, 1);
        graphicsDisplay.addButton(returnButton);
        super.enactLayout(graphicsDisplay);
    }

    public void setCredits(ButtonData buttonData) {
        credits = buttonData;
    }

    public void setReturn(ButtonData buttonData) {
        returnButton = buttonData;
    }

}
