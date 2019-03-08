package com.da.lect2.unfair;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairMode {
    public static void main(String[] agrs) {
        final CyclicBarrier BAR = new CyclicBarrier(3);
        final Lock REF = new ReentrantLock(true);

        //Please note for using barrier in all threads.
        //If CyclicBarrier is not used first outputs will be the same "A" for instanse.
        //Using CyclicBarrier guarantee is provided that won't be repiting the same letter
        new Thread(() -> {
            try {
                BAR.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true) {
                REF.lock();
                try {
                    System.out.println("A");
                } finally {
                    REF.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            try {
                BAR.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true) {
                REF.lock();
                try {
                    System.out.println("B");
                } finally {
                    REF.unlock();
                }
            }
        }).start();
        new Thread(() -> {
            try {
                BAR.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (true) {
                REF.lock();
                try {
                    System.out.println("C");
                } finally {
                    REF.unlock();
                }
            }
        }).start();
    }
}
