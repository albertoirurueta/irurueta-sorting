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
 * Sorts instances of type T in provided arrays using straight insertion method.
 *
 * @param <T> Type of instances being sorted.
 *            <p>
 *            This class is based on algorithm found at
 *            Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 424
 *            Knuth. D.E. 1997, Sorting and Searching, 3rd ed., vol. 3 of The Art of
 *            Computer Programming (Reading, MA: Addison-Wesley)
 *            Sedgewick, R. 1998. Algorithms in C, 3rd ed. (Reading, MA: Addison-
 *            Wesley), Chapter 11.
 */
@SuppressWarnings("Duplicates")
public class StraightInsertionSorter<T> extends Sorter<T> {

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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final T[] array, final int fromIndex, final int toIndex,
                     final Comparator<T> comparator) {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        T a;
        int i;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && comparator.compare(array[i - 1], a) > 0) {
                array[i] = array[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final T[] array, final int fromIndex, final int toIndex,
                                 final Comparator<T> comparator) {

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

        int i;
        int b;
        T a;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];
            b = indices[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && comparator.compare(array[i - 1], a) > 0) {
                array[i] = array[i - 1];
                indices[i] = indices[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
            indices[i] = b;
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
        return SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final double[] array, final int fromIndex, final int toIndex) {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        double a;
        int i;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final double[] array, final int fromIndex, final int toIndex) {

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

        int i;
        int b;
        double a;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];
            b = indices[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                indices[i] = indices[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
            indices[i] = b;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final float[] array, final int fromIndex, final int toIndex) {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        float a;
        int i;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final float[] array, final int fromIndex, final int toIndex) {

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

        int i;
        int b;
        float a;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];
            b = indices[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                indices[i] = indices[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
            indices[i] = b;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final int[] array, final int fromIndex, final int toIndex) {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        int a;
        int i;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final int[] array, final int fromIndex, final int toIndex) {

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

        int i;
        int b;
        int a;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];
            b = indices[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                indices[i] = indices[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
            indices[i] = b;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public void sort(final long[] array, final int fromIndex, final int toIndex) {

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (fromIndex == toIndex) {
            return;
        }

        long a;
        int i;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
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
     * @throws IllegalArgumentException       If {@code fromIndex > toIndex}.
     * @throws ArrayIndexOutOfBoundsException if {@code fromIndex < 0} or
     *                                        {@code toIndex > array.length}.
     */
    @Override
    public int[] sortWithIndices(final long[] array, final int fromIndex, final int toIndex) {

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

        int i;
        int b;
        long a;

        for (int j = fromIndex + 1; j < toIndex; j++) {
            // Pick out each element in turn
            a = array[j];
            b = indices[j];

            i = j;

            // look for the place to insert it
            while (i > fromIndex && array[i - 1] > a) {
                array[i] = array[i - 1];
                indices[i] = indices[i - 1];
                i--;
            }

            // Insert it
            array[i] = a;
            indices[i] = b;
        }

        return indices;
    }
}
