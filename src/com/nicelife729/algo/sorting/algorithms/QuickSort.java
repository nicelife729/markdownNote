package com.nicelife729.algo.sorting.algorithms;

/**
 * Choose a pivot value, Partition and Sort both parts. Recursive operation.
 * O(n log n)
 * <p/>
 * User: rpanjrath
 * Date: 8/20/13
 * Time: 2:55 PM
 *
 *
 *
 原理： 小的向左，大的向右，递归下去，排序必完美
 介绍：
 快速排序通常明显比同为Ο(n log n)的其他算法更快，因此常被采用，而且快排采用了分治法的思想，所以在很多笔试面试中能经常看到快排的影子。可见掌握快排的重要性。
 步骤：
 从数列中挑出一个元素作为基准数。
 分区过程，将比基准数大的放到右边，小于或等于它的数都放到左边。
 再对左右区间递归执行第二步，直至各区间只有一个数。
 */
public class QuickSort {

    private int[] inputArray;
    private int length;

    public int[] sort(int[] values) {
        // Check for empty or null array
        if (values == null || values.length == 0) {
            return values;
        }
        this.inputArray = values;
        length = values.length;
        quicksort(0, length - 1);
        return values;
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        int pivot = inputArray[low + (high - low) / 2];

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller then the pivot
            // element then get the next element from the left list.
			// If current value is greater than pivot value then stop!
            while (inputArray[i] < pivot) {
                i++;
            }
            // If the current value from the right list is larger then the pivot
            // element then get the next element (continue) from the right list.
			// If current value is less than pivot value then stop!
            while (inputArray[j] > pivot) {
                j--;
            }

            // If we have found a values in the left list which is larger then
            // the pivot element and if we have found a value in the right list
            // which is smaller then the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j) {
        int temp = inputArray[i];
        inputArray[i] = inputArray[j];
        inputArray[j] = temp;
    }
}
