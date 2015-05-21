package com.nicelife729.algo.sorting.algorithms;

/**
 * March up the array, checking each element. If larger (than what's in previous position checked), leave it. If
 * smaller then march back down, shifting larger elements up until encounter a smaller element. Insert there.
 *
 * http://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif
 *
 * User: rpanjrath
 * Date: 8/20/13
 * Time: 2:55 PM
 *
 *
 扑克牌整牌法
 介绍：
 插入排序的工作原理是，对于每个未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。

 步骤：
 从第一个元素开始，该元素可以认为已经被排序
 取出下一个元素，在已经排序的元素序列中从后向前扫描
 如果被扫描的元素（已排序）大于新元素，将该元素后移一位
 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置
 将新元素插入到该位置后
 重复步骤2~5

 *
 */
public class InsertionSort {

    public int[] sort(int[] inputArray) {
        int arrayLength = inputArray.length;
        int temp;
        int j;
        for (int i = 1; i < arrayLength; i++) {
            temp = inputArray[i];
            j = i;
            // Keep on moving the element to the left of the list till its greater than current element in comparison.
            while (j > 0 && inputArray[j - 1] > temp) {
                inputArray[j] = inputArray[j - 1];
                j--;
            }
            inputArray[j] = temp;
        }
        return inputArray;
    }
}
