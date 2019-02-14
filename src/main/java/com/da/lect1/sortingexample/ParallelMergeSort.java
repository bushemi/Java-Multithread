package com.da.lect1.sortingexample;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort extends RecursiveAction {

    private static final int SORT_THRESHOLD = 3;
    //private static final int SORT_THRESHOLD = 30;

    private final int[] values;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] values) {
        this(values, 0, values.length - 1);
    }

    public ParallelMergeSort(int[] values, int from, int to) {
        this.values = values;
        this.from = from;
        this.to = to;
    }

    public void sort() {
        compute();
    }

    @Override
    protected void compute() {
        if (from < to) {
            int size = to - from;
            if (size < SORT_THRESHOLD) {
                insertionSort();
            } else {
                int mid = from + Math.floorDiv(size, 2);
                invokeAll(new ParallelMergeSort(values, from, mid), new ParallelMergeSort(values, mid + 1, to));
                merge(mid);
            }
        }
    }

    private void insertionSort() {
        for (int i = from + 1; i <= to; ++i) {
            int current = values[i];
            int j = i - 1;
            while (from <= j && current < values[j]) {
                values[j + 1] = values[j--];
            }
            values[j + 1] = current;
        }
    }

    private void merge(int mid) {
        int[] left = Arrays.copyOfRange(values, from, mid + 1);
        int[] right = Arrays.copyOfRange(values, mid + 1, to + 1);
        int f = from;

        int li = 0, ri = 0;
        while (li < left.length && ri < right.length) {
            if (left[li] <= right[ri]) {
                values[f++] = left[li++];
            } else {
                values[f++] = right[ri++];
            }
        }

        while (li < left.length) {
            values[f++] = left[li++];
        }

        while (ri < right.length) {
            values[f++] = right[ri++];
        }
    }


    public static void main(String[] args) {
        int[] serial = new Random().ints(1_000_000).toArray();
        int[] parallel = Arrays.copyOf(serial, serial.length);
        int[] standardParallelSorting = Arrays.copyOf(serial, serial.length);

        InsertionSorter mergeSort = new InsertionSorter();
        long start = System.currentTimeMillis();
        mergeSort.sort(serial);
        System.out.println("Merge Sort done in: " + (System.currentTimeMillis() - start) + "ms");

        ParallelMergeSort sorter = new ParallelMergeSort(parallel);
        start = System.currentTimeMillis();
        sorter.sort();
        System.out.println("Parallel Merge Sort done in: " + (System.currentTimeMillis() - start) + "ms");

        start = System.currentTimeMillis();
        Arrays.parallelSort(standardParallelSorting);
        System.out.println("Parallel standard sort done in: " + (System.currentTimeMillis() - start) + "ms");
    }
}
