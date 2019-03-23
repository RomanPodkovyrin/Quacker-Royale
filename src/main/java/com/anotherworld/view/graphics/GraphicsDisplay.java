package com.anotherworld.view.graphics;

import com.anotherworld.view.data.DisplayObject;
import com.anotherworld.view.data.TextDisplayObject;
import com.anotherworld.view.data.TextListData;
import com.anotherworld.view.input.Button;
import com.anotherworld.view.input.ButtonData;
import com.anotherworld.view.input.Clickable;
import com.anotherworld.view.input.MouseState;
import com.anotherworld.view.programme.Programme;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the display objects for part of a scene.
 * 
 * @author Jake Stewart
 *
 */
public class GraphicsDisplay {

    private static Logger logger = LogManager.getLogger();

    private final float x;
    private final float y;
    private final float height;
    private final float width;

    private Camera camera;

    protected ArrayList<DisplayObject> objects;

    private ArrayList<ButtonData> buttonsToAdd;

    public GraphicsDisplay() {
        this(-1f, -1f, 2f, 2f, new Static2dCamera(0, 0, 2, 2));
    }

    /**
     * Creates a new Graphics display (Uses normalised device coordinates).
     * 
     * @param x      The x position
     * @param y      The y position
     * @param height The display height
     * @param width  The display width
     * @throws IncoherentGraphicsDisplay If the display would go outside of the
     *                                   window
     */
    public GraphicsDisplay(float x, float y, float height, float width, Camera camera) {
        if (!(-1f <= x && x <= 1f && -1f <= y && y <= 1f && 0f <= height && height <= 2f && 0f <= width && width <= 2f
                && -1f <= x + width && x + width <= 1f && -1f <= y + height && y + height <= 1f)) {
            logger.catching(new IncoherentGraphicsDisplay("Display doesn't fit on screen"));
        }
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.camera = camera;
        objects = new ArrayList<>();
        buttonsToAdd = new ArrayList<>();
    }

    /**
     * Returns draws the objects it contains to the screen.
     * @param programme
     * @param mouseState
     */
    public void draw(Programme programme, MouseState mouseState) {
        synchronized (buttonsToAdd) {
            for (ButtonData button : buttonsToAdd) {
                objects.add(new Button(programme, button));
                objects.add(new TextDisplayObject(programme, button));
            }
            buttonsToAdd.clear();
        }
        programme.pushMatrix();
        this.transform(programme);
        for (int i = 0; i < objects.size(); i++) {
            programme.pushMatrix();
            objects.get(i).transform();
            objects.get(i).draw();
            programme.popMatrix();
            if (Clickable.class.isAssignableFrom(objects.get(i).getClass())) {
                Clickable temp = (Clickable) objects.get(i);
                if (mouseState.getX() >= temp.getX() - temp.getWidth() / 2
                        && mouseState.getY() >= temp.getY() - temp.getHeight() / 2
                        && mouseState.getX() < temp.getX() + temp.getWidth() / 2
                        && mouseState.getY() < temp.getY() + temp.getHeight() / 2) {
                    if (mouseState.isMouseDown()) {
                        temp.click();
                    } else {
                        temp.release();
                    }
                } else {
                    temp.release();
                }
            }
        }
        programme.popMatrix();
    }

    public void transform(Programme programme) {
        programme.transform(camera);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void addButton(ButtonData object) {
        synchronized (buttonsToAdd) {
            buttonsToAdd.add(object);
        }
    }

    /**
     * Deletes the programme buffers it has bound.
     */
    public void destroyObjects() {
        for (DisplayObject d : objects) {
            d.destroyObject();
        }
    }

}
