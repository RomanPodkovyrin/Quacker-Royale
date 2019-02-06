package com.anotherworld.view;

import com.anotherworld.view.data.DisplayData;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
class UpdateDisplayObjects implements ViewEvent {
    
    private final DisplayData[] objects;

    public UpdateDisplayObjects(DisplayData[] objects) {
        this.objects = objects;
    }
    
    public DisplayData[] getObjects() {
        return objects;
    }

}
