package homework.task1_2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Random;

public class MainClass {
    private static final int ARRAYS_SIZE = 1_000;

//        Есть два массива целых чисел по 1000 элементов.
//        Заполните массивы произвольными числами, затем отсортируйте массивы в первом потоке сортировкой слиянием,
//        во втором потоке быстрой сортировкой.
//        В третьем потоке найтите среднее арифметическое наибольших 10 элементов из первого потока, и нименьших
//        100 элементов из второго потока.
//        Так же результе выполнения программы выведете время работы быстрой сортировкой, и сортировкой слиянием,
//        и в отдельные файлы запишите отсортированные массивы.

    public static void main(String[] args) throws InterruptedException {
        int[] first = new Random().ints(ARRAYS_SIZE).toArray();
        int[] second = new Random().ints(ARRAYS_SIZE).toArray();

        Thread mergeSort = new Thread(() -> {
            long start = System.nanoTime();
            MergeSorter.mergeSort(first, first.length);
            long finish = System.nanoTime();
            System.out.println("MergeSorter in nano: finish-start = " + (finish - start));

            try {
                saveToDisk(first, "mergeSort");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread quickSort = new Thread(() -> {
            long start = System.nanoTime();
            QuickSorter.quickSort(second, 0, first.length - 1);
            long finish = System.nanoTime();
            System.out.println("QuickSorter in nano: finish-start = " + (finish - start));
            try {
                saveToDisk(second, "quickSort");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            int[] maxTen = new int[10];
            System.arraycopy(first, first.length - 10, maxTen, 0, 10);
            int[] minHundred = new int[100];
            System.arraycopy(second,  0, minHundred, 0, 100);

            int[] both = concat(first, second);
            OptionalDouble average = Arrays.stream(both).average();
            System.out.println("average = " + average.getAsDouble());
        });


        mergeSort.start();
        quickSort.start();

        mergeSort.join();
        quickSort.join();
        thread3.start();
        thread3.join();
    }

    private static void saveToDisk(int[] text, String fileName) throws IOException {
        Path path = Paths.get(fileName + ".txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(text).forEach(e -> sb.append(e).append("\n"));
            writer.write(sb.toString());
        }
    }
    private static int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        int[] c = (int[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
}
