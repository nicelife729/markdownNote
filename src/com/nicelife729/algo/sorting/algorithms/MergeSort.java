package com.nicelife729.algo.sorting.algorithms;

/**
 * Created with IntelliJ IDEA.
 * User: rpanjrath
 * Date: 8/20/13
 * Time: 2:55 PM
 *
 * 介绍：
 归并排序是采用分治法的一个非常典型的应用。归并排序的思想就是先递归分解数组，再合并数组。
 先考虑合并两个有序数组，基本思路是比较两个数组的最前面的数，谁小就先取谁，取了后相应的指针就往后移一位。然后再比较，直至一个数组为空，最后把另一个数组的剩余部分复制过来即可。
 再考虑递归分解，基本思路是将数组分解成left和right，如果这两个数组内部数据是有序的，那么就可以用上面合并数组的方法将这两个数组合并排序。如何让这两个数组内部是有序的？可以再二分，直至分解出的小组只含有一个元素时为止，此时认为该小组内部已有序。然后合并排序相邻二个小组即可。
 */
//TODO
public class MergeSort {
  private static int[] a;

  public static void sort(int temp[]) {
    a = temp;
    sort(0, temp.length - 1);
  }

  private static void sort(int left, int right) {
    int mid = (right + left) / 2;
    if (mid > left)
      sort(left, mid);
    if (right > mid)
      sort(mid + 1, right);
    merge(left, right);
  }

  private static void merge(int left, int right) {
    int[] sorted = new int[right - left + 1];
    int i = 0;
    int mid = (right + left) / 2;
    int leftP = left, rightP = mid + 1; //left pointer, right pointer
    while (leftP <= mid && rightP <= right)
      sorted[i++] = (a[leftP] < a[rightP])? a[leftP++]: a[rightP++];
    if (leftP == mid + 1)
      while(rightP <= right)
        sorted[i++] = a[rightP++];
    else
      while(leftP <= mid)
        sorted[i++] = a[leftP++];
    i = 0;
    while(left <= right)
      a[left++] = sorted[i++];
  }
}
