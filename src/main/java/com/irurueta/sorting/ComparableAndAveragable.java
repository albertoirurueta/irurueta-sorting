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
 * Interface for objects that can be compared and averaged with other objects.
 *
 * @param <T> Type to be compared and averaged
 */
public interface ComparableAndAveragable<T> extends Comparable<T> {

    /**
     * Averages current instance with another instance. (i.e. if both objects
     * where Doubles, then the result would be equal to 0.5 * (obj1 + obj2).
     *
     * @param other Other instance to be averaged with.
     * @return An instance representing the average of both instances.
     */
    T averageWith(T other);
}
