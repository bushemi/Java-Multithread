package com.da.lect1.sortingexample;

public class InsertionSorter {

    public void sort(int[] values) {
        for (int i = 1; i < values.length; ++i) {
            insert(values, i-1, values[i]);
        }
    }

    private void insert(int[] array, int rightIndex, int value) {
        while (0 <= rightIndex && value < array[rightIndex]) {
            array[rightIndex+1] = array[rightIndex];
            --rightIndex;
        }
        array[rightIndex+1] = value;
    }
}
