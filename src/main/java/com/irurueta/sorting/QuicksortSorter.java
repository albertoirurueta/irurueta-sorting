/**
 * @file
 * This file contains implementation of
 * com.irurueta.sorting.QuicksortSorter
 * 
 * @author Alberto Irurueta (alberto@irurueta.com)
 * @date April 7, 2012
 */
package com.irurueta.sorting;

import java.util.Comparator;

/**
 * Sorts instances of type T in provided arrays using Quicksort method.
 * @param <T> Type of instances being sorted.
 * 
 * This class is based on algorithm found at
 * Numerical Recipes. 3rd Edition. Cambridge Press. Chapter 8. p. 424
 * Sedgewick, R. 1978. "Implementing Quicksort Programs", Communications 
 * of the ACM, vol. 21, pp. 847-857.
 */
public class QuicksortSorter<T> extends Sorter<T>{
    
    /**
     * Constant defining size of smallest subarrays to be ordered using
     * straight insertion.
     */
    private static final int M = 7;
    
    /**
     * Constant defining size of stack
     */
    private static final int NSTACK = 64;

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @param comparator Determines whether an element is greater or lower 
     * than another one
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public void sort(T[] array, int fromIndex, int toIndex, 
        Comparator<T> comparator) throws SortingException, 
        IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        if(fromIndex == toIndex) return;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	T a;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(comparator.compare(array[i + fromIndex], a) <= 0) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(comparator.compare(array[l + fromIndex], array[ir + fromIndex]) > 0){
                    swap(array, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(comparator.compare(array[l + 1 + fromIndex], array[ir + fromIndex]) > 0){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(comparator.compare(array[l + fromIndex], array[l + 1 + fromIndex]) > 0){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(comparator.compare(array[i + fromIndex], a) < 0);
                    //Scan down to find element < a
                    do j--; while(comparator.compare(array[j + fromIndex], a) > 0);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @param comparator Determines whether an element is greater or lower 
     * than another one
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public int[] sortWithIndices(T[] array, int fromIndex, int toIndex, 
        Comparator<T> comparator) throws SortingException, 
        IllegalArgumentException, ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        
        int [] indices = getInitialIndicesVector(array.length);
        if(fromIndex == toIndex) return indices;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	T a;
        int b;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(comparator.compare(array[i + fromIndex], a) <= 0) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(comparator.compare(array[l + fromIndex], array[ir + fromIndex]) > 0){
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(comparator.compare(array[l + 1 + fromIndex], array[ir + fromIndex]) > 0){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(comparator.compare(array[l + fromIndex], array[l + 1 + fromIndex]) > 0){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(comparator.compare(array[i + fromIndex], a) < 0);
                    //Scan down to find element < a
                    do j--; while(comparator.compare(array[j + fromIndex], a) > 0);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		indices[l + 1 + fromIndex] = indices[j + fromIndex];
		indices[j + fromIndex] = b;                
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
        
        return indices;
    }

    /**
     * Returns sorting method of this class.
     * @return Sorting method.
     */    
    @Override
    public SortingMethod getMethod() {
        return SortingMethod.QUICKSORT_SORTING_METHOD;
    }
    
   /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public void sort(double[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        if(fromIndex == toIndex) return;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	double a;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public int[] sortWithIndices(double[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        
        int [] indices = getInitialIndicesVector(array.length);
        if(fromIndex == toIndex) return indices;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	double a;
        int b;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		indices[l + 1 + fromIndex] = indices[j + fromIndex];
		indices[j + fromIndex] = b;                
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
        
        return indices;
    }

   /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public void sort(float[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        if(fromIndex == toIndex) return;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	float a;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public int[] sortWithIndices(float[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        
        int [] indices = getInitialIndicesVector(array.length);
        if(fromIndex == toIndex) return indices;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	float a;
        int b;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		indices[l + 1 + fromIndex] = indices[j + fromIndex];
		indices[j + fromIndex] = b;                
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
        
        return indices;
    }
    
   /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public void sort(int[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        if(fromIndex == toIndex) return;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	int a;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public int[] sortWithIndices(int[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        
        int [] indices = getInitialIndicesVector(array.length);
        if(fromIndex == toIndex) return indices;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	int a;
        int b;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		indices[l + 1 + fromIndex] = indices[j + fromIndex];
		indices[j + fromIndex] = b;                
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
        
        return indices;
    }
    
   /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public void sort(long[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        if(fromIndex == toIndex) return;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	long a;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
    }

    /**
     * Sorts provided array in ascending order so that {@code
     * array[i - 1] < array[i]} for any valid i. 
     * This method modifies provided array so that
     * after execution of this method array elements are ordered.
     * An array containing the original indices where elements were
     * located is returned so that other arrays or collections can be kept
     * in the same order
     * @param array Array to be sorted. After execution of this method 
     * elements in array between fromIndex (inclusive) and toIndex 
     * (exclusive) are modified so that they are on ascending order.
     * @param fromIndex Index were sorting starts (inclusive)
     * @param toIndex Index were sorting stops (exclusive)
     * @return Array containing original location of elements that have been
     * sorted. Only elements between fromIndex (inclusive) and toIndex
     * (exclusive) are modified, the remaining ones are kept in natural
     * order.
     * @throws SortingException If for some reason sorting fails
     * @throws IllegalArgumentException If {@code fromIndex > toIndex}
     * @throws ArrayIndexOutOfBoundsException  if {@code fromIndex < 0} or 
     * {@code toIndex > array.length}
     */    
    @Override
    public int[] sortWithIndices(long[] array, int fromIndex, int toIndex) 
            throws SortingException, IllegalArgumentException, 
            ArrayIndexOutOfBoundsException {
        
        if(fromIndex > toIndex) throw new IllegalArgumentException();
        if(fromIndex < 0 || toIndex > array.length) 
            throw new ArrayIndexOutOfBoundsException();
        
        int [] indices = getInitialIndicesVector(array.length);
        if(fromIndex == toIndex) return indices;
        
        int n = toIndex - fromIndex;
        
        int i, j, ir, k, jstack = -1, l = 0;
	long a;
        int b;
	int[] istack = new int[NSTACK];
	ir = n - 1;
		
	for(;;){
            //Insertion sort when subarray is small enough
            if(ir - l < M){
                for(j = l + 1; j <= ir; j++){
                    a = array[j + fromIndex];
                    b = indices[j + fromIndex];
                    for(i = j - 1; i >= l; i--){
                        if(array[i + fromIndex] <= a) break;
                        array[i + 1 + fromIndex] = array[i + fromIndex];
                        indices[i + 1 + fromIndex] = indices[i + fromIndex];
                    }
                    array[i + 1 + fromIndex] = a;
                    indices[i + 1 + fromIndex] = b;
		}
		if(jstack < 0) break;
		//Pop stack and begin a new round of partitioning
		ir = istack[jstack--];
		l = istack[jstack--];
            }else {
		//Choose median of left, center, and right elements as 
		//partitioning element a. Also rearrance so that a(l) <= a(l+1)
		//<= a(ir)
		k = (l + ir) >> 1;
		swap(array, k + fromIndex, l + 1 + fromIndex);
                swapIndices(indices, k + fromIndex, l + 1 + fromIndex);
		//swap(array[k], array[l + 1]);
		if(array[l + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + fromIndex, ir + fromIndex);
                    //swap(array[l], array[ir]);
		}
		if(array[l + 1 + fromIndex] > array[ir + fromIndex]){
                    swap(array, l + 1 + fromIndex, ir + fromIndex);
                    swapIndices(indices, l + 1 + fromIndex, ir + fromIndex);
                    //swap(array[l + 1], arr[ir]);
		}
		if(array[l + fromIndex] > array[l + 1 + fromIndex]){
                    swap(array, l + fromIndex, l + 1 + fromIndex);
                    swapIndices(indices, l + fromIndex, l + 1 + fromIndex);
                    //swap(arr(l), arr(l + 1));
		}
		//Initialize pointers for partitioning
		i = l + 1;
		j = ir;
		//Paritioning element
		a = array[l + 1 + fromIndex];
                b = indices[l + 1 + fromIndex];
		//Beginning of innermost loop
		for(;;)
		{
                    //Scan up to find element > a
                    do i++; while(array[i + fromIndex] < a);
                    //Scan down to find element < a
                    do j--; while(array[j + fromIndex] > a);
                    //Pointers crossed. Partitioning complete
                    if(j < i) break;
                    //Exchange elements
                    swap(array, i + fromIndex, j + fromIndex);
                    swapIndices(indices, i + fromIndex, j + fromIndex);
                    //swap(arr(i), arr(j));
                    //End of innermost loop
		}
		//Insert partitioning element
		array[l + 1 + fromIndex] = array[j + fromIndex];
		array[j + fromIndex] = a;
		indices[l + 1 + fromIndex] = indices[j + fromIndex];
		indices[j + fromIndex] = b;                
		jstack += 2;
		//NSTACK too small in sort
		if(jstack >= NSTACK) throw new SortingException();
		//Push pointers to larger subarray on stack; process smaller 
		//subarray immediately				
		if(ir - i + 1 >= j - l){
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
		}else{
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
		}
            }			
	}
        
        return indices;
    }
    
    /**
     * Swaps values in array of indices at locations posA and posB.
     * @param indices array containing indices to be swapped
     * @param posA Location to be swapped
     * @param posB Location to be swapped
     */    
    private void swapIndices(int[] indices, int posA, int posB){
        int value = indices[posA];
        indices[posA] = indices[posB];
        indices[posB] = value;
    }    
}
