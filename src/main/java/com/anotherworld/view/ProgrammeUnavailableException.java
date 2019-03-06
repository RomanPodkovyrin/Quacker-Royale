package com.anotherworld.view;

/**
 * If an attempt to use a programme fails.
 * @author Jake Stewart
 *
 */
public class ProgrammeUnavailableException extends Exception {

    private static final long serialVersionUID = 2903359866516932442L;

    public ProgrammeUnavailableException(String s) {
        super(s);
    }
    
}
