package com.da.lect2.join;

import java.util.concurrent.CountDownLatch;

/**
 * After finishing method run thread does
 * notify all for all hread in wait set on itlelf
 * and flag isAlife became false and after these actions
 * thread finally dies!
 */
public class TestNotify {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new JoinedThread());
        t.start();
        t.join();

        System.out.println("ExceptionHandlerExample thread finished");
    }
}

class JoinedThread implements Runnable {

    @Override
    public void run() {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < latch.getCount(); ++i) {
            new Thread(new TestThread1(Thread.currentThread(), latch)).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (Thread.currentThread()) {
        }

        System.out.println("Joined thread was finished");
    }
}


class TestThread1 implements Runnable {

    private Thread t;
    private CountDownLatch latch;

    public TestThread1(Thread t, CountDownLatch latch) {
        this.t = t;
        this.latch = latch;
    }

    @Override
    public void run() {

        synchronized (t) {
            latch.countDown();
            try {
                t.wait();
                System.out.println("Thread1 was notified");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

