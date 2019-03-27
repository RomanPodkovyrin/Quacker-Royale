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
            buttonWidth = returnButton.getText().length() * 0.2f * (dimensions.getX() / dimensions.getY());
        } catch (Exception ex) {
            buttonWidth = 0.2f;
        }
        credits.setWidth(1.8f);
        credits.setHeight(1f);
        graphicsDisplay.addText(credits);
        returnButton.setPosition(0.8f, 0.9f - buttonWidth);
        returnButton.setWidth(buttonWidth);
        returnButton.setHeight(0.2f);
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
