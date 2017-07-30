/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.SortingException
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 6, 2012
 */
package com.irurueta.sorting;

/**
 * Exception thrown if sorting process for some reason.
 */
public class SortingException extends Exception {
    
    /**
     * Constructor.
     */
    public SortingException() {
        super();
    }

    /**
     * Constructor with String containing message.
     * @param message Message indicating the cause of the exception.
     */
    public SortingException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * @param message Message describing the cause of the exception.
     * @param cause Instance containing the cause of the exception.
     */
    public SortingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     * @param cause Instance containing the cause of the exception.
     */
    public SortingException(Throwable cause) {
        super(cause);
    }    
}
