/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.ComparableAndAveragable
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 7, 2012
 */
package com.irurueta.sorting;

/**
 * Interface for objects that can be compared and averaged with other objects
 */
public interface ComparableAndAveragable<T> extends Comparable<T>{

    /**
     * Averages current instance with another instance. (i.e. if both objects
     * where Doubles, then the result would be equal to 0.5 * (obj1 + obj2)
     * @param other Other instance to be averaged with
     * @return An instance representing the average of both instances.
     */
    public T averageWith(T other);
}
