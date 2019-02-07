package com.anotherworld.view;

import java.util.ArrayList;

import com.anotherworld.view.data.DisplayObject;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
public class UpdateDisplayObjects implements ViewEvent {
    
    private final ArrayList<DisplayObject> objects;

    public UpdateDisplayObjects(ArrayList<DisplayObject> objects) {
        this.objects = objects;
    }
    
    public ArrayList<DisplayObject> getObjects() {
        return objects;
    }

}
