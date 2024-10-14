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

import com.irurueta.statistics.UniformRandomizer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StraightInsertionSorterTest {

    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;

    private static final int TIMES = 50;

    @Test
    void testSortWithComparator() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new Date[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var sorter = new StraightInsertionSorter<Date>();
            sorter.sort(array, fromIndex, toIndex, Date::compareTo);

            // check that array is now sorted in ascending order
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sort(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortWithIndicesAndComparator() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new Date[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = new StraightInsertionSorter<Date>();
            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex, Date::compareTo);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sortWithIndices(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortDoubles() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new double[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = new StraightInsertionSorter<Double>();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sort(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortWithIndicesDoubles() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new double[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = new StraightInsertionSorter<Double>();
            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sortWithIndices(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortFloats() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new float[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float) MIN_VALUE, (float) MAX_VALUE);
            }

            final var sorter = new StraightInsertionSorter<Float>();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sort(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortWithIndicesFloats() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new float[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float) MIN_VALUE, (float) MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = new StraightInsertionSorter<Float>();
            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sortWithIndices(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortInts() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new int[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = new StraightInsertionSorter<Integer>();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sort(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortWithIndicesInts() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new int[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = new StraightInsertionSorter<Integer>();
            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sortWithIndices(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortLongs() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new long[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = new StraightInsertionSorter<Long>();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sort(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.sort(array, fromIndex, length + 1));
        }
    }

    @Test
    void testSortWithIndicesLongs() {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new long[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = new StraightInsertionSorter<Long>();
            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            var prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.sortWithIndices(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.sortWithIndices(array, fromIndex, length + 1));
        }
    }

    @Test
    void testGetMethod() {
        final var sorter = new StraightInsertionSorter<>();
        assertEquals(SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD, sorter.getMethod());
    }
}
