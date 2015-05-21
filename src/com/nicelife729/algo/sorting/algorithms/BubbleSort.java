package com.nicelife729.algo.sorting.algorithms;

/**
 * Starting from the beginning of the list, compare every adjacent pair, swap their position if they are not in the
 * right order (the latter one is smaller than the former one).
 * After each iteration, one less element (the last one) is needed to be compared until there are no more elements
 * left to be compared.
 * If at least one swap has been done, repeat step 1.
 *
 * It puts biggest element at the end of the list and decreases the list size by 1, to be compared.
 *
 * http://www.algolist.net/Algorithms/Sorting/Bubble_sort
 * http://upload.wikimedia.org/wikipedia/commons/c/c8/Bubble-sort-example-300px.gif
 *
 *
 *
 介绍：
 冒泡排序的原理非常简单，它重复地走访过要排序的数列，一次比较两个元素，如果他们的顺序错误就把他们交换过来。

 步骤：
 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
 2.对第0个到第n-1个数据做同样的工作。这时，最大的数就“浮”到了数组最后的位置上。
 3.针对所有的元素重复以上的步骤，除了最后一个。
 4. 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。

 优化1：某一趟遍历如果没有数据交换，则说明已经排好序了，因此不用再进行迭代了。用一个标记记录这个状态即可。
 *
 * User: rpanjrath
 * Date: 8/20/13
 * Time: 2:55 PM
 */
public class BubbleSort {

    public int[] sort(int[] inputArray) {
        int arrayLength = inputArray.length;
        int j = 0;
        int temp;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arrayLength - j; i++) {
                if (inputArray[i] > inputArray[i + 1]) {
                    temp = inputArray[i];
                    inputArray[i] = inputArray[i + 1];
                    inputArray[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return inputArray;
    }
}
