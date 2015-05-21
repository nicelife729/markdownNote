package com.nicelife729.algo.arrays.test;

import com.nicelife729.algo.arrays.numberfrequency.PrintNumbersWithFrequency;

/**
 * Test class.
 * User: rpanjrath
 * Date: 10/23/13
 * Time: 3:24 PM
 */
public class TestPrintNumbersWithFrequency {

    public static void main(String[] args) {
        int[] input = new int[]{1, 1, 1, 3, 5, 5, 4, 4, 4, 4, 4, 4, 4, 8, 9};
//        PrintNumbersWithFrequency.printNumberWithFrequency(input);
//        PrintNumbersWithFrequency.printNumberWithFrequencyUsingList(input);
        PrintNumbersWithFrequency.printNumberWithFrequencyUsingTemp(input);
    }
}
