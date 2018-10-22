/*
 * Copyright (C) 2012 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.irurueta.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorts instances of type T in provided arrays using Quicksort method.
 * @param <T> Type of instances being sorted.
 * 
 * Where available, sort is based on Java SDK implementation, which also
 * uses Quicksort, however Java SDK implementation does not offer the
 * ability to retrieve indices when sorting
 * 
 * This class is based on algorithm found at
 * Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 424
 * Sedgewick, R. 1978. "Implementing Quicksort Programs", Communications 
 * of the ACM, vol. 21, pp. 847-857.
 */
public class SystemSorter<T> extends QuicksortSorter<T> {
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    @Override
    public void sort(T[] array, int fromIndex, int toIndex, 
        Comparator<T> comparator) throws SortingException, 
        IllegalArgumentException, ArrayIndexOutOfBoundsException {    
        
        Arrays.sort(array, fromIndex, toIndex, comparator);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    @Override
    public void sort(double[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {    
        
        Arrays.sort(array, fromIndex, toIndex);
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    @Override
    public void sort(float[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {    
        
        Arrays.sort(array, fromIndex, toIndex);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    @Override
    public void sort(int[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {    
        
        Arrays.sort(array, fromIndex, toIndex);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    @Override
    public void sort(long[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {    
        
        Arrays.sort(array, fromIndex, toIndex);
    }    
    
    /**
     * Returns sorting method of this class.
     * @return Sorting method.
     */    
    @Override
    public SortingMethod getMethod() {
        return SortingMethod.SYSTEM_SORTING_METHOD;
    }    
}
