package com.anotherworld.view.graphics;

/**
 * The error generated when an illegal matrix operation is attempted.
 * @author Jake Stewart
 *
 */
public class MatrixSizeException extends Error {
    
    private static final long serialVersionUID = -2042753013304539649L;

    public MatrixSizeException(String string) {
        super(string);
    }
    
}
