/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.Sorter
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 6, 2012
 */
package com.irurueta.sorting;

import java.util.Comparator;

/**
 * Sorts instances of type T in provided arrays using any of the
 * available methods.
 * @param <T> Type of instances being sorted.
 */
public abstract class Sorter<T> {
    
    /**
     * Default method to be used for sorting if none is provided.
     */
    public static final SortingMethod DEFAULT_SORTING_METHOD =
            SortingMethod.SYSTEM_SORTING_METHOD;
    
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
    public abstract void sort(T[] array, int fromIndex, int toIndex, 
            Comparator<T> comparator) throws SortingException, 
            IllegalArgumentException, ArrayIndexOutOfBoundsException;
        
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */    
    public abstract int[] sortWithIndices(T[] array, int fromIndex, int toIndex,
            Comparator<T> comparator) throws SortingException, 
            IllegalArgumentException, ArrayIndexOutOfBoundsException;
    
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
    public abstract void sort(double[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public abstract int[] sortWithIndices(double[] array, int fromIndex, 
            int toIndex) throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
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
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public abstract void sort(float[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;   
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public abstract int[] sortWithIndices(float[] array, int fromIndex, 
            int toIndex) throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
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
    public abstract void sort(int[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public abstract int[] sortWithIndices(int[] array, int fromIndex, 
            int toIndex) throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
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
    public abstract void sort(long[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public abstract int[] sortWithIndices(long[] array, int fromIndex, 
            int toIndex) throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException;    
    
    /**
     * Sorts provided array of Comparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
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
    public void sort(Comparable<T>[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {                
        sort((T[])array, fromIndex, toIndex, new Comparator<T>() {

            @Override
            public int compare(T t1, T t2) {
                Comparable t1b = (Comparable)t1;
                Comparable t2b = (Comparable)t2;
                
                return t1b.compareTo(t2b);
            }
            
        });
    }
         
    /**
     * Sorts provided array of Comparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @throws SortingException If for some reason sorting fails.
     */                
    public void sort(Comparable<T>[] array) throws SortingException {
        sort(array, 0, array.length);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public void sort(T[] array, Comparator<T> comparator) 
            throws SortingException {
        sort(array, 0, array.length, comparator);
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public void sort(double[] array) 
            throws SortingException {
        sort(array, 0, array.length);
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public void sort(float[] array) throws SortingException {
        sort(array, 0, array.length);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public void sort(int[] array) throws SortingException {
        sort(array, 0, array.length);
    }
    
    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public void sort(long[] array) throws SortingException {
        sort(array, 0, array.length);
    }
    
    
    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails.
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}.
     */        
    public int[] sortWithIndices(Comparable<T>[] array, int fromIndex, 
            int toIndex) throws SortingException, IllegalArgumentException,
            ArrayIndexOutOfBoundsException {
        return sortWithIndices((T[])array, fromIndex, toIndex, new Comparator<T>() {
                @Override
                public int compare(T t1, T t2) {
                    Comparable t1b = (Comparable)t1;
                    Comparable t2b = (Comparable)t2;
                
                    return t1b.compareTo(t2b);
                }
        });
    }
    
    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */            
    public int[] sortWithIndices(Comparable<T>[] array) throws SortingException {
        return sortWithIndices(array, 0, array.length);
    }

    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */                
    public int[] sortWithIndices(T[] array, Comparator<T> comparator) 
            throws SortingException {
        return sortWithIndices(array, 0, array.length, comparator);
    }

    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method
     * all elements in array are modified so that they are on ascending 
     * order.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */                
    public int[] sortWithIndices(double[] array) throws SortingException {
        return sortWithIndices(array, 0, array.length);
    }

    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */                
    public int[] sortWithIndices(float[] array) throws SortingException {
        return sortWithIndices(array, 0, array.length);
    }
    
    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */                
    public int[] sortWithIndices(int[] array) throws SortingException {
        return sortWithIndices(array, 0, array.length);
    }
    
    /**
     * Sorts provided array of COmparables in ascending order so that 
     * {@code array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order.
     * @param array Array to be sorted. After execution of this method 
     * all elements in array are modified so that they are on ascending 
     * order.
     * @return Array containing original location of elements that have been
     * sorted.
     * @throws SortingException If for some reason sorting fails.
     */                
    public int[] sortWithIndices(long[] array) throws SortingException {
        return sortWithIndices(array, 0, array.length);
    }
    
    /**
     * Returns the k-th sorted element in provided array of Comparables. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k]
     * @return The k-th sorted element in provided array
     * @throws IllegalArgumentException if k &lt; array.length
     */
    public T select(int k, Comparable<T>[] array) 
            throws IllegalArgumentException {
        return select(k, array, 0, array.length);
    }

    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */    
    public T select(int k, Comparable<T>[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return select(k, (T[])array, fromIndex, toIndex, new Comparator<T>() {
                @Override
                public int compare(T t1, T t2) {
                    Comparable t1b = (Comparable)t1;
                    Comparable t2b = (Comparable)t2;
               
                    return t1b.compareTo(t2b);
                }
        });
    }

    /**
     * Returns the k-th sorted element in provided array. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k].
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException k &lt; array.length.
     */    
    public T select(int k, T[] array, Comparator<T> comparator)
            throws IllegalArgumentException {
        return select(k, array, 0, array.length, comparator);
    }

    /**
     * Returns the k-th sorted element in provided array. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k].
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException k &lt; array.length.
     */    
    public double select(int k, double[] array)
            throws IllegalArgumentException {
        return select(k, array, 0, array.length);
    }

    /**
     * Returns the k-th sorted element in provided array. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k].
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException k &lt; array.length.
     */    
    public float select(int k, float[] array) throws IllegalArgumentException {
        return select(k, array, 0, array.length);
    }
    
    /**
     * Returns the k-th sorted element in provided array. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k].
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException k &lt; array.length.
     */    
    public int select(int k, int[] array) throws IllegalArgumentException {
        return select(k, array, 0, array.length);
    }
    
    /**
     * Returns the k-th sorted element in provided array. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[0] ... array[k-1] contains unsorted
     * elements smaller than sorted element array[k],  and on locations
     * array[k+1] ... array[length-1] contains unsorted elements greater
     * than sorted element array[k].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[0] ... array[k-1] contains unsorted elements smaller than 
     * sorted element array[k] and array[k+1] ... array[length-1] contains
     * elements greater than sorted element array[k].
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException k &lt; array.length.
     */    
    public long select(int k, long[] array) throws IllegalArgumentException {
        return select(k, array, 0, array.length);
    }
        
    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex]
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param comparator Determines whether an element is greater or lower 
     * than another one.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */        
    public T select(int k, T[] array, int fromIndex, int toIndex,
            Comparator<T> comparator) throws IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int i, ir, j, l, mid, n = toIndex - fromIndex;
        if (k >= n) {
            throw new IllegalArgumentException();
        }
        
        T a;
        l = 0;
        ir = n - 1;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && comparator.compare(array[ir + fromIndex],
                        array[l + fromIndex]) < 0) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                return array[k + fromIndex];
            } else {
                mid = (l + ir) >> 1;
                swap(array, mid + fromIndex, l + 1 + fromIndex);
                if (comparator.compare(array[l + fromIndex],
                        array[ir + fromIndex]) > 0) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + 1 + fromIndex],
                        array[ir + fromIndex]) > 0) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + fromIndex],
                        array[l + 1 + fromIndex]) > 0) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                i = l + 1;
                j = ir;
                a = array[l + 1 + fromIndex];
                for (;;) {
                    do {
                        i++;
                    } while (comparator.compare(
                            array[i + fromIndex], a) < 0);
                    do {
                        j--;
                    } while (comparator.compare(
                            array[j + fromIndex], a) > 0);
                    if (j < i) {
                        break;
                    }
                    swap(array, i + fromIndex, j + fromIndex);
                }
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */        
    public double select(int k, double[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int i, ir, j, l, mid, n = toIndex - fromIndex;
        if (k >= n) {
            throw new IllegalArgumentException();
        }
        
        double a;
        l = 0;
        ir = n - 1;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && array[ir + fromIndex] < array[l + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                return array[k + fromIndex];
            } else {
                mid = (l + ir) >> 1;
                swap(array, mid + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                i = l + 1;
                j = ir;
                a = array[l + 1 + fromIndex];
                for (;;) {
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    if (j < i) {
                        break;
                    }
                    swap(array, i + fromIndex, j + fromIndex);
                }
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }

    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */        
    public float select(int k, float[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int i, ir, j, l, mid, n = toIndex - fromIndex;
        if (k >= n) {
            throw new IllegalArgumentException();
        }
        
        float a;
        l = 0;
        ir = n - 1;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && array[ir + fromIndex] < array[l + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                return array[k + fromIndex];
            } else {
                mid = (l + ir) >> 1;
                swap(array, mid + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                i = l + 1;
                j = ir;
                a = array[l + 1 + fromIndex];
                for (;;) {
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    if (j < i) {
                        break;
                    }
                    swap(array, i + fromIndex, j + fromIndex);
                }
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }
    
    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */        
    public int select(int k, int[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int i, ir, j, l, mid, n = toIndex - fromIndex;
        if (k >= n) {
            throw new IllegalArgumentException();
        }
        
        int a;
        l = 0;
        ir = n - 1;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && array[ir + fromIndex] < array[l + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                return array[k + fromIndex];
            } else {
                mid = (l + ir) >> 1;
                swap(array, mid + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                i = l + 1;
                j = ir;
                a = array[l + 1 + fromIndex];
                for (;;) {
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    if (j < i) {
                        break;
                    }
                    swap(array, i + fromIndex, j + fromIndex);
                }
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }
    
    /**
     * Returns the k-th sorted element in provided array of COmparables
     * starting at fromIndex and finishing at toIndex, elements outside this
     * range are ignored. 
     * Selecting an element is usually faster than sorting the whole 
     * array, and for that reason, when only a few sorted elements are 
     * required, this method should be used instead of sort.
     * Because array is passed by reference, after executing this method
     * array is modified so that in k location it contains the k-th sorted
     * elements and on locations array[fromIndex] ... array[k-1 + fromIndex] 
     * contains unsorted elements smaller than sorted element 
     * array[k + fromIndex],  and on locations
     * array[k+1 + fromIndex] ... array[toIndex-1] contains unsorted 
     * elements greater than sorted element array[k + fromIndex].
     * @param k Position of sorted element to be retrieved.
     * @param array Array to be used for retrieving k-th sorted element.
     * Provided array is passed by reference and modified upon execution of
     * this method so that k-th location contains k-th sorted element, and
     * array[fromIndex] ... array[k-1 + fromIndex] contains unsorted 
     * elements smaller than sorted element array[k + fromIndex] and 
     * array[k+1 + fromIndex] ... array[toIndex-1] contains elements 
     * greater than sorted element array[k].
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return The k-th sorted element in provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or 
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are 
     * outside array boundaries.
     */        
    public long select(int k, long[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int i, ir, j, l, mid, n = toIndex - fromIndex;
        if (k >= n) {
            throw new IllegalArgumentException();
        }
        
        long a;
        l = 0;
        ir = n - 1;
        for (;;) {
            if (ir <= l + 1) {
                if (ir == l + 1 && array[ir + fromIndex] < array[l + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                return array[k + fromIndex];
            } else {
                mid = (l + ir) >> 1;
                swap(array, mid + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                i = l + 1;
                j = ir;
                a = array[l + 1 + fromIndex];
                for (;;) {
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    if (j < i) {
                        break;
                    }
                    swap(array, i + fromIndex, j + fromIndex);
                }
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                if (j >= k) {
                    ir = j - 1;
                }
                if (j <= k) {
                    l = i;
                }
            }
        }
    }
    
    /**
     * Computes median of provided array
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @return Median of provided array.
     */
    public T median(Comparable<T>[] array) {
        return median(array, 0, array.length);
    }

    /**
     * Computes median of provided array of Comparables
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Median of provided array.
     * @throws IllegalArgumentException if k &lt; (toIndex - fromIndex) or
     * fromIndex &lt; toIndex.
     * @throws ArrayIndexOutOfBoundsException if fromIndex or toIndex are
     * outside array boundaries.
     */    
    public T median(Comparable<T>[] array, int fromIndex, int toIndex)
        throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        return median((T[])array, fromIndex, toIndex, new ComparatorAndAverager<T>() {
                @Override
                public int compare(T t1, T t2) {
                    Comparable t1b = (Comparable)t1;
                    Comparable t2b = (Comparable)t2;
                
                    return t1b.compareTo(t2b);
                }
            
                @Override
                public T average(T t1, T t2) {
                    if (t1 instanceof ComparableAndAveragable &&
                            t2 instanceof ComparableAndAveragable) {
                        return ((ComparableAndAveragable<T>)t1).averageWith(t2);
                    }
                    if (t1 instanceof Byte && t1 instanceof Byte) {
                        byte b1 = ((Byte)t1);
                        byte b2 = ((Byte)t2);
                        return (T)Byte.valueOf((byte)((b1 + b2) / 2));
                    }
                    if (t1 instanceof Character && t1 instanceof Character) {
                        char c1 = ((Character)t1);
                        char c2 = ((Character)t2);
                        return (T)Character.valueOf((char)((c1 + c2) / 2));
                    }
                    if (t1 instanceof Short && t1 instanceof Short) {
                        short c1 = ((Short)t1);
                        short c2 = ((Short)t2);
                        return (T)Short.valueOf((short)((c1 + c2) / 2));
                    }
                    if (t1 instanceof Integer && t1 instanceof Integer) {
                        int i1 = ((Integer)t1);
                        int i2 = ((Integer)t2);
                        return (T)Integer.valueOf((i1 + i2) / 2);
                    }
                    if (t1 instanceof Long && t1 instanceof Long) {
                        long l1 = ((Long)t1);
                        long l2 = ((Long)t2);
                        return (T)Long.valueOf((l1 + l2) / 2);
                    }
                    if (t1 instanceof Float && t1 instanceof Float) {
                        float f1 = ((Float)t1);
                        float f2 = ((Float)t2);
                        return (T)Float.valueOf((f1 + f2) / 2.0f);
                    }
                    if (t1 instanceof Double && t1 instanceof Double) {
                        double d1 = ((Double)t1);
                        double d2 = ((Double)t2);
                        return (T)Double.valueOf((d1 + d2) / 2.0);
                    }
                
                    //for other case, average returns 1st parameter
                    return t1;
                }
        });
    }
    
    /**
     * Computes median of provided array
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param comparator Determines whether an element is greater or lower 
     * than another one and also is capable of computing the average 
     * between two T instances.
     * @return Median of provided array.
     */    
    public T median(T[] array, ComparatorAndAverager<T> comparator) {
        return median(array, 0, array.length, comparator);
    }    

    /**
     * Computes median of provided array
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @return Median of provided array.
     */    
    public double median(double[] array) {
        return median(array, 0, array.length);
    }    

    /**
     * Computes median of provided array.
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @return Median of provided array.
     */    
    public float median(float[] array) {
        return median(array, 0, array.length);
    }    
    
    /**
     * Computes median of provided array.
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @return Median of provided array.
     */    
    public int median(int[] array) {
        return median(array, 0, array.length);
    }    
    
    /**
     * Computes median of provided array.
     * Median is computed by selecting the length / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location length / 2, smaller unsorted elements at
     * arra[0] ... array[length / 2 - 1], and greater unsorted elements at
     * array[length / 2 + 1] ... array[length - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @return Median of provided array.
     */    
    public long median(long[] array) {
        return median(array, 0, array.length);
    }    
    
    
    /**
     * Computes median of provided array.
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @param comparator Determines whether an element is greater or lower 
     * than another one and also is capable of computing the average 
     * between two T instances.
     * @return Median of provided array.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws ArrayIndexOutOfBoundsException if either fromIndex or toIndex are out of bounds.
     */        
    public T median(T[] array, int fromIndex, int toIndex, 
            ComparatorAndAverager<T> comparator) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int length = toIndex - fromIndex;
        
        T value1, value2, value3;
        int pos1;
        
        pos1 = length / 2;
        //select pos1 ordered element of v and modifies v so that
        // v(0) ... v(pos1 - 1) < value1 < v(pos1 + 1) ... v(length - 1)
        //where v(0) ... v(pos1 - 1) are unordered elements lower than value1
        //and v(pos1) ... v(length - 1) are unordered elements greater than
        //value1
        value1 = select(pos1, array, fromIndex, toIndex, comparator);
        if ((length % 2) == 0) {
            //for even length
            
            //value2 is the previous ordered element of v, which is the maximum
            //element within v(0) ... v(pos1 - 1)
            value2 = array[fromIndex];
            for (int i = 1; i < pos1; i++) {
                value3 = array[i + fromIndex];
                if (comparator.compare(value3, value2) > 0) {
                    value2 = value3;
                }
            }
            
            return comparator.average(value1, value2);
        } else {
            //for odd length
            return value1;
        }
    }    

    /**
     * Computes median of provided array.
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Median of provided array.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws ArrayIndexOutOfBoundsException if either fromIndex or toIndex are out of bounds.
     */        
    public double median(double[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int length = toIndex - fromIndex;
        
        double value1, value2, value3;
        int pos1;
        
        pos1 = length / 2;
        //select pos1 ordered element of v and modifies v so that
        // v(0) ... v(pos1 - 1) < value1 < v(pos1 + 1) ... v(length - 1)
        //where v(0) ... v(pos1 - 1) are unordered elements lower than value1
        //and v(pos1) ... v(length - 1) are unordered elements greater than
        //value1
        value1 = select(pos1, array, fromIndex, toIndex);
        if ((length % 2) == 0) {
            //for even length
            
            //value2 is the previous ordered element of v, which is the maximum
            //element within v(0) ... v(pos1 - 1)
            value2 = array[fromIndex];
            for (int i = 1; i < pos1; i++) {
                value3 = array[i + fromIndex];
                if (value3 > value2) {
                    value2 = value3;
                }
            }
            
            return 0.5 * (value1 + value2);
        } else {
            //for odd length
            return value1;
        }
    }    

    /**
     * Computes median of provided array.
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Median of provided array.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws ArrayIndexOutOfBoundsException if either fromIndex or toIndex are out of bounds.
     */        
    public float median(float[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int length = toIndex - fromIndex;
        
        float value1, value2, value3;
        int pos1;
        
        pos1 = length / 2;
        //select pos1 ordered element of v and modifies v so that
        // v(0) ... v(pos1 - 1) < value1 < v(pos1 + 1) ... v(length - 1)
        //where v(0) ... v(pos1 - 1) are unordered elements lower than value1
        //and v(pos1) ... v(length - 1) are unordered elements greater than
        //value1
        value1 = select(pos1, array, fromIndex, toIndex);
        if ((length % 2) == 0) {
            //for even length
            
            //value2 is the previous ordered element of v, which is the maximum
            //element within v(0) ... v(pos1 - 1)
            value2 = array[fromIndex];
            for (int i = 1; i < pos1; i++) {
                value3 = array[i + fromIndex];
                if (value3 > value2) {
                    value2 = value3;
                }
            }
            
            return 0.5f * (value1 + value2);
        } else {
            //for odd length
            return value1;
        }
    }    
    
    /**
     * Computes median of provided array.
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Median of provided array.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws ArrayIndexOutOfBoundsException if either fromIndex or toIndex are out of bounds.
     */        
    public int median(int[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int length = toIndex - fromIndex;
        
        int value1, value2, value3;
        int pos1;
        
        pos1 = length / 2;
        //select pos1 ordered element of v and modifies v so that
        // v(0) ... v(pos1 - 1) < value1 < v(pos1 + 1) ... v(length - 1)
        //where v(0) ... v(pos1 - 1) are unordered elements lower than value1
        //and v(pos1) ... v(length - 1) are unordered elements greater than
        //value1
        value1 = select(pos1, array, fromIndex, toIndex);
        if ((length % 2) == 0) {
            //for even length
            
            //value2 is the previous ordered element of v, which is the maximum
            //element within v(0) ... v(pos1 - 1)
            value2 = array[fromIndex];
            for (int i = 1; i < pos1; i++) {
                value3 = array[i + fromIndex];
                if (value3 > value2) {
                    value2 = value3;
                }
            }
            
            return (int)(0.5 * ((double)value1 + (double)value2));
        } else {
            //for odd length
            return value1;
        }
    }    
    
    /**
     * Computes median of provided array.
     * Median is computed by selecting the 
     * ((toIndex - fromIndex) + fromIndex) / 2 element, hence 
     * provided array is modified upon execution of this method containing
     * sorted element at location ((toIndex - fromIndex) + fromIndex)  / 2, 
     * smaller unsorted elements at arra[fromIndex] ... 
     * array[((toIndex - fromIndex) + fromIndex) / 2 - 1], and greater 
     * unsorted elements at 
     * array[((toIndex - fromIndex) + fromIndex) / 2 + 1] ... 
     * array[toIndex - 1].
     * @param array Array to be used for computation of median. This array
     * is modified after execution of this method.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex Index were sorting stops (exclusive).
     * @return Median of provided array.
     * @throws IllegalArgumentException if fromIndex is greater than toIndex.
     * @throws ArrayIndexOutOfBoundsException if either fromIndex or toIndex are out of bounds.
     */        
    public long median(long[] array, int fromIndex, int toIndex) 
            throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int length = toIndex - fromIndex;
        
        long value1, value2, value3;
        int pos1;
        
        pos1 = length / 2;
        //select pos1 ordered element of v and modifies v so that
        // v(0) ... v(pos1 - 1) < value1 < v(pos1 + 1) ... v(length - 1)
        //where v(0) ... v(pos1 - 1) are unordered elements lower than value1
        //and v(pos1) ... v(length - 1) are unordered elements greater than
        //value1
        value1 = select(pos1, array, fromIndex, toIndex);
        if ((length % 2) == 0) {
            //for even length
            
            //value2 is the previous ordered element of v, which is the maximum
            //element within v(0) ... v(pos1 - 1)
            value2 = array[fromIndex];
            for (int i = 1; i < pos1; i++) {
                value3 = array[i + fromIndex];
                if (value3 > value2) {
                    value2 = value3;
                }
            }
            
            return (long)(0.5 * ((double)value1 + (double)value2));
        } else {
            //for odd length
            return value1;
        }
    }    
    
    /**
     * Returns sorting method of an implementation of this class.
     * @return Sorting method.
     */        
    public abstract SortingMethod getMethod();
    
    /**
     * Creates a Sorter instance using DEFAULT_SORTING_METHOD.
     * @return A sorter instance.
     */
    public static Sorter create() {
        return create(DEFAULT_SORTING_METHOD);
    }
            
    /**
     * Creates a Sorter instance using provided sorting method.
     * @param method Method to be used for sorting.
     * @return A sorter instance.
     */
    public static Sorter create(SortingMethod method) {
        switch (method) {
            case STRAIGHT_INSERTION_SORTING_METHOD:
                return new StraightInsertionSorter();
            case SHELL_SORTING_METHOD:
                return new ShellSorter();
            case HEAPSORT_SORTING_METHOD:
                return new HeapsortSorter();
            case QUICKSORT_SORTING_METHOD:
                return new QuicksortSorter();
            case SYSTEM_SORTING_METHOD:
            default:
                return new SystemSorter();
        }
    }   
    
    /**
     * Returns a new array containing original indices ordered from 0
     * to length-1.
     * @param length length of returned array.
     * @return Array with indices in natural order.
     */
    protected int[] getInitialIndicesVector(int length) {
        int [] out = new int[length];
        
        for (int i = 0; i < length; i++) {
            out[i] = i;
        }
        
        return out;
    }
    
    /**
     * Swaps values in array at locations posA and posB.
     * @param arr array where values are swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */        
    protected void swap(T[] arr, int posA, int posB) {
        T value = arr[posA];
        arr[posA] = arr[posB];
        arr[posB] = value;
    }    
    
    /**
     * Swaps values in array at locations posA and posB.
     * @param arr array where values are swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */        
    protected void swap(double[] arr, int posA, int posB) {
        double value = arr[posA];
        arr[posA] = arr[posB];
        arr[posB] = value;
    }    

    /**
     * Swaps values in array at locations posA and posB.
     * @param arr array where values are swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */        
    protected void swap(float[] arr, int posA, int posB) {
        float value = arr[posA];
        arr[posA] = arr[posB];
        arr[posB] = value;
    }    
    
    /**
     * Swaps values in array at locations posA and posB.
     * @param arr array where values are swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */        
    protected void swap(int[] arr, int posA, int posB) {
        int value = arr[posA];
        arr[posA] = arr[posB];
        arr[posB] = value;
    }    
    
    /**
     * Swaps values in array at locations posA and posB.
     * @param arr array where values are swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */        
    protected void swap(long[] arr, int posA, int posB) {
        long value = arr[posA];
        arr[posA] = arr[posB];
        arr[posB] = value;
    }        
}
