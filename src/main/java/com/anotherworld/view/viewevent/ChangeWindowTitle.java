package com.anotherworld.view.viewevent;

public class ChangeWindowTitle implements ViewEvent {

    private final String title;
    
    public ChangeWindowTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
}
