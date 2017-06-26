/**
 * @file
 * This file contains Unit Tests for
 * com.irurueta.sorting.SortingException
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 9, 2012
 */
package com.irurueta.sorting;

import org.junit.*;
import static org.junit.Assert.*;

public class SortingExceptionTest {
    
    public SortingExceptionTest() { }

    @BeforeClass
    public static void setUpClass() throws Exception { }

    @AfterClass
    public static void tearDownClass() throws Exception { }
    
    @Before
    public void setUp() { }
    
    @After
    public void tearDown() { }
    
    @Test
    public void testConstructor() {
        SortingException ex;
        assertNotNull(ex = new SortingException());
        
        ex = null;
        assertNotNull(ex = new SortingException("message"));
        
        ex = null;
        assertNotNull(ex = new SortingException(new Exception()));
        
        ex = null;
        assertNotNull(ex = new SortingException("message", new Exception()));
    }
}
