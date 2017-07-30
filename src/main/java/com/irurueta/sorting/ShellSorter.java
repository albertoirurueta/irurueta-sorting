/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.ShellSorter
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 7, 2012
 */
package com.irurueta.sorting;

import java.util.Comparator;

/**
 * Sorts instances of type T in provided arrays using Shell method.
 * @param <T> Type of instances being sorted.
 * 
 * This class is based on algorithm found at
 * Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 422
 * Knuth. D.E. 1997, Sorting and Searching, 3rd ed., vol. 3 of The Art of
 * Computer Programming (Reading, MA: Addison-Wesley)
 * Sedgewick, R. 1998. Algorithms in C, 3rd ed. (Reading, MA: Addison-
 * Wesley), Chapter 11.
 */
public class ShellSorter<T> extends Sorter<T> {
    
    /**
     * Constant defining increment factor to be used internally.
     */
    private static final int INCREMENT_FACTOR = 3;
    
    /**
     * Constant defining minimum increment before stopping the sorting
     * process.
     */
    private static final int MIN_INCREMENT = 1;

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
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }
        
        int j;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        T v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (comparator.compare(array[j - inc + fromIndex], v) > 0) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
            }
        } while (inc > MIN_INCREMENT);
    }

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
    @Override
    public int[] sortWithIndices(T[] array, int fromIndex, int toIndex, 
        Comparator<T> comparator) throws SortingException, 
        IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int [] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }
        
        int j, b;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        T v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                b = indices[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (comparator.compare(array[j - inc + fromIndex], v) > 0) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    indices[j + fromIndex] = indices[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
                indices[j + fromIndex] = b;
            }
        } while (inc > MIN_INCREMENT);
        
        return indices;        
    }

    /**
     * Returns sorting method of this class.
     * @return Sorting method.
     */    
    @Override
    public SortingMethod getMethod() {
        return SortingMethod.SHELL_SORTING_METHOD;
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
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }
        
        int j;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        double v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
            }
        } while (inc > MIN_INCREMENT);
    }

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
    @Override
    public int[] sortWithIndices(double[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int [] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }
        
        int j, b;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        double v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                b = indices[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    indices[j + fromIndex] = indices[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
                indices[j + fromIndex] = b;
            }
        } while (inc > MIN_INCREMENT);
        
        return indices;        
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
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }
        
        int j;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        float v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
            }
        } while (inc > MIN_INCREMENT);
    }

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
    @Override
    public int[] sortWithIndices(float[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int [] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }
        
        int j, b;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        float v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                b = indices[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    indices[j + fromIndex] = indices[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
                indices[j + fromIndex] = b;
            }
        } while (inc > MIN_INCREMENT);
        
        return indices;        
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
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }
        
        int j;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        int v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
            }
        } while (inc > MIN_INCREMENT);
    }

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
    @Override
    public int[] sortWithIndices(int[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int [] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }
        
        int j, b;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        int v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                b = indices[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    indices[j + fromIndex] = indices[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
                indices[j + fromIndex] = b;
            }
        } while (inc > MIN_INCREMENT);
        
        return indices;        
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
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }
        
        int j;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        long v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
            }
        } while (inc > MIN_INCREMENT);
    }

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
    @Override
    public int[] sortWithIndices(long[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        
        int [] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }
        
        int j, b;
        int inc = MIN_INCREMENT;
        int n = toIndex - fromIndex;
        
        long v;
        
        do {
            inc *= INCREMENT_FACTOR;
            inc++;
        } while (inc <= n);
        
        //Loop over the partial sorts
        do {
            inc /= INCREMENT_FACTOR;
            //Outer loop of straight insertion
            for (int i = inc; i < n; i++) {
                v = array[i + fromIndex];
                b = indices[i + fromIndex];
                j = i;
                
                //Inner loop of straight insertion
                while (array[j - inc + fromIndex] > v) {
                    array[j + fromIndex] = array[j - inc + fromIndex];
                    indices[j + fromIndex] = indices[j - inc + fromIndex];
                    j -= inc;
                    if (j < inc) {
                        break;
                    }
                }
                array[j + fromIndex] = v;
                indices[j + fromIndex] = b;
            }
        } while (inc > MIN_INCREMENT);
        
        return indices;        
    }    
}
