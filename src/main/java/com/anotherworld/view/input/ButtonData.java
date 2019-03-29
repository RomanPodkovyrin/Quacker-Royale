package com.anotherworld.view.input;

import com.anotherworld.model.movable.ObjectState;
import com.anotherworld.view.data.TextDisplayData;
import com.anotherworld.view.data.primatives.Supplier;

import java.util.Optional;

public class ButtonData implements TextDisplayData {

    private static Supplier<Float> ZERO = () -> 0f;
    
    private Supplier<Float> x = ZERO;
    private Supplier<Float> y = ZERO;
    private Supplier<Float> width = ZERO;
    private Supplier<Float> height = ZERO;
    private Supplier<String> text;
    private float backgroundR;
    private float backgroundG;
    private float backgroundB;
    private float backgroundAlpha;
    private float textR;
    private float textG;
    private float textB;
    private float textHeight = 0.1f;
    private Optional<ButtonListener> clock;
    private Optional<ButtonListener> hover;
    private Optional<ButtonListener> release;
    
    /**
     * Creates the button data.
     * @param text The text for the button
     */
    public ButtonData(String text) {
        this(() -> {
            return text;
        }, 1f);
    }
    
    /**
     * Creates button data.
     * @param text The supplier of text for the button
     * @param backgroundAlpha If the button should have a transparent background
     */
    public ButtonData(Supplier<String> text, float backgroundAlpha) {
        this.textR = 0;
        this.textG = 0;
        this.textB = 0;
        this.backgroundR = 1;
        this.backgroundG = 1;
        this.backgroundB = 1;
        this.backgroundAlpha = backgroundAlpha;
        this.text = text;
        clock = Optional.empty();
        hover = Optional.empty();
        release = Optional.empty();
    }
    
    /**
     * Performs the action if set for the button.
     */
    public void preformAction() {
        if (clock.isPresent()) {
            clock.get().clicked();
        }
    }
    
    /**
     * Performs the hover if set for the button.
     */
    public void preformHover() {
        if (hover.isPresent()) {
            hover.get().clicked();
        }
    }
    
    /**
     * Performs the release if set for the button.
     */
    public void preformRelease() {
        if (release.isPresent()) {
            release.get().clicked();
        }
    }
    
    public void setTextHeight(float height) {
        this.textHeight = height;
    }
    
    /**
     * Sets the background colour for the button.
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     * @param alpha the alpha value
     */
    public void setBackgroundColour(float r, float g, float b, float alpha) {
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
        backgroundAlpha = alpha;
    }
    
    /**
     * Sets the text colour for the button.
     * @param r the red value
     * @param g the green value
     * @param b the blue value
     */
    public void setTextColour(float r, float g, float b) {
        textR = r;
        textG = g;
        textB = b;
    }
    
    public void setWidth(float width) {
        this.width = () -> width;
    }
    
    public void setWidth(Supplier<Float> width) {
        this.width = width;
    }
    
    public void setHeight(float height) {
        this.height = () -> height;
    }
    
    public void setPosition(float x, float y) {
        this.x = () -> x;
        this.y = () -> y;
    }
    
    /**
     * Sets the text for the string.
     * @param text the new text
     */
    public void setText(String text) {
        this.text = () -> {
            return text;
        };
    }
    
    @Override
    public float getXCoordinate() {
        return x.get();
    }

    @Override
    public float getYCoordinate() {
        return y.get();
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public ObjectState getState() {
        return null;
    }

    @Override
    public float getWidth() {
        return width.get();
    }

    @Override
    public float getHeight() {
        return height.get();
    }
    
    public String getText() {
        return text.get();
    }

    public void setOnAction(ButtonListener action) {
        this.clock = Optional.of(action);
    }

    public void setOnHover(ButtonListener action) {
        this.hover = Optional.of(action);
    }

    public void setOnRelease(ButtonListener action) {
        this.release = Optional.of(action);
    }
    
    public float getTextR() {
        return textR;
    }
    
    public float getTextG() {
        return textG;
    }
    
    public float getTextB() {
        return textB;
    }
    
    public float getBackgroundR() {
        return backgroundR;
    }
    
    public float getBackgroundG() {
        return backgroundG;
    }
    
    public float getBackgroundB() {
        return backgroundB;
    }

    @Override
    public float getTextHeight() {
        return textHeight;
    }

    public float getBackgroundAlpha() {
        return backgroundAlpha;
    }

}
