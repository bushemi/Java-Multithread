package com.da.lect2.join;

public class JoinExample {

    private static Runnable generateTask(int min, int max) {

        return () -> {
            int delay = (int) (Math.random() * (max - min)) + min;
            try {
                //TimeUnit.SECONDS.sleep(delay);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread " + Thread.currentThread().getName() +
                    " was finished. Delay in thread was = " + delay);
        };
    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(generateTask(3, 8), "First thread");
        Thread t2 = new Thread(generateTask(5, 12), "Second thread");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Main thread was finished");
    }
}
