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
 * This insterface represents an object capable of comparing and averaging
 * instances of type T.
 * @param <T> Type to be compared and averaged.
 */
public interface ComparatorAndAverager<T> extends Comparator<T> {
    
    /**
     * Averages provided instances and returns an instance representing the
     * average of provided ones. (i.e. if provided instances were doubles, then
     * result would be 0.5 * (t1 + t2)).
     * @param t1 Instance to be averaged.
     * @param t2 Instance to be averaged.
     * @return Instance representing average of provided ones.
     */
    T average(T t1, T t2);
}
