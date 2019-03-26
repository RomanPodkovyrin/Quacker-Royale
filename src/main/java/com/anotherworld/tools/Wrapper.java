package com.anotherworld.tools;

public class Wrapper<T> {

    private T value;
    
    public Wrapper(T value) {
        this.value = value;
    }
    
    public synchronized T getValue() {
        return value;
    }
    
    public synchronized void setValue(T value) {
        this.value = value;
    }
    
}
