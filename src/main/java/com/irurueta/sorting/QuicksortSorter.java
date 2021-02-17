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
 * Sorts instances of type T in provided arrays using Quicksort method.
 *
 * @param <T> Type of instances being sorted.
 *            <p>
 *            This class is based on algorithm found at
 *            Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 424
 *            Sedgewick, R. 1978. "Implementing Quicksort Programs", Communications
 *            of the ACM, vol. 21, pp. 847-857.
 */
@SuppressWarnings("Duplicates")
public class QuicksortSorter<T> extends Sorter<T> {

    /**
     * Constant defining size of smallest subarrays to be ordered using
     * straight insertion.
     */
    private static final int M = 7;

    /**
     * Constant defining size of stack.
     */
    private static final int NSTACK = 64;

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i.
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     *
     * @param array      Array to be sorted. After execution of this method
     *                   elements in array between fromIndex (inclusive) and toIndex
     *                   (exclusive) are modified so that they are on ascending order.
     * @param fromIndex  Index were sorting starts (inclusive).
     * @param toIndex    Index were sorting stops (exclusive).
     * @param comparator Determines whether an element is greater or lower
     *                   than another one.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final T[] array, final int fromIndex, final int toIndex,
                     final Comparator<T> comparator) throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        T a;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (comparator.compare(array[i + fromIndex], a) <= 0) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                if (comparator.compare(array[l + fromIndex], array[ir + fromIndex]) > 0) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + 1 + fromIndex], array[ir + fromIndex]) > 0) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + fromIndex], array[l + 1 + fromIndex]) > 0) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (comparator.compare(array[i + fromIndex], a) < 0);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (comparator.compare(array[j + fromIndex], a) > 0);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
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
     *
     * @param array      Array to be sorted. After execution of this method
     *                   elements in array between fromIndex (inclusive) and toIndex
     *                   (exclusive) are modified so that they are on ascending order.
     * @param fromIndex  Index were sorting starts (inclusive).
     * @param toIndex    Index were sorting stops (exclusive).
     * @param comparator Determines whether an element is greater or lower
     *                   than another one.
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final T[] array, final int fromIndex, final int toIndex,
                                 final Comparator<T> comparator) throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        final int[] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        T a;
        int b;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (comparator.compare(array[i + fromIndex], a) <= 0) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
                if (comparator.compare(array[l + fromIndex], array[ir + fromIndex]) > 0) {
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + 1 + fromIndex], array[ir + fromIndex]) > 0) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                }
                if (comparator.compare(array[l + fromIndex], array[l + 1 + fromIndex]) > 0) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (comparator.compare(array[i + fromIndex], a) < 0);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (comparator.compare(array[j + fromIndex], a) > 0);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                indices[l + 1 + fromIndex] = indices[j + fromIndex];
                indices[j + fromIndex] = b;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }

        return indices;
    }

    /**
     * Returns sorting method of this class.
     *
     * @return Sorting method.
     */
    @Override
    public SortingMethod getMethod() {
        return SortingMethod.QUICKSORT_SORTING_METHOD;
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i.
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final double[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        double a;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
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
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final double[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        final int[] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        double a;
        int b;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                indices[l + 1 + fromIndex] = indices[j + fromIndex];
                indices[j + fromIndex] = b;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }

        return indices;
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i.
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final float[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        float a;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
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
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final float[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        final int[] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        float a;
        int b;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                indices[l + 1 + fromIndex] = indices[j + fromIndex];
                indices[j + fromIndex] = b;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }

        return indices;
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i.
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        int a;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
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
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final int[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        final int[] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        int a;
        int b;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                indices[l + 1 + fromIndex] = indices[j + fromIndex];
                indices[j + fromIndex] = b;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }

        return indices;
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i.
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final long[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        long a;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
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
     *
     * @param array     Array to be sorted. After execution of this method
     *                  elements in array between fromIndex (inclusive) and toIndex
     *                  (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive).
     * @param toIndex   Index were sorting stops (exclusive).
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException               If for some reason sorting fails.
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final long[] array, final int fromIndex, final int toIndex)
            throws SortingException {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        final int[] indices = getInitialIndicesVector(array.length);
        if (fromIndex == toIndex) {
            return indices;
        }

        final int n = toIndex - fromIndex;

        int i;
        int j;
        int ir;
        int k;
        int jstack = -1;
        int l = 0;
        long a;
        int b;
        final int[] istack = new int[NSTACK];
        ir = n - 1;

        for (; ; ) {
            // Insertion sort when subarray is small enough
            if (ir - l < M) {
                for (j = l + 1; j <= ir; j++) {
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for (i = j - 1; i >= l; i--) {
                        if (array[i + fromIndex] <= a) {
                            break;
                        }
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
                }
                if (jstack < 0) {
                    break;
                }
                // Pop stack and begin a new round of partitioning
                ir = istack[jstack--];
                l = istack[jstack--];
            } else {
                // Choose median of left, center, and right elements as
                // partitioning element a. Also rearrange so that a(l) <= a(l+1)
                // <= a(ir)
                k = (l + ir) >> 1;
                swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
                if (array[l + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                }
                if (array[l + 1 + fromIndex] > array[ir + fromIndex]) {
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                }
                if (array[l + fromIndex] > array[l + 1 + fromIndex]) {
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                }
                // Initialize pointers for partitioning
                i = l + 1;
                j = ir;
                // Partitioning element
                a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
                // Beginning of innermost loop
                for (; ; ) {
                    // Scan up to find element > a
                    do {
                        i++;
                    } while (array[i + fromIndex] < a);
                    // Scan down to find element < a
                    do {
                        j--;
                    } while (array[j + fromIndex] > a);
                    // Pointers crossed. Partitioning complete
                    if (j < i) {
                        break;
                    }
                    // Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    // End of innermost loop
                }
                // Insert partitioning element
                array[l + 1 + fromIndex] = array[j + fromIndex];
                array[j + fromIndex] = a;
                indices[l + 1 + fromIndex] = indices[j + fromIndex];
                indices[j + fromIndex] = b;
                jstack += 2;
                // NSTACK too small in sort
                if (jstack >= NSTACK) {
                    throw new SortingException();
                }
                // Push pointers to larger subarray on stack; process smaller
                // subarray immediately
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                } else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }

        return indices;
    }

    /**
     * Swaps values in array of indices at locations posA and posB.
     *
     * @param indices array containing indices to be swapped.
     * @param posA    Location to be swapped.
     * @param posB    Location to be swapped.
     */
    private void swapIndices(final int[] indices, final int posA, final int posB) {
        final int value = indices[posA];
        indices[posA] = indices[posB];
        indices[posB] = value;
    }
}
