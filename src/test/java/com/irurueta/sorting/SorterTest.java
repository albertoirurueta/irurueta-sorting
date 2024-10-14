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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;

    private static final int TIMES = 50;

    @Test
    void testCreate() {

        Sorter<?> sorter;

        // create without parameters
        sorter = Sorter.create();
        assertNotNull(sorter);
        assertEquals(Sorter.DEFAULT_SORTING_METHOD, sorter.getMethod());

        // create with sorting method
        sorter = Sorter.create(SortingMethod.HEAPSORT_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.HEAPSORT_SORTING_METHOD, sorter.getMethod());
        assertInstanceOf(HeapsortSorter.class, sorter);

        sorter = Sorter.create(SortingMethod.QUICKSORT_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.QUICKSORT_SORTING_METHOD, sorter.getMethod());
        assertInstanceOf(QuicksortSorter.class, sorter);

        sorter = Sorter.create(SortingMethod.SHELL_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.SHELL_SORTING_METHOD, sorter.getMethod());

        sorter = Sorter.create(SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD, sorter.getMethod());

        sorter = Sorter.create(SortingMethod.SYSTEM_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.SYSTEM_SORTING_METHOD, sorter.getMethod());
    }

    @Test
    void testSortWithComparator() throws SortingException {
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

            final var sorter = Sorter.<Date>create();
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
    void testSortWithIndicesAndComparator() throws SortingException {
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

            final var sorter = Sorter.<Date>create();
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
    void testSortDoubles() throws SortingException {
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

            final var sorter = Sorter.<Double>create();
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
    void testSortWithIndicesDoubles() throws SortingException {
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

            final var sorter = Sorter.<Double>create();
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
    void testSortFloats() throws SortingException {
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

            final var sorter = Sorter.<Float>create();
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
    void testSortWithIndicesFloats() throws SortingException {
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

            final var sorter = Sorter.<Float>create();
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
    void testSortInts() throws SortingException {
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

            final var sorter = Sorter.<Integer>create();
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
    void testSortWithIndicesInts() throws SortingException {
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

            final var sorter = Sorter.<Integer>create();
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
    void testSortLongs() throws SortingException {
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

            final var sorter = Sorter.<Long>create();
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
    void testSortWithIndicesLongs() throws SortingException {
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

            final var sorter = Sorter.<Long>create();
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
    void testSortComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var sorter = Sorter.<Date>create();

            sorter.sort(array, fromIndex, toIndex);

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
    void testSortComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var sorter = Sorter.<Date>create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWholeArray() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var sorter = Sorter.<Date>create();

            sorter.sort(array, Date::compareTo);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWholeArrayOfDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = Sorter.<Double>create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWholeArrayOfFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = Sorter.<Float>create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWholeArrayOfInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = Sorter.<Integer>create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWholeArrayOfLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var sorter = Sorter.<Long>create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            final var indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
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
    void testSortWithIndicesComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            final var indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesWholeArray() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            final var indices = sorter.sortWithIndices(array, Date::compareTo);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesWholeArrayOfDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            final var indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesWholeArrayOfFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            final var indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesWholeArrayOfInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            final var indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            int prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSortWithIndicesWholeArrayOfLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            final var indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            var prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    void testSelectComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i].compareTo(selected) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(selected) >= 0);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array));
        }
    }

    @Test
    void testSelectComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i].compareTo(selected) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i].compareTo(selected) >= 0);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.select(pos, array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1));
        }
    }

    @Test
    void testSelectWithComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2, Date::compareTo);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i].compareTo(selected) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(selected) >= 0);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array, Date::compareTo));
        }
    }

    @Test
    void testSelectDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array));
        }
    }

    @Test
    void testSelectFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array));
        }
    }

    @Test
    void testSelectInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array));
        }
    }

    @Test
    void testSelectLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var pos = randomizer.nextInt(0, length);

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final var selected = sorter.select(pos, array2);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < pos; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1; i < length; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.select(length, array));
        }
    }

    @Test
    void testSelectWithComparatorWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex, Date::compareTo);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i].compareTo(selected) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i].compareTo(selected) >= 0);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex, Date::compareTo));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex, Date::compareTo));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, -1, toIndex, Date::compareTo));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1, Date::compareTo));
        }
    }

    @Test
    void testSelectDoublesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.select(pos, array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1));
        }
    }

    @Test
    void testSelectFloatsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.select(pos, array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1));
        }
    }

    @Test
    void testSelectIntsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.select(pos, array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1));
        }
    }

    @Test
    void testSelectLongWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var pos = randomizer.nextInt(0, toIndex - fromIndex);

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final var selected = sorter.select(pos, array2, fromIndex, toIndex);

            // check that selected value corresponds to sorted value at pos
            assertEquals(selected, array[pos + fromIndex], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < pos + fromIndex; i++) {
                assertTrue(array2[i] <= selected);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = pos + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= selected);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(toIndex - fromIndex, array, toIndex, fromIndex));
            assertThrows(IllegalArgumentException.class,
                    () -> sorter.select(pos, array, fromIndex + 1, fromIndex));

            //Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.select(pos, array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.select(pos, array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianComparablesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianComparablesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            sorter.median(array2, fromIndex, toIndex);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex]);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i].compareTo(array[n / 2 + fromIndex]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i].compareTo(array[n / 2 + fromIndex]) >= 0);
            }

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianWithComparatorOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2,
                    new ComparatorAndAverager<>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }

                        @Override
                        public Date average(final Date obj1, final Date obj2) {
                            return new Date((long) (0.5 * (obj1.getTime() + obj2.getTime())));
                        }

                    });

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianWithComparatorEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2,
                    new ComparatorAndAverager<>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }

                        @Override
                        public Date average(final Date obj1, final Date obj2) {
                            return new Date((long) (0.5 * (obj1.getTime() + obj2.getTime())));
                        }

                    });

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value for even length
            final Date otherMedian = new Date((long) (0.5 * (array[(length / 2) - 1].getTime()
                    + array[length / 2].getTime())));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianWithComparatorWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Date>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final var median = sorter.median(array2, fromIndex, toIndex,
                    new ComparatorAndAverager<>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }

                        @Override
                        public Date average(final Date obj1, final Date obj2) {
                            return new Date((long) (0.5 * (obj1.getTime() + obj2.getTime())));
                        }

                    });


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex]);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i].compareTo(array[n / 2 + fromIndex]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i].compareTo(array[n / 2 + fromIndex]) >= 0);
            }

            // Check median value
            final Date otherMedian;
            if ((n % 2) == 0) {
                // even length
                otherMedian = new Date((long) (0.5 * (array[(n / 2 + fromIndex) - 1].getTime()
                        + array[n / 2 + fromIndex].getTime())));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianDoublesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    void testMedianDoublesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value even length
            final var otherMedian = 0.5 * (array[(length / 2) - 1] + array[length / 2]);
            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    void testMedianDoublesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final var median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex], 0.0);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i] <= array[n / 2 + fromIndex]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= array[n / 2 + fromIndex]);
            }

            // Check median value
            final double otherMedian;
            if ((n % 2) == 0) {
                // even length
                otherMedian = 0.5 * (array[(n / 2 + fromIndex) - 1] +
                        array[n / 2 + fromIndex]);
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median, 0.0);

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianFloatsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    void testMedianFloatsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value even length
            final var otherMedian = 0.5f * (array[(length / 2) - 1] + array[length / 2]);
            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    void testMedianFloatsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final var median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex], 0.0);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i] <= array[n / 2 + fromIndex]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= array[n / 2 + fromIndex]);
            }

            // Check median value
            final float otherMedian;
            if ((n % 2) == 0) {
                // even length
                otherMedian = 0.5f * (array[(n / 2 + fromIndex) - 1] + array[n / 2 + fromIndex]);
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median, 0.0);

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianIntsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianIntsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value even length
            final int otherMedian = (int) (0.5 * (array[(length / 2) - 1] + array[length / 2]));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianIntsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final var median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex], 0.0);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i] <= array[n / 2 + fromIndex]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= array[n / 2 + fromIndex]);
            }

            // Check median value
            final int otherMedian;
            if ((n % 2) == 0) {
                // even length
                otherMedian = (int) (0.5 * (array[(n / 2 + fromIndex) - 1] + array[n / 2 + fromIndex]));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianLongsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianLongsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2], 0.0);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i] <= array[length / 2]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i] >= array[length / 2]);
            }

            // Check median value even length
            final var otherMedian = (long) (0.5 * (array[(length / 2) - 1] + array[length / 2]));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianLongsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            final var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final var fromIndex = randomizer.nextInt(0, length - 2);
            final var toIndex = randomizer.nextInt(fromIndex + 1, length);
            final var n = toIndex - fromIndex;

            final var array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final var median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex], array2[n / 2 + fromIndex], 0.0);


            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = fromIndex; i < n / 2 + fromIndex; i++) {
                assertTrue(array2[i] <= array[n / 2 + fromIndex]);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = n / 2 + 1 + fromIndex; i < toIndex; i++) {
                assertTrue(array2[i] >= array[n / 2 + fromIndex]);
            }

            // Check median value
            final long otherMedian;
            if ((n % 2) == 0) {
                // even length
                otherMedian = (long) (0.5 * (array[(n / 2 + fromIndex) - 1] + array[n / 2 + fromIndex]));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> sorter.median(array, toIndex, fromIndex));

            // Force ArrayIndexOutOfBoundsException
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> sorter.median(array, -1, toIndex));
            assertThrows(ArrayIndexOutOfBoundsException.class,
                    () -> sorter.median(array, fromIndex, length + 1));
        }
    }

    @Test
    void testMedianComparableAndAverageablesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new AverageableDate[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new AverageableDate(new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE)));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<AverageableDate>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianComparableAndAverageablesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new AverageableDate[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new AverageableDate(new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE)));
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<AverageableDate>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = array[length / 2 - 1].averageWith(array[length / 2]);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianBytesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Byte[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (byte) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Byte>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianBytesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Byte[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (byte) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Byte>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = (byte) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianCharactersOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Character[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (char) randomizer.nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Character>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianCharactersEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Character[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (char) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Character>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = (char) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianShortsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Short[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (short) randomizer.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Short>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianShortsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Short[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (short) randomizer.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Short>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = (short) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianIntegersOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Integer[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianIntegersEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Integer[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Integer>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianLongsOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Long[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (long) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianLongsEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Long[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (long) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Long>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianFloatsOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Float[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (float) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianFloatsEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Float[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (float) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Float>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianDoublesOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final var array = new Double[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (double) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check that median value
            final var otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    void testMedianDoublesEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final var randomizer = new UniformRandomizer();

            var length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final var array = new Double[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (double) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final var array2 = Arrays.copyOf(array, length);

            final var sorter = Sorter.<Double>create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final var median = sorter.median(array2);

            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[length / 2], array2[length / 2]);

            // check that elements in array2[0] ... array2[pos - 1] are lower
            // than selected value
            for (int i = 0; i < length / 2; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) <= 0);
            }

            // check that elements int array2[pos + 1] ... array2[length - 1] are
            // greater than selected value
            for (int i = length / 2 + 1; i < length; i++) {
                assertTrue(array2[i].compareTo(array[length / 2]) >= 0);
            }

            // Check median value
            final var otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    private static class AverageableDate implements ComparableAndAverageable<AverageableDate> {

        private final Date date;

        public AverageableDate(final Date date) {
            this.date = date;
        }

        @Override
        public AverageableDate averageWith(AverageableDate other) {
            return new AverageableDate(new Date((date.getTime() + other.date.getTime()) / 2));
        }

        @Override
        public int compareTo(AverageableDate o) {
            return date.compareTo(o.date);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            AverageableDate that = (AverageableDate) o;
            return date.equals(that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date);
        }
    }
}
