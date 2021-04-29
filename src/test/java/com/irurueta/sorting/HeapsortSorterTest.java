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
import java.util.Random;

import static org.junit.Assert.*;

public class HeapsortSorterTest {

    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;

    private static final int TIMES = 50;

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

            final HeapsortSorter<Date> sorter =
                    new HeapsortSorter<>();
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

            final HeapsortSorter<Date> sorter =
                    new HeapsortSorter<>();
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
    public void testSortDoubles() {
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

            final HeapsortSorter<Double> sorter = new HeapsortSorter<>();
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
    public void testSortWithIndicesDoubles() {
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

            final HeapsortSorter<Double> sorter = new HeapsortSorter<>();
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
    public void testSortFloats() {
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

            final HeapsortSorter<Float> sorter = new HeapsortSorter<>();
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
    public void testSortWithIndicesFloats() {
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

            final HeapsortSorter<Float> sorter = new HeapsortSorter<>();
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
    public void testSortInts() {
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

            final HeapsortSorter<Integer> sorter = new HeapsortSorter<>();
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
    public void testSortWithIndicesInts() {
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

            final HeapsortSorter<Integer> sorter = new HeapsortSorter<>();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);

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
    public void testSortLongs() {
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

            final HeapsortSorter<Long> sorter = new HeapsortSorter<>();
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
    public void testSortWithIndicesLongs() {
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

            final HeapsortSorter<Long> sorter = new HeapsortSorter<>();
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
    public void testGetMethod() {
        final HeapsortSorter<?> sorter = new HeapsortSorter<>();
        assertEquals(sorter.getMethod(), SortingMethod.HEAPSORT_SORTING_METHOD);
    }
}
