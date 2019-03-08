package com.da.lect2.wait.notify;


import java.util.Objects;

public class WaitNotifyExample {
    public static void main(String[] args) throws InterruptedException {
        //Reentrant
        final Object LOCK = new Object();
        SharedObject sharedStr = new SharedObject();


        Thread t = new Thread(new ResultProducer(sharedStr, LOCK, Thread.currentThread()));
        t.start();

        synchronized (LOCK) {
            while (Objects.isNull(sharedStr.getStr())) {
                System.out.println("\nawake");
                System.out.println("Main thread state = " + Thread.currentThread().getState());
                System.out.println("Second thread state = " + t.getState());
                LOCK.wait();
            }
        }
        System.out.println("Computation result of second thread = " + sharedStr.getStr());
    }
}
