package com.da.lect4.atomics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Program creates five executor services each executor service
 * owns 50 thread. Each thread calculates 2^loop_counter and adds
 * result to common variable. In correct multithreading program
 * result must be the same each time. In class NonAtomicLongExample
 * Atomic variable is not used and there are different result in each
 * iteration.
 */
public class AtomicLongExample {
    private static AtomicLong sum = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {

        for (int k = 0; k < 5; k++) {
            sum.set(0);
            ExecutorService es = Executors.newFixedThreadPool(50);
            for (int i = 1; i <= 50; i++) {
                int finalI = i;
                es.execute(() -> sum.addAndGet((long) Math.pow(2, finalI)));
            }

            es.shutdown();
            es.awaitTermination(10, TimeUnit.MINUTES);
            System.out.println(sum);
        }

    }
}
