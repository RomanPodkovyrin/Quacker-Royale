package com.anotherworld.view;

import java.util.ArrayList;

import com.anotherworld.view.data.DisplayData;

/**
 * Creates a ViewEvent that updates the view's display objects.
 * @author Jake Stewart
 *
 */
class UpdateDisplayObjects implements ViewEvent {
    
    private final ArrayList<DisplayData> objects;

    public UpdateDisplayObjects(ArrayList<DisplayData> objects) {
        this.objects = objects;
    }
    
    public ArrayList<DisplayData> getObjects() {
        return objects;
    }

}
