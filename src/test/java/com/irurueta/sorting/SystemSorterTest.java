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
import org.junit.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;

public class SystemSorterTest {
    
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 100;
    
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 100;
    
    private static final int TIMES = 50;
    
    public SystemSorterTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }
    
    @Before
    public void setUp() { }
    
    @After
    public void tearDown() { }
    
    @Test
    public void testSortWithComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            Double [] array = new Double[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }
        
            SystemSorter<Double> sorter = new SystemSorter<>();
            sorter.sort(array, fromIndex, toIndex, new Comparator<Double>() {

                @Override
                public int compare(Double value1, Double value2) {
                    return value1.compareTo(value2);
                }
            });
        
            //check that array is now sorted in ascending order
            Double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }    
    }

    @Test
    public void testSortWithIndicesAndComparator() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            Double [] array = new Double[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }
        
            Double [] array2 = Arrays.copyOf(array, length);
        
            SystemSorter<Double> sorter = new SystemSorter<>();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex, 
                    new Comparator<Double>() {

                @Override
                public int compare(Double value1, Double value2) {
                    return value1.compareTo(value2);
                }
            });
        
            //check that array is now sorted in ascending order and that indices
            //correspond to sorted vector
            Double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue.compareTo(array[i]) <= 0);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }
    }   
    
    @Test
    public void testSortDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            double [] array = new double[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }
        
            SystemSorter sorter = new SystemSorter();
            sorter.sort(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++){
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }  
    }

    @Test
    public void testSortWithIndicesDoubles() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            double [] array = new double[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextDouble(MIN_VALUE, MAX_VALUE);
            }
        
            double [] array2 = Arrays.copyOf(array, length);
        
            SystemSorter sorter = new SystemSorter();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order and that indices
            //correspond to sorted vector
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }      
    }    
    
    @Test
    public void testSortFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            float [] array = new float[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float)MIN_VALUE, 
                        (float)MAX_VALUE);
            }
        
            SystemSorter sorter = new SystemSorter();
            sorter.sort(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }   
    }

    @Test
    public void testSortWithIndicesFloats() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            float [] array = new float[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextFloat((float)MIN_VALUE, 
                        (float)MAX_VALUE);
            }
        
            float [] array2 = Arrays.copyOf(array, length);
        
            SystemSorter sorter = new SystemSorter();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order and that indices
            //correspond to sorted vector
            float prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i], 0.0);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }    
    }        
    
    @Test
    public void testSortInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            int [] array = new int[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }
        
            SystemSorter sorter = new SystemSorter();
            sorter.sort(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }  
    }

    @Test
    public void testSortWithIndicesInts() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            int [] array = new int[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextInt(MIN_VALUE, MAX_VALUE);
            }
        
            int [] array2 = Arrays.copyOf(array, length);
        
            SystemSorter sorter = new SystemSorter();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order and that indices
            //correspond to sorted vector
            float prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                assertEquals(array2[indices[i]], array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sortWithIndices(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        } 
    }        
    
    @Test
    public void testSortLongs() throws SortingException {
        for (int t = 0; t < TIMES; t++) {
            UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
            int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
            int fromIndex = randomizer.nextInt(0, length - 2);
            int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
            long [] array = new long[length];
        
            //set random values into array
            for (int i = 0; i < length; i++) {
                array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
            }
        
            SystemSorter sorter = new SystemSorter();
            sorter.sort(array, fromIndex, toIndex);
        
            //check that array is now sorted in ascending order
            double prevValue = array[fromIndex];
            for (int i = fromIndex + 1; i < toIndex; i++) {
                assertTrue(prevValue <= array[i]);
                prevValue = array[i];
            }
        
            //Force IllegalArgumentException
            try {
                sorter.sort(array, toIndex, fromIndex);
                fail("IllegalArgumentException expected but not thrown");
            } catch (IllegalArgumentException ignore) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException ignore) { }
        }
    }

    @Test
    public void testSortWithIndicesLongs() throws SortingException {
        UniformRandomizer randomizer = new UniformRandomizer(new Random());
        
        int length = randomizer.nextInt(MIN_LENGTH, MAX_LENGTH);
        int fromIndex = randomizer.nextInt(0, length - 2);
        int toIndex = randomizer.nextInt(fromIndex + 1, length);        
        
        long [] array = new long[length];
        
        //set random values into array
        for (int i = 0; i < length; i++) {
            array[i] = randomizer.nextLong(MIN_VALUE, MAX_VALUE);
        }
        
        long [] array2 = Arrays.copyOf(array, length);
        
        SystemSorter sorter = new SystemSorter();
        int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex);
        
        //check that array is now sorted in ascending order and that indices
        //correspond to sorted vector
        float prevValue = array[fromIndex];
        for (int i = fromIndex + 1; i < toIndex; i++) {
            assertTrue(prevValue <= array[i]);
            assertEquals(array2[indices[i]], array[i]);
            prevValue = array[i];
        }
        
        //Force IllegalArgumentException
        try {
            sorter.sortWithIndices(array, toIndex, fromIndex);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        
        //Force ArrayIndexOutOfBoundsException
        try {
            sorter.sortWithIndices(array, -1, toIndex);
            fail("ArrayIndexOutOfBoundsException expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException ignore) { }
        try {
            sorter.sortWithIndices(array, fromIndex, length + 1);
            fail("ArrayIndexOutOfBoundsException expected but not thrown");
        } catch (ArrayIndexOutOfBoundsException ignore) { }
    }  
    
    @Test
    public void testGetMethod() {
        SystemSorter sorter = new SystemSorter();
        assertEquals(sorter.getMethod(), SortingMethod.SYSTEM_SORTING_METHOD);
    }    
}
