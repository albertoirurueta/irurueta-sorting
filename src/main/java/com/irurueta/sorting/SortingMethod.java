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

/**
 * Enumerator containing different algorithms for sorting arrays of data.
 */
public enum SortingMethod {
    /**
     * Sorts data by using a straight insertion algorithm. This is a simple
     * yet slow algorithm for sorting, although for small arrays might be
     * fast enough.
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
