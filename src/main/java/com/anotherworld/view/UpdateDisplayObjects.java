package com.anotherworld.view;

import com.anotherworld.view.data.DisplayObject;

import java.util.ArrayList;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
class UpdateDisplayObjects implements ViewEvent {
    
    private final ArrayList<DisplayObject> objects;

    /**
     * Creates an event to update the view's objects with the given objects.
     * @param objects The new display objects
     */
    public UpdateDisplayObjects(ArrayList<DisplayObject> objects) {
        this.objects = objects;
    }
    
    /**
     * Returns the new objects for the view.
     * @return the new objects
     */
    public ArrayList<DisplayObject> getObjects() {
        return objects;
    }

}
