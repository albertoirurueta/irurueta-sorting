/**
 * @file
 * This file contains Unit Tests for
 * com.irurueta.sorting.QuicksortSorter
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 9, 2012
 */
package com.irurueta.sorting;

import com.irurueta.statistics.UniformRandomizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.*;

public class QuicksortSorterTest {
    
    public static final int MIN_LENGTH = 10;
    public static final int MAX_LENGTH = 100;
    
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 100;
    
    public static final int TIMES = 50;
    
    public QuicksortSorterTest() { }

    @BeforeClass
    public static void setUpClass() throws Exception { }

    @AfterClass
    public static void tearDownClass() throws Exception { }
    
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
        
            QuicksortSorter<Double> sorter = new QuicksortSorter<Double>();
            sorter.sort(array, fromIndex, toIndex, new Comparator<Double>(){

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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
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
        
            QuicksortSorter<Double> sorter = new QuicksortSorter<Double>();
            int[] indices = sorter.sortWithIndices(array, fromIndex, toIndex, 
                    new Comparator<Double>(){

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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { } 
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            }catch(ArrayIndexOutOfBoundsException e) { }  
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            }catch(IllegalArgumentException e){}
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch(ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { } 
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
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sort(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sort(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
        }
    }

    @Test
    public void testSortWithIndicesLongs() throws SortingException {
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
        
            long [] array2 = Arrays.copyOf(array, length);
        
            QuicksortSorter sorter = new QuicksortSorter();
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
            } catch (IllegalArgumentException e) { }
        
            //Force ArrayIndexOutOfBoundsException
            try {
                sorter.sortWithIndices(array, -1, toIndex);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
            try {
                sorter.sortWithIndices(array, fromIndex, length + 1);
                fail("ArrayIndexOutOfBoundsException expected but not thrown");
            } catch (ArrayIndexOutOfBoundsException e) { }
        }
    }    
    
    @Test
    public void testGetMethod() {
        QuicksortSorter sorter = new QuicksortSorter();
        assertEquals(sorter.getMethod(), 
                SortingMethod.QUICKSORT_SORTING_METHOD);
    }    
}
