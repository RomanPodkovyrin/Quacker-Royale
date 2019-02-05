package com.anotherworld.view.graphics;

/**
 * The error generated when an illegal matrix operation is attempted.
 * @author Jake Stewart
 *
 */
public class MatrixSizeError extends Error {
    
    private static final long serialVersionUID = -2042753013304539649L;

    public MatrixSizeError(String string) {
        super(string);
    }
    
}
