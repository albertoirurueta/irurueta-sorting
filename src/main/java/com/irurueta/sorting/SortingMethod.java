/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.SortingMethod
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 6, 2012
 */
package com.irurueta.sorting;

/**
 * Enumerator containing different algorithms for sorting arrays of data
 */
public enum SortingMethod {
    /**
     * Sorts data by using a straing insertion algorithm. This is a simple
     * yet slow algorithm for sorting, although for small arrays might be
     * fast enough
     */
    STRAIGHT_INSERTION_SORTING_METHOD,
    
    /**
     * Sorts data using Shell's sorting algorithm. This algorithm is an
     * improvement over the straight insertion algorithm to achieve faster
     * results.
     */
    SHELL_SORTING_METHOD,
    
    /**
     * Sorts data using Quicksort algorithm. This is the fastest algorithm
     * in average for sorting arrays of any size.
     */
    QUICKSORT_SORTING_METHOD,
    
    /**
     * Sorts data using Heapsort algorithm. This algorithm is based on the
     * idea of sorted trees, and performs better than straight insertions.
     */
    HEAPSORT_SORTING_METHOD,
    
    /**
     * Uses Java SDK sorting algorithm. Performance depends on SDK 
     * implementation and functionality is limited to sorting only (indices
     * cannot be retrieved).
     */
    SYSTEM_SORTING_METHOD
}
