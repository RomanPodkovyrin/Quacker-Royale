package com.anotherworld.view;

import com.anotherworld.view.graphics.displayobject.DisplayObject;

class AddDisplayObjects implements ViewEvent {
    
    private final DisplayObject[] objects;

    public AddDisplayObjects(DisplayObject[] objects) {
        this.objects = objects;
    }
    
    public DisplayObject[] getObjects() {
        return objects;
    }

}
