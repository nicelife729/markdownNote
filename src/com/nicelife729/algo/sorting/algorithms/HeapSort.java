package com.nicelife729.algo.sorting.algorithms;

/**
 * Created by yoyoadm on 15-4-8.
 *
 *
 介绍：
 堆排序在 top K 问题中使用比较频繁。堆排序是采用二叉堆的数据结构来实现的，虽然实质上还是一维数组。二叉堆是一个近似完全二叉树 。
 二叉堆具有以下性质：
 1.父节点的键值总是大于或等于（小于或等于）任何一个子节点的键值。
 2.每个节点的左右子树都是一个二叉堆（都是最大堆或最小堆）。
 步骤：
 1.构造最大堆（Build_Max_Heap）：若数组下标范围为0~n，考虑到单独一个元素是大根堆，则从下标n/2开始的元素均为大根堆。于是只要从n/2-1开始，向前依次构造大根堆，这样就能保证，构造到某个节点时，它的左右子树都已经是大根堆。
 2.堆排序（HeapSort）：由于堆是用数组模拟的。得到一个大根堆后，数组内部并不是有序的。因此需要将堆化数组有序化。思想是移除根节点，并做最大堆调整的递归运算。第一次将heap[0]与heap[n-1]交换，再对heap[0...n-2]做最大堆调整。第二次将heap[0]与heap[n-2]交换，再对heap[0...n-3]做最大堆调整。重复该操作直至heap[0]和heap[1]交换。由于每次都是将最大的数并入到后面的有序区间，故操作完后整个数组就是有序的了。
 3.最大堆调整（Max_Heapify）：该方法是提供给上述两个过程调用的。目的是将堆的末端子节点作调整，使得子节点永远小于父节点 。
 */
public class HeapSort {
  private static int[] a;
  private static int heapSize;

  public static void sort(int[] temp) {
    a = temp;
    sort(0, a.length);
  }

  private static void sort(int left, int right) {
    int i = a.length - 1;

    buildMaxHeap();
    while (i > 0) {
      swap(0, i);
      heapSize -= 1;
      maxHeapify(0);
      i--;
    }
  }

  private static int left(int i) {
    return ((i + 1) * 2 - 1);
  }

  private static int right(int i) {
    return ((i + 1) * 2);
  }

  private static int parent(int i) {
    return ((i + 1) / 2 - 1);
  }

  private static void swap(int x, int y) {
    int temp = a[x];
    a[x] = a[y];
    a[y] = temp;
  }

  private static void buildMaxHeap() {
    int i = a.length / 2 - 1;

    heapSize = a.length;
    while (i >= 0) {
      maxHeapify(i);
      i--;
    }
  }

  private static void maxHeapify(int i) {
    int l = left(i);
    int r = right(i);
    int largest = i;

    if (l < heapSize) {
      if (a[largest] < a[l]) {
        largest = l;
      }
    }
    if (r < heapSize) {
      if (a[largest] < a[r]) {
        largest = r;
      }
    }
    if (largest != i) {
      swap(i, largest);
      maxHeapify(largest);
    }
  }
}
