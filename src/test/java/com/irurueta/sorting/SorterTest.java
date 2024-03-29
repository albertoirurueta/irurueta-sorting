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
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.*;

public class SorterTest {

    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;

    private static final int TIMES = 50;

    @Test
    public void testCreate() {

        Sorter<?> sorter;

        // create without parameters
        sorter = Sorter.create();
        assertNotNull(sorter);
        assertEquals(Sorter.DEFAULT_SORTING_METHOD, sorter.getMethod());

        // create with sorting method
        sorter = Sorter.create(SortingMethod.HEAPSORT_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.HEAPSORT_SORTING_METHOD,
                sorter.getMethod());
        assertTrue(sorter instanceof HeapsortSorter);

        sorter = Sorter.create(SortingMethod.QUICKSORT_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.QUICKSORT_SORTING_METHOD,
                sorter.getMethod());
        assertTrue(sorter instanceof QuicksortSorter);

        sorter = Sorter.create(SortingMethod.SHELL_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.SHELL_SORTING_METHOD,
                sorter.getMethod());

        sorter = Sorter.create(SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.STRAIGHT_INSERTION_SORTING_METHOD,
                sorter.getMethod());

        sorter = Sorter.create(SortingMethod.SYSTEM_SORTING_METHOD);
        assertNotNull(sorter);
        assertEquals(SortingMethod.SYSTEM_SORTING_METHOD,
                sorter.getMethod());
    }

    @Test
    public void testSortWithComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final Date[] array = new Date[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Sorter<Date> sorter = Sorter.create();
            sorter.sort(array, fromIndex, toIndex, new Comparator<Date>() {

                @Override
                public int compare(final Date value1, final Date value2) {
                    return value1.compareTo(value2);
                }
            });

            // check that array is now sorted in ascending order
            Date prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesAndComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final Date[] array = new Date[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();
            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex,
                    new Comparator<Date>() {

                        @Override
                        public int compare(final Date value1, final Date value2) {
                            return value1.compareTo(value2);
                        }
                    });

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            Date prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final double[] array = new double[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Double> sorter = Sorter.create();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final double[] array = new double[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();
            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final float[] array = new float[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float) MIN_VALUE,
                        (float) MAX_VALUE);
            }

            final Sorter<Float> sorter = Sorter.create();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            float prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final float[] array = new float[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float) MIN_VALUE,
                        (float) MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();
            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            float prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final int[] array = new int[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Integer> sorter = Sorter.create();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            int prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final int[] array = new int[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();
            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            int prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final long[] array = new long[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Long> sorter = Sorter.create();
            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            long prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final long[] array = new long[length];

            // set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();
            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order and that indices
            // correspond to sorted vector
            long prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Sorter<Date> sorter = Sorter.create();

            sorter.sort(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            Date prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Sorter<Date> sorter = Sorter.create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            Date prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWholeArray() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Sorter<Date> sorter = Sorter.create();

            sorter.sort(array, new Comparator<Date>() {

                @Override
                public int compare(final Date obj1, final Date obj2) {
                    return obj1.compareTo(obj2);
                }
            });

            // check that array is now sorted in ascending order
            Date prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWholeArrayOfDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Double> sorter = Sorter.create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            double prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWholeArrayOfFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Float> sorter = Sorter.create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            float prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWholeArrayOfInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Integer> sorter = Sorter.create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            int prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWholeArrayOfLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final Sorter<Long> sorter = Sorter.create();

            sorter.sort(array);

            // check that array is now sorted in ascending order
            long prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWithIndicesComparablesWithinRange()
            throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

            // check that array is now sorted in ascending order
            Date prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }

            // Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSortWithIndicesComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            Date prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWithIndicesWholeArray() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array,
                    new Comparator<Date>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }
                    });

            // check that array is now sorted in ascending order
            Date prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWithIndicesWholeArrayOfDoubles()
            throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            double prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWithIndicesWholeArrayOfFloats()
            throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            float prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSortWithIndicesWholeArrayOfInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array);

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
    public void testSortWithIndicesWholeArrayOfLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            final int[] indices = sorter.sortWithIndices(array);

            // check that array is now sorted in ascending order
            long prevValue = array[0];
            for (int i = 1; i < length; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        }
    }

    @Test
    public void testSelectComparables() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final Date selected = sorter.select(pos, array2);

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
            try {
                sorter.select(length, array);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final Date selected = sorter.select(pos, array2, fromIndex, toIndex);

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSelectWithComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final Date selected = sorter.select(pos, array2,
                    new Comparator<Date>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }

                    });

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
            try {
                sorter.select(length, array, new Comparator<Date>() {

                    @Override
                    public int compare(final Date obj1, final Date obj2) {
                        return obj1.compareTo(obj2);
                    }

                });
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final double selected = sorter.select(pos, array2);

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
            try {
                sorter.select(length, array);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final float selected = sorter.select(pos, array2);

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
            try {
                sorter.select(length, array);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final int selected = sorter.select(pos, array2);

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
            try {
                sorter.select(length, array);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int pos = randomizer.nextInt(0, length);

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // select value at pos
            final long selected = sorter.select(pos, array2);

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
            try {
                sorter.select(length, array);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
        }
    }

    @Test
    public void testSelectWithComparatorWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final Date selected = sorter.select(pos, array2, fromIndex, toIndex,
                    new Comparator<Date>() {

                        @Override
                        public int compare(final Date obj1, final Date obj2) {
                            return obj1.compareTo(obj2);
                        }
                    });

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex,
                        new Comparator<Date>() {

                            @Override
                            public int compare(final Date obj1, final Date obj2) {
                                return obj1.compareTo(obj2);
                            }

                        });
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex,
                        new Comparator<Date>() {

                            @Override
                            public int compare(final Date obj1, final Date obj2) {
                                return obj1.compareTo(obj2);
                            }

                        });
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex,
                        new Comparator<Date>() {

                            @Override
                            public int compare(final Date obj1, final Date obj2) {
                                return obj1.compareTo(obj2);
                            }

                        });
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1,
                        new Comparator<Date>() {

                            @Override
                            public int compare(final Date obj1, final Date obj2) {
                                return obj1.compareTo(obj2);
                            }

                        });
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSelectDoublesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final double selected = sorter.select(pos, array2, fromIndex, toIndex);

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSelectFloatsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final float selected = sorter.select(pos, array2, fromIndex, toIndex);

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSelectIntsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final int selected = sorter.select(pos, array2, fromIndex, toIndex);

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testSelectLongWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int pos = randomizer.nextInt(0, toIndex - fromIndex);

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // select value at pos
            final long selected = sorter.select(pos, array2, fromIndex, toIndex);

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
            try {
                sorter.select(toIndex - fromIndex, array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex + 1, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.select(pos, array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.select(pos, array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianComparablesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Date median = sorter.median(array2);

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
            final Date otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianComparablesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Date median = sorter.median(array2);

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
            final Date otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianComparablesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

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
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianWithComparatorOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Object median = sorter.median(array2,
                    new ComparatorAndAverager<Date>() {

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
            final Date otherMedian;
            otherMedian = array[length / 2];

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianWithComparatorEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Object median = sorter.median(array2,
                    new ComparatorAndAverager<Date>() {

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
            final Date otherMedian = new Date((long) (0.5 * (array[(length / 2) - 1].getTime() +
                    array[length / 2].getTime())));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianWithComparatorWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final Date[] array = new Date[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE));
            }

            final Date[] array2 = Arrays.copyOf(array, length);

            final Sorter<Date> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final Object median = sorter.median(array2, fromIndex, toIndex,
                    new ComparatorAndAverager<Date>() {

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
                otherMedian = new Date((long) (0.5 * (array[(n / 2 + fromIndex) - 1].getTime() +
                        array[n / 2 + fromIndex].getTime())));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianDoublesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final double median = sorter.median(array2);

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
            final double otherMedian;
            otherMedian = array[length / 2];

            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    public void testMedianDoublesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final double median = sorter.median(array2);

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
            final double otherMedian = 0.5 * (array[(length / 2) - 1] +
                    array[length / 2]);

            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    public void testMedianDoublesWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final double[] array = new double[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }

            final double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final double median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex],
                    array2[n / 2 + fromIndex], 0.0);


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
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianFloatsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final float median = sorter.median(array2);

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
            final float otherMedian;
            otherMedian = array[length / 2];

            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    public void testMedianFloatsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final float median = sorter.median(array2);

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
            final float otherMedian = 0.5f * (array[(length / 2) - 1] +
                    array[length / 2]);

            assertEquals(otherMedian, median, 0.0);
        }
    }

    @Test
    public void testMedianFloatsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final float[] array = new float[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat(MIN_VALUE, MAX_VALUE);
            }

            final float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final float median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex],
                    array2[n / 2 + fromIndex], 0.0);


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
                otherMedian = 0.5f * (array[(n / 2 + fromIndex) - 1] +
                        array[n / 2 + fromIndex]);
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median, 0.0);

            // Force IllegalArgumentException
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianIntsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final int median = sorter.median(array2);

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
            final int otherMedian;
            otherMedian = array[length / 2];

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianIntsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final int median = sorter.median(array2);

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
            final int otherMedian = (int) (0.5 * (array[(length / 2) - 1] +
                    array[length / 2]));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianIntsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final int[] array = new int[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final int[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final int median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex],
                    array2[n / 2 + fromIndex], 0.0);


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
                otherMedian = (int) (0.5 * (array[(n / 2 + fromIndex) - 1] +
                        array[n / 2 + fromIndex]));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianLongsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final long median = sorter.median(array2);

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
            final long otherMedian;
            otherMedian = array[length / 2];

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianLongsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final long median = sorter.median(array2);

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
            final long otherMedian = (long) (0.5 * (array[(length / 2) - 1] +
                    array[length / 2]));

            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianLongsWithinRange() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            final int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            final int fromIndex = randomizer.nextInt(0, length - 2);
            final int toIndex = randomizer.nextInt(fromIndex + 1, length);
            final int n = toIndex - fromIndex;

            final long[] array = new long[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }

            final long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array, fromIndex, toIndex);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 + fromIndex and elements greater than
            // element at length / 2 + fromIndex because selection is done at
            // length / 2 + fromIndex
            final long median = sorter.median(array2, fromIndex, toIndex);


            // for that reason element at length / 2 is the same on sorted and
            // selected arrays
            assertEquals(array[n / 2 + fromIndex],
                    array2[n / 2 + fromIndex], 0.0);


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
                otherMedian = (long) (0.5 * (array[(n / 2 + fromIndex) - 1] +
                        array[n / 2 + fromIndex]));
            } else {
                otherMedian = array[n / 2 + fromIndex];
            }

            assertEquals(otherMedian, median);

            // Force IllegalArgumentException
            try {
                sorter.median(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (final IllegalArgumentException ignore) {
            }

            // Force ArrayIndexOutOfBoundsException
            try {
                sorter.median(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
            try {
                sorter.median(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (final ArrayIndexOutOfBoundsException ignore) {
            }
        }
    }

    @Test
    public void testMedianComparableAndAverageablesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final AverageableDate[] array = new AverageableDate[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new AverageableDate(new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE)));
            }

            final AverageableDate[] array2 = Arrays.copyOf(array, length);

            final Sorter<AverageableDate> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final AverageableDate median = sorter.median(array2);

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
            final AverageableDate otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianComparableAndAverageablesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final AverageableDate[] array = new AverageableDate[length];

            // set random values into array of comparables
            for (int i = 0; i < length; i++) {
                array[i] = new AverageableDate(new Date(randomizer.nextLong(MIN_VALUE, MAX_VALUE)));
            }

            final AverageableDate[] array2 = Arrays.copyOf(array, length);

            final Sorter<AverageableDate> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final AverageableDate median = sorter.median(array2);

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
            final AverageableDate otherMedian = array[length / 2 - 1].averageWith(array[length / 2]);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianBytesOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Byte[] array = new Byte[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (byte) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final Byte[] array2 = Arrays.copyOf(array, length);

            final Sorter<Byte> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Byte median = sorter.median(array2);

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
            final Byte otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianBytesEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Byte[] array = new Byte[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (byte) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final Byte[] array2 = Arrays.copyOf(array, length);

            final Sorter<Byte> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Byte median = sorter.median(array2);

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
            final Byte otherMedian = (byte) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianCharactersOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Character[] array = new Character[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (char) randomizer.nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
            }

            final Character[] array2 = Arrays.copyOf(array, length);

            final Sorter<Character> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Character median = sorter.median(array2);

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
            final Character otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianCharactersEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Character[] array = new Character[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (char) randomizer.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
            }

            final Character[] array2 = Arrays.copyOf(array, length);

            final Sorter<Character> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Character median = sorter.median(array2);

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
            final Character otherMedian = (char) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianShortsOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Short[] array = new Short[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (short) randomizer.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
            }

            final Short[] array2 = Arrays.copyOf(array, length);

            final Sorter<Short> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Short median = sorter.median(array2);

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
            final Short otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianShortsEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Short[] array = new Short[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (short) randomizer.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
            }

            final Short[] array2 = Arrays.copyOf(array, length);

            final Sorter<Short> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Short median = sorter.median(array2);

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
            final Short otherMedian = (short) ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianIntegersOddLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Integer[] array = new Integer[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Integer[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Integer median = sorter.median(array2);

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
            final Integer otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianIntegersEvenLength() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Integer[] array = new Integer[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Integer[] array2 = Arrays.copyOf(array, length);

            final Sorter<Integer> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Integer median = sorter.median(array2);

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
            final Integer otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianLongsOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Long[] array = new Long[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (long) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Long median = sorter.median(array2);

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
            final Long otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianLongsEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Long[] array = new Long[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (long) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Long[] array2 = Arrays.copyOf(array, length);

            final Sorter<Long> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Long median = sorter.median(array2);

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
            final Long otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianFloatsOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Float[] array = new Float[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (float) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Float median = sorter.median(array2);

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
            final Float otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianFloatsEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Float[] array = new Float[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (float) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Float[] array2 = Arrays.copyOf(array, length);

            final Sorter<Float> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Float median = sorter.median(array2);

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
            final Float otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianDoublesOddLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 == 0) {
                length++;
            }

            final Double[] array = new Double[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (double) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Double median = sorter.median(array2);

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
            final Double otherMedian = array[length / 2];
            assertEquals(otherMedian, median);
        }
    }

    @Test
    public void testMedianDoublesEvenLength2() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            final UniformRandomizer randomizer = new UniformRandomizer(new Random());

            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            if (length % 2 != 0) {
                length++;
            }

            final Double[] array = new Double[length];

            // set random values into array of bytes
            for (int i = 0; i < length; i++) {
                array[i] = (double) randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }

            final Double[] array2 = Arrays.copyOf(array, length);

            final Sorter<Double> sorter = Sorter.create();

            // sort original array
            sorter.sort(array);

            // after median computation array2 will contain elements smaller than
            // element at length / 2 and elements greater than element at
            // length / 2 because selection is done at length / 2
            final Double median = sorter.median(array2);

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
            final Double otherMedian = ((array[length / 2 - 1] + array[length / 2]) / 2);
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AverageableDate that = (AverageableDate) o;
            return date.equals(that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date);
        }
    }
}
