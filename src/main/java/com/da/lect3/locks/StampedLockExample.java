package com.da.lect3.locks;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {

    public static void main(String[] args) {
        StampedLock lock = new StampedLock();

        new Thread(() -> {
            long stamp = lock.tryOptimisticRead();
            try {
                try {
                    System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
                } finally {
                    //Please pay attantion if statement is not needed
                    //when method unlock() is colled optimisticRead lock is released and
                    //if stamp is not valid IllegalMonitorStateException will be throwen
                    lock.unlock(stamp);
                }
            } catch (InterruptedException e) {
            } catch (IllegalMonitorStateException il) {
                throw il;
            }
        }).start();

        new Thread(() -> {
            long stamp = lock.writeLock();
            try {
                System.out.println("Write Lock acquired");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new Error("");
            } finally {
                lock.unlock(stamp);
                System.out.println("Write done");
            }
        }).start();
    }

}
