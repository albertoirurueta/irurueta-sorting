/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.COmparatorAndAverager
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 7, 2012
 */
package com.irurueta.sorting;

import java.util.Comparator;

/**
 * This insterface represents an object capable of comparing and averaging
 * instances of type T.
 * @param <T> Type to be compared and averaged
 */
public interface ComparatorAndAverager<T> extends Comparator<T>{
    
    /**
     * Averages provided instances and returns an instance representing the
     * average of provided ones. (i.e. if provided instances were doubles, then
     * result would be 0.5 * (t1 + t2))
     * @param t1 Instance to be averaged
     * @param t2 Instance to be averaged
     * @return Instance representing average of provided ones.
     */
    public T average(T t1, T t2);
}
