package com.nicelife729.algo.sorting.algorithms;

/**
 * Comparison sort with O(n2) timing complexity making in-efficient on large sets.
 * In this algorithm the element on active position (say ith position) is compared with other positions
 * (say i+1th to nth position) and swaps if it's larger than the compared element. The compared element then becomes
 * the active element.
 * In this algorithm, a minimal element is found in the list and put in the start of the list.
 *
 * User: rpanjrath
 * Date: 8/20/13
 * Time: 2:54 PM
 *

 介绍：
 选择排序无疑是最简单直观的排序。它的工作原理如下。

 步骤：
 1.在未排序序列中找到最小（大）元素，存放到排序序列的起始位置。
 2.再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 3.以此类推，直到所有元素均排序完毕。

 */
public class SelectionSort {

    public int[] sort(int[] inputArray) {
        int arraylength = inputArray.length;
        int minIndex; // Current array index considered to be holding minimum value. Will be updated if other index holding lower value is found.
        int temp; // used for swapping.
        for (int i = 0; i < arraylength - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < arraylength; j++) {
                if (inputArray[j] < inputArray[minIndex]) {
                    minIndex = j; // since index j is holding lower value, update minIndex to j.
                }
            }
            // Now minIndex is actually holding the lowest value in current sub-array.
            // if minIndex is i itself then dont swap. It means in the beginning only the selected element was smallest.
            if (minIndex != i) {
                temp = inputArray[i];
                inputArray[i] = inputArray[minIndex];
                inputArray[minIndex] = temp;
            }
        }
        return inputArray;
    }
}
