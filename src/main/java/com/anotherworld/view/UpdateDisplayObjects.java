package com.anotherworld.view;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
public class UpdateDisplayObjects implements ViewEvent {
    
    private final DisplayObject[] objects;

    public UpdateDisplayObjects(DisplayObject[] objects) {
        this.objects = objects;
    }
    
    public DisplayObject[] getObjects() {
        return objects;
    }

}
