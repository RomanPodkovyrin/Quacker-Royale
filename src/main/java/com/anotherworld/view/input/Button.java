package com.anotherworld.view.input;

import com.anotherworld.audio.AudioControl;
import com.anotherworld.view.data.RectangleDisplayObject;
import com.anotherworld.view.data.primatives.Points2d;
import com.anotherworld.view.graphics.spritesheet.SpriteSheet;
import com.anotherworld.view.programme.Programme;

public class Button extends RectangleDisplayObject implements Clickable {
    
    private final ButtonData buttonData;
    
    private float buttonWidth;
    
    private Programme programme;
    
    private boolean soundPlaying;

    /**
     * Creates a new button.
     * @param programme The programme to use for rendering
     * @param buttonData The data to use for rendering
     */
    public Button(Programme programme, ButtonData buttonData) {
        super(new SpriteSheet(), programme, buttonData);
        this.buttonData = buttonData;
        this.setColour(buttonData.getBackgroundR(), buttonData.getBackgroundG(), buttonData.getBackgroundB(), buttonData.getBackgroundAlpha());
        this.buttonWidth = buttonData.getWidth();
        this.programme = programme;
        soundPlaying = false;
    }
    
    @Override
    public void transform() {
        super.transform();
        if (buttonData.getWidth() != buttonWidth) {
            this.updatePoints(Points2d.genRectangle(buttonData.getWidth(), buttonData.getHeight()));
            programme.updateBuffers(this);
            this.buttonWidth = buttonData.getWidth();
        }
        this.setColour(buttonData.getBackgroundR(), buttonData.getBackgroundG(), buttonData.getBackgroundB(), buttonData.getBackgroundAlpha());
    }

    @Override
    public void click() {
        buttonData.preformAction();
        release();
    }

    @Override
    public void hover() {
        if (!soundPlaying) {
            AudioControl.playButtonHover();
            soundPlaying = true;
        }
        buttonData.preformHover();
    }

    @Override
    public void release() {
        buttonData.preformRelease();
        soundPlaying = false;
    }
    
    @Override
    public boolean shouldDraw() {
        return true;
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
