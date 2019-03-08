package com.da.lect2.wait.notify;

import java.util.concurrent.TimeUnit;

public class ResultProducer implements Runnable {
    private SharedObject sharedObj;
    private final Object LOCK;
    private Thread ownerThread;


    public ResultProducer(SharedObject sharedObj, Object LOCK, Thread ownerThread) {
        this.sharedObj = sharedObj;
        this.LOCK = LOCK;
        this.ownerThread = ownerThread;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("SECOND THREAD");
        try {
            TimeUnit.SECONDS.sleep(2);
            synchronized (LOCK) {
                System.out.println("\nFirst notify");
                System.out.println("First thread state = " + ownerThread.getState());
                LOCK.notify();
            }
            Thread.sleep(2000);
            sharedObj.setStr("Some valuable result");

            synchronized (LOCK) {
                System.out.println("\nSecond notify");
                System.out.println("First thread state = " + ownerThread.getState());
                LOCK.notify();
            }

        } catch (InterruptedException e) {
        }
    }
}
