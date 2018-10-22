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

import java.util.Comparator;

/**
 * Sorts instances of type T in provided arrays using Heapsort method.
 * @param <T> Type of instances being sorted.
 * 
 * This class is based on algorithm found at
 * Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 428
 * Knuth. D.E. 1997, Sorting and Searching, 3rd ed., vol. 3 of The Art of
 * Computer Programming (Reading, MA: Addison-Wesley)
 * Sedgewick, R. 1998. Algorithms in C, 3rd ed. (Reading, MA: Addison-
 * Wesley), Chapter 11.
 */
@SuppressWarnings("Duplicates")
public class HeapsortSorter<T> extends Sorter<T> {

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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n - 1, comparator, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            siftDown(array, 0, i - 1, comparator, fromIndex);
        }
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDownWithIndices(array, indices, i, n - 1, comparator,
                    fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            swapIndices(indices, fromIndex, i + fromIndex);
            siftDownWithIndices(array, indices, 0, i - 1, comparator, 
                    fromIndex);
        }
        
        return indices;
    }

    /**
     * Returns sorting method of this class.
     * @return Sorting method.
     */
    @Override
    public SortingMethod getMethod() {
        return SortingMethod.HEAPSORT_SORTING_METHOD;
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            siftDown(array, 0, i - 1, fromIndex);
        }
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDownWithIndices(array, indices, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            swapIndices(indices, fromIndex, i + fromIndex);
            siftDownWithIndices(array, indices, 0, i - 1, fromIndex);
        }
        
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            siftDown(array, 0, i - 1, fromIndex);
        }
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDownWithIndices(array, indices, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            swapIndices(indices, fromIndex, i + fromIndex);
            siftDownWithIndices(array, indices, 0, i - 1, fromIndex);
        }
        
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            siftDown(array, 0, i - 1, fromIndex);
        }
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDownWithIndices(array, indices, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            swapIndices(indices, fromIndex, i + fromIndex);
            siftDownWithIndices(array, indices, 0, i - 1, fromIndex);
        }
        
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            siftDown(array, 0, i - 1, fromIndex);
        }
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
        
        int i;
        int n = toIndex - fromIndex;
        
        for (i = n / 2 - 1; i >= 0; i--) {
            siftDownWithIndices(array, indices, i, n - 1, fromIndex);
        }
        for (i = n - 1; i > 0; i--) {
            swap(array, fromIndex, i + fromIndex);
            swapIndices(indices, fromIndex, i + fromIndex);
            siftDownWithIndices(array, indices, 0, i - 1, fromIndex);
        }
        
        return indices;
    }    
    
    /**
     * Internal method to reorder sub-array ra.
     * @param ra sub-array ra.
     * @param l l value.
     * @param r r value.
     * @param comparator a comparator.
     * @param fromIndex initial position.
     */
    private void siftDown(T[] ra, int l, int r, Comparator<T> comparator,
            int fromIndex) {
        int j, jold;
        T a = ra[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && comparator.compare(ra[j + fromIndex],
                    ra[j + 1 + fromIndex]) < 0) {
                j++;
            }
            if (comparator.compare(a, ra[j + fromIndex]) >= 0) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
    }
    
    /**
     * Internal method to reorder sub-array ra along with its corresponding
     * indices.
     * @param ra sub-array ra.
     * @param rb sub-array rb.
     * @param l l value.
     * @param r r value.
     * @param comparator a comparator.
     * @param fromIndex initial position.
     */
    private void siftDownWithIndices(T[] ra, int[] rb, int l, int r, 
            Comparator<T> comparator, int fromIndex) {
        int j, jold;
        T a = ra[l + fromIndex];
        int b = rb[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && comparator.compare(ra[j + fromIndex],
                    ra[j + 1 + fromIndex]) < 0) {
                j++;
            }
            if (comparator.compare(a, ra[j + fromIndex]) >= 0) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            rb[jold + fromIndex] = rb[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
        rb[jold + fromIndex] = b;
    }
    
    /**
     * Swaps values in array of indices at locations posA and posB.
     * @param indices array containing indices to be swapped.
     * @param posA Location to be swapped.
     * @param posB Location to be swapped.
     */
    private void swapIndices(int[] indices, int posA, int posB) {
        int value = indices[posA];
        indices[posA] = indices[posB];
        indices[posB] = value;
    }    
    
    /**
     * Internal method to reorder sub-array ra.
     * @param ra sub-array ra.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDown(double[] ra, int l, int r, int fromIndex) {
        int j, jold;
        double a = ra[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
    }
    
    /**
     * Internal method to reorder sub-array ra along with its corresponding
     * indices.
     * @param ra sub-array ra.
     * @param rb sub-array rb.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDownWithIndices(double[] ra, int[] rb, int l, int r, 
            int fromIndex) {
        int j, jold;
        double a = ra[l + fromIndex];
        int b = rb[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            rb[jold + fromIndex] = rb[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
        rb[jold + fromIndex] = b;
    }        
    
    /**
     * Internal method to reorder sub-array ra.
     * @param ra sub-array ra.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDown(float[] ra, int l, int r, int fromIndex) {
        int j, jold;
        float a = ra[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
    }
    
    /**
     * Internal method to reorder sub-array ra along with its corresponding
     * indices.
     * @param ra sub-array ra.
     * @param rb sub-array rb.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDownWithIndices(float[] ra, int[] rb, int l, int r, 
            int fromIndex) {
        int j, jold;
        float a = ra[l + fromIndex];
        int b = rb[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            rb[jold + fromIndex] = rb[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
        rb[jold + fromIndex] = b;
    }        

    /**
     * Internal method to reorder sub-array ra.
     * @param ra sub-array ra.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDown(int[] ra, int l, int r, int fromIndex) {
        int j, jold;
        int a = ra[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
    }
    
    /**
     * Internal method to reorder sub-array ra along with its corresponding
     * indices.
     * @param ra sub-array ra.
     * @param rb sub-array rb.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial position.
     */
    private void siftDownWithIndices(int[] ra, int[] rb, int l, int r, 
            int fromIndex) {
        int j, jold;
        int a = ra[l + fromIndex];
        int b = rb[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            rb[jold + fromIndex] = rb[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
        rb[jold + fromIndex] = b;
    }   
    
    /**
     * Internal method to reorder sub-array ra.
     * @param ra sub-array ra.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial value.
     */
    private void siftDown(long[] ra, int l, int r, int fromIndex) {
        int j, jold;
        long a = ra[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
    }
    
    /**
     * Internal method to reorder sub-array ra along with its corresponding
     * indices.
     * @param ra sub-array ra.
     * @param rb sub-array rb.
     * @param l l value.
     * @param r r value.
     * @param fromIndex initial value.
     */
    private void siftDownWithIndices(long[] ra, int[] rb, int l, int r, 
            int fromIndex) {
        int j, jold;
        long a = ra[l + fromIndex];
        int b = rb[l + fromIndex];
        jold = l;
        j = 2 * l + 1;
        while (j <= r) {
            if (j < r && ra[j + fromIndex] < ra[j + 1 + fromIndex]) {
                j++;
            }
            if (a >= ra[j + fromIndex]) {
                break;
            }
            ra[jold + fromIndex] = ra[j + fromIndex];
            rb[jold + fromIndex] = rb[j + fromIndex];
            jold = j;
            j = 2 * j + 1;
        }
        ra[jold + fromIndex] = a;
        rb[jold + fromIndex] = b;
    }
}
